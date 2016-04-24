/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.service.patient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.dao.BaseDao;
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.security.token.SecurityHandler;
import com.jiadoctor.common.service.BaseServiceImpl;
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.common.util.StringUtil;
import com.jiadoctor.dao.patient.JybRolesDao;
import com.jiadoctor.dao.patient.JybUserDao;
import com.jiadoctor.entity.patient.JybRoles;
import com.jiadoctor.entity.patient.JybUser;



/**
 * @author Microcat
 * @version 1.0
 */
@Service("jybUserService")
public class JybUserServiceImpl extends BaseServiceImpl implements JybUserService {

	@Autowired
	@Qualifier("jybUserDao")
	private JybUserDao jybUserDao;

	@Autowired
	@Qualifier("jybRolesDao")
	private JybRolesDao jybRolesDao;

	@Autowired
	@Qualifier("baseDao")
	private BaseDao baseDao;

	/**
	 * @throws Exception
	 *  普通方式会员注册
	 */

	public void addJybUser(JybUser jybUser, String ntRolesIds) throws Exception {
		String username = jybUser.getAccount();
		JybUser jybUser0 = jybUserDao.isExistJybUserByName(username);
		if (!BeanUtil.isValid(jybUser0)) {
			String encodePassword = SecurityHandler.encodePassword(jybUser.getPassword(), jybUser.getAccount());// 对密码加盐
			jybUser.setPassword(encodePassword);
			if (BeanUtil.isValid(jybUser.getStatus())) {
				jybUser.setStatus(CommonConstant.STATUS_ONE);
			}
			// 添加权限
			Set<JybRoles> set = new HashSet<JybRoles>();
			if (StringUtil.isNotBlank(ntRolesIds)) {
				for (String id : ntRolesIds.split(",")) {
					JybRoles jybRoles = (JybRoles) baseDao.get(JybRoles.class, Long.parseLong(id));
					if (BeanUtil.isValid(jybRoles)) {
						set.add(jybRoles);
					}
				}
				jybUser.setJybRoles(set);
			}
			jybUser.setRegisTime(new Date());
			this.addObject(jybUser);
		} else {
			throw new Exception(CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
		}
	}

	/**
	 * @throws Exception
	 *  手机验证码注册
	 */

	@Override
	public void addJybUserByMobile(JybUser jybUser) throws Exception {
		String mobile = jybUser.getMobile();
		JybUser jybUser0 = jybUserDao.isExistJybUserByMobile(mobile);
		if (!BeanUtil.isValid(jybUser0)) {
			if (StringUtil.isBlank(jybUser.getStatus()))
				jybUser.setStatus(CommonConstant.STATUS_ONE);
			jybUser.setRegisTime(new Date());
			this.addObject(jybUser);
		} else {
			throw new Exception(CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
		}

	}

	/**
	 * @throws Exception
	 * 手机密码注册
	 */
	@Override
	public void addJybUserByMobilePwd(JybUser jybUser) throws Exception {
		String mobile = jybUser.getMobile();
		JybUser jybUser0 = jybUserDao.isExistJybUserByMobile(mobile);
		if (!BeanUtil.isValid(jybUser0)) {
			String encodePassword = SecurityHandler.encodePassword(jybUser.getPassword(), jybUser.getMobile());// 对密码加盐
			jybUser.setPassword(encodePassword);
			if (StringUtil.isBlank(jybUser.getStatus()))
				jybUser.setStatus(CommonConstant.STATUS_ONE);
			jybUser.setRegisTime(new Date());
			this.addObject(jybUser);
		} else {
			throw new Exception(CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
		}

	}

	/**
	 *  修改会员信息
	 */
	@Override
	public void modifyJybUser(JybUser jybUser, String ntRolesIds) throws Exception {
		JybUser jybUser0 = (JybUser) this.retrieveObject(JybUser.class, jybUser.getId());
		if (null == jybUser0)
			throw new Exception(CommonConstant.MSG_USER_NOEXIST_ERROR);
		jybUser0.setAccount(jybUser.getAccount());
		jybUser0.setNickname(jybUser.getNickname());
		jybUser0.setMobile(jybUser.getMobile());
		jybUser0.setSex(jybUser.getSex());
		jybUser0.setRealName(jybUser.getRealName());
		jybUser0.setBirthday(jybUser.getBirthday());
		jybUser0.setAddress(jybUser.getAddress());
		jybUser0.setArea(jybUser.getArea());
		jybUser0.setUserHead(jybUser.getUserHead());
		jybUser0.setStatus(jybUser.getStatus());
		jybUser0.setRegisFrom(jybUser.getRegisFrom());
		jybUser0.setIsBlock(jybUser.getIsBlock());
		jybUser0.setCardNum(jybUser.getCardNum());
		jybUser0.setCardType(jybUser.getCardType());

		// 添加权限
		Set<JybRoles> set = new HashSet<JybRoles>();
		if (StringUtil.isNotBlank(ntRolesIds)) {
			for (String id : ntRolesIds.split(",")) {
				JybRoles jybRoles = (JybRoles) baseDao.get(JybRoles.class, Long.parseLong(id));
				if (BeanUtil.isValid(jybRoles)) {
					set.add(jybRoles);
				}
			}
			jybUser0.setJybRoles(set);
		}

		this.modifyObject(jybUser0);
	}

	/**
	 * @author ace
	 * @since 2016-03-14 快速拉黑
	 */
	@Override
	public void modifyBlock(String ids) throws Exception {
		JybUser jybUser0;
		String[] strings = ids.split(",");
		List list = new ArrayList();
		for (int i = 0; i < strings.length; i++) {
			list.add(strings[i]);
		}
		for (int j = 0; j < list.size(); j++) {
			Long id = Long.parseLong((String) list.get(j));
			jybUser0 = (JybUser) this.retrieveObject(JybUser.class, id);
			if (null == jybUser0)
				throw new Exception(CommonConstant.MSG_USER_NOEXIST_ERROR);
			jybUser0.setIsBlock("Y");
			this.modifyObject(jybUser0);
		}
	}

	/**
	 *  修改密码
	 */

	@Override
	public void modifyPasswordJybUser(JybUser jybUser, String newPassword) throws Exception {
		JybUser jybUser0 = (JybUser) this.retrieveObject(JybUser.class, jybUser.getId());

		if (null == jybUser0)
			throw new Exception(CommonConstant.MSG_USER_NOEXIST_ERROR);
		if (!BeanUtil.isValid(jybUser.getPassword()))
			throw new Exception(CommonConstant.MSG_PASSWORD_FORMAT_ERROR);

		if (verifyOldPassword(jybUser0.getAccount(), jybUser.getPassword())) {
			String encodePassword = SecurityHandler.encodePassword(newPassword, jybUser.getAccount());
			jybUser0.setPassword(encodePassword);
			this.modifyObject(jybUser0);
		} else {
			throw new Exception(CommonConstant.MSG_UPDATE_PASSWORD_OLD_ERROR);
		}
	}

	/**
	 * 旧密码输入判断
	 */

	@Override
	public boolean verifyOldPassword(String username, String oldPassword) {

		if (StringUtil.isBlank(username)) {
			return false;
		}
		if (StringUtil.isBlank(oldPassword)) {
			return false;
		}
		JybUser jybUser = retrieveJybUserByName(username);
		if (null == jybUser) {
			return false;
		}
		if (StringUtil.isBlank(jybUser.getPassword())) {
			return false;
		}
		return SecurityHandler.verifyPassword(username, oldPassword, jybUser.getPassword());
	}

	/**
	 *  会员列表,查询所有
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<JybUser> retrieveJybUserAll() {
		List resultList = retrieveAllObjects(JybUser.class);
		return (List<JybUser>) resultList;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Pager retrieveJybUserList(Integer pageNo, Integer pageSize) {
		List list = retrieveJybUserAll();
		return getPager(pageNo, pageSize, list, null);
	}

	/**
	 * 查询用户 空参数默认查询全部
	 */

	@SuppressWarnings({ "rawtypes" })
	public List retrieveJybUserAll(JybUser jybUser) {
		List resultList = jybUserDao.retrieveJybUserAll(jybUser);
		return resultList;

	}

	@SuppressWarnings("rawtypes")
	public Pager retrieveJybUserList(JybUser jybUser, Integer pageNo, Integer pageSize) {
		List list = retrieveJybUserAll(jybUser);
		return getPager(pageNo, pageSize, list, null);
	}

	/**
	 *按用户名查询列表
	 */

	@Override
	public JybUser retrieveJybUserByName(String username) {
		return jybUserDao.isExistJybUserByName(username);
	}

	/**
	 *  用户名密码登录
	 */
	public String loginByUsername(JybUser jybUser) {
		// 1.判断用户名是否存在
		// 2.如果存在用户名，则将前端密码加盐在与数据库密码作校验
		JybUser jybUser0 = jybUserDao.isExistJybUserByName(jybUser.getAccount());
		if (jybUser0 != null) {
			if (SecurityHandler.verifyPassword(jybUser.getAccount(), jybUser.getPassword(), jybUser0.getPassword())) {
				return "success";
			} else {
				return "errorPassword";
			}
		} else {
			return "errorUsername";
		}
	}

	/**
	 * 
	 *  手机号登录
	 */
	public String loginByMobile(JybUser jybUser) {
		// 1.判断用户名是否存在
		// 2.如果存在用户名，则将前端密码加盐在与数据库密码作校验
		JybUser jybUser0 = jybUserDao.isExistJybUserByMobile(jybUser.getMobile());
		if (jybUser0 != null) {
			if (SecurityHandler.verifyPassword(jybUser.getAccount(), jybUser.getPassword(), jybUser0.getPassword())) {
				return "success";
			} else {
				return "errorPassword";
			}
		} else {
			return "errorMobile";
		}
	}

	/**
	 * 验证帐号是否存在
	 */
	public JybUser isExistJybUserByMobile(String mobile) {
		return jybUserDao.isExistJybUserByMobile(mobile);
	}

	/**
	 * id查用户
	 */
	public JybUser retrieveJybUserById(Long id) {
		JybUser jybUser = (JybUser) this.retrieveObject(JybUser.class, id);
		return jybUser;
	}

	public void removeJybUser(Long id) {
		this.removeObject(JybUser.class, id);
		;

	}

	/**
	 * 判断昵称是否存在，按昵称查看用户
	 */
	@Override
	public JybUser isExistJybUserByNickname(String nickname) {
		return jybUserDao.isExistJybUserByNickname(nickname);
	}

}
