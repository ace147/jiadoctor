/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.service.doctor;

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
import com.jiadoctor.dao.doctor.JydRolesDao;
import com.jiadoctor.dao.doctor.JydUserDao;
import com.jiadoctor.entity.doctor.JydRoles;
import com.jiadoctor.entity.doctor.JydUser;



/**
 * @author Ace
 * @version 1.0
 */
@Service("jydUserService")
public class JydUserServiceImpl extends BaseServiceImpl implements JydUserService {

	@Autowired
	@Qualifier("jydUserDao")
	private JydUserDao jydUserDao;

	@Autowired
	@Qualifier("jydRolesDao")
	private JydRolesDao jydRolesDao;

	@Autowired
	@Qualifier("baseDao")
	private BaseDao baseDao;

	/**
	 * @throws Exception
	 *  普通方式会员注册
	 */

	public void addJydUser(JydUser jydUser, String ntRolesIds) throws Exception {
		String username = jydUser.getAccount();
		JydUser jydUser0 = jydUserDao.isExistJydUserByName(username);
		if (!BeanUtil.isValid(jydUser0)) {
			String encodePassword = SecurityHandler.encodePassword(jydUser.getPassword(), jydUser.getAccount());// 对密码加盐
			jydUser.setPassword(encodePassword);
			if (BeanUtil.isValid(jydUser.getStatus())) {
				jydUser.setStatus(CommonConstant.STATUS_ONE);
			}
			
			
//			// 上传前文件的设置
//			setPic();
//			// 设置图片上传
//			if (StringUtil.isNotBlank(this.uploadFileName)) {
//				comUpload.setUpload(this.upload);// 上传的File文件
//				comUpload.setUploadFileName(this.uploadFileName);// 上传文件的文件名
//				if (!comUpload.uploadFile()) {
//					// return ActionConstants.INPUT_KEY;
//				} else {
//					this.jydUser.seth(comUpload.getUploadFileName());
//				}
//			}
			
			// 添加权限
			Set<JydRoles> set = new HashSet<JydRoles>();
			if (StringUtil.isNotBlank(ntRolesIds)) {
				for (String id : ntRolesIds.split(",")) {
					JydRoles jydRoles = (JydRoles) baseDao.get(JydRoles.class, Long.parseLong(id));
					if (BeanUtil.isValid(jydRoles)) {
						set.add(jydRoles);
					}
				}
				jydUser.setJydRoles(set);
			}
			jydUser.setRegisTime(new Date());
			this.addObject(jydUser);
		} else {
			throw new Exception(CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
		}
	}

	/**
	 * @throws Exception
	 *  手机验证码注册
	 */

	@Override
	public void addJydUserByMobile(JydUser jydUser) throws Exception {
		String mobile = jydUser.getMobile();
		JydUser jydUser0 = jydUserDao.isExistJydUserByMobile(mobile);
		if (!BeanUtil.isValid(jydUser0)) {
			if (StringUtil.isBlank(jydUser.getStatus()))
				jydUser.setStatus(CommonConstant.STATUS_ONE);
			jydUser.setRegisTime(new Date());
			this.addObject(jydUser);
		} else {
			throw new Exception(CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
		}

	}

	/**
	 * @throws Exception
	 * 手机密码注册
	 */
	@Override
	public void addJydUserByMobilePwd(JydUser jydUser) throws Exception {
		String mobile = jydUser.getMobile();
		JydUser jydUser0 = jydUserDao.isExistJydUserByMobile(mobile);
		if (!BeanUtil.isValid(jydUser0)) {
			String encodePassword = SecurityHandler.encodePassword(jydUser.getPassword(), jydUser.getMobile());// 对密码加盐
			jydUser.setPassword(encodePassword);
			if (StringUtil.isBlank(jydUser.getStatus()))
				jydUser.setStatus(CommonConstant.STATUS_ONE);
			jydUser.setRegisTime(new Date());
			this.addObject(jydUser);
		} else {
			throw new Exception(CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
		}

	}

	/**
	 *  修改会员信息
	 */
	@Override
	public void modifyJydUser(JydUser jydUser, String ntRolesIds) throws Exception {
		JydUser jydUser0 = (JydUser) this.retrieveObject(JydUser.class, jydUser.getId());
		if (null == jydUser0)
			throw new Exception(CommonConstant.MSG_USER_NOEXIST_ERROR);
		jydUser0.setAccount(jydUser.getAccount());
		jydUser0.setNickname(jydUser.getNickname());
		jydUser0.setMobile(jydUser.getMobile());
		jydUser0.setSex(jydUser.getSex());
		jydUser0.setRealName(jydUser.getRealName());
		jydUser0.setBirthday(jydUser.getBirthday());
		jydUser0.setAddress(jydUser.getAddress());
		jydUser0.setArea(jydUser.getArea());
		jydUser0.setUserHead(jydUser.getUserHead());
		jydUser0.setLastVisitTime(jydUser.getLastVisitTime());
		jydUser0.setLoginNums(jydUser.getLoginNums());
		jydUser0.setStatus(jydUser.getStatus());
		jydUser0.setRegisFrom(jydUser.getRegisFrom());
		jydUser0.setIsBlock(jydUser.getIsBlock());

		// 添加权限
		Set<JydRoles> set = new HashSet<JydRoles>();
		if (StringUtil.isNotBlank(ntRolesIds)) {
			for (String id : ntRolesIds.split(",")) {
				JydRoles jydRoles = (JydRoles) baseDao.get(JydRoles.class, Long.parseLong(id));
				if (BeanUtil.isValid(jydRoles)) {
					set.add(jydRoles);
				}
			}
			jydUser0.setJydRoles(set);
		}

		this.modifyObject(jydUser0);
	}

	/**
	 * @author ace
	 * @since 2016-03-14 快速拉黑
	 */
	@Override
	public void modifydlock(String ids) throws Exception {
		JydUser jydUser0;
		String[] strings = ids.split(",");
		List list = new ArrayList();
		for (int i = 0; i < strings.length; i++) {
			list.add(strings[i]);
		}
		for (int j = 0; j < list.size(); j++) {
			Long id = Long.parseLong((String) list.get(j));
			jydUser0 = (JydUser) this.retrieveObject(JydUser.class, id);
			if (null == jydUser0)
				throw new Exception(CommonConstant.MSG_USER_NOEXIST_ERROR);
			jydUser0.setIsBlock("Y");
			this.modifyObject(jydUser0);
		}
	}

	/**
	 *  修改密码
	 */

	@Override
	public void modifyPasswordJydUser(JydUser jydUser, String newPassword) throws Exception {
		JydUser jydUser0 = (JydUser) this.retrieveObject(JydUser.class, jydUser.getId());

		if (null == jydUser0)
			throw new Exception(CommonConstant.MSG_USER_NOEXIST_ERROR);
		if (!BeanUtil.isValid(jydUser.getPassword()))
			throw new Exception(CommonConstant.MSG_PASSWORD_FORMAT_ERROR);

		if (verifyOldPassword(jydUser0.getAccount(), jydUser.getPassword())) {
			String encodePassword = SecurityHandler.encodePassword(newPassword, jydUser.getAccount());
			jydUser0.setPassword(encodePassword);
			this.modifyObject(jydUser0);
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
		JydUser jydUser = retrieveJydUserByName(username);
		if (null == jydUser) {
			return false;
		}
		if (StringUtil.isBlank(jydUser.getPassword())) {
			return false;
		}
		return SecurityHandler.verifyPassword(username, oldPassword, jydUser.getPassword());
	}

	/**
	 *  会员列表,查询所有
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<JydUser> retrieveJydUserAll() {
		List resultList = retrieveAllObjects(JydUser.class);
		return (List<JydUser>) resultList;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Pager retrieveJydUserList(Integer pageNo, Integer pageSize) {
		List list = retrieveJydUserAll();
		return getPager(pageNo, pageSize, list, null);
	}

	/**
	 * 查询用户 空参数默认查询全部
	 */

	@SuppressWarnings({ "rawtypes" })
	public List retrieveJydUserAll(JydUser jydUser) {
		List resultList = jydUserDao.retrieveJydUserAll(jydUser);
		return resultList;

	}

	@SuppressWarnings("rawtypes")
	public Pager retrieveJydUserList(JydUser jydUser, Integer pageNo, Integer pageSize) {
		List list = retrieveJydUserAll(jydUser);
		return getPager(pageNo, pageSize, list, null);
	}

	/**
	 *按用户名查询列表
	 */

	@Override
	public JydUser retrieveJydUserByName(String username) {
		return jydUserDao.isExistJydUserByName(username);
	}

	/**
	 *  用户名密码登录
	 */
	public String loginByUsername(JydUser jydUser) {
		// 1.判断用户名是否存在
		// 2.如果存在用户名，则将前端密码加盐在与数据库密码作校验
		JydUser jydUser0 = jydUserDao.isExistJydUserByName(jydUser.getAccount());
		if (jydUser0 != null) {
			if (SecurityHandler.verifyPassword(jydUser.getAccount(), jydUser.getPassword(), jydUser0.getPassword())) {
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
	public String loginByMobile(JydUser jydUser) {
		// 1.判断用户名是否存在
		// 2.如果存在用户名，则将前端密码加盐在与数据库密码作校验
		JydUser jydUser0 = jydUserDao.isExistJydUserByMobile(jydUser.getMobile());
		if (jydUser0 != null) {
			if (SecurityHandler.verifyPassword(jydUser.getAccount(), jydUser.getPassword(), jydUser0.getPassword())) {
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
	public JydUser isExistJydUserByMobile(String mobile) {
		return jydUserDao.isExistJydUserByMobile(mobile);
	}

	/**
	 * id查用户
	 */
	public JydUser retrieveJydUserById(Long id) {
		JydUser jydUser = (JydUser) this.retrieveObject(JydUser.class, id);
		return jydUser;
	}

	public void removeJydUser(Long id) {
		this.removeObject(JydUser.class, id);
		;

	}

	/**
	 * 判断昵称是否存在，按昵称查看用户
	 */
	@Override
	public JydUser isExistJydUserByNickname(String nickname) {
		return jydUserDao.isExistJydUserByNickname(nickname);
	}
	
	
	/**
	 * 设置上传图片通用信息
	 * 
	 * @throws Exception
	 */
//	private void setPic() throws Exception {
//		comUpload.setMaxSize(comUpload.getPhotoMaxSize());// 上传文件最大尺寸1.5M
//		comUpload.setSavePath(Constants.FILE_SEPARATOR + "upload"
//				+ Constants.FILE_SEPARATOR + "enterprice"+Constants.FILE_SEPARATOR+"ClinicImg");// 上传文件保存路径
//		comUpload.setAllowTypes(comUpload.getAllowTypePhoto());// 上传文件所允许的格式
//	}

}
