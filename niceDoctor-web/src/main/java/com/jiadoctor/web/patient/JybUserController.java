/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.web.patient;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.model.ResponseBean;
import com.jiadoctor.common.security.token.VerifyToken;
import com.jiadoctor.common.service.BaseService;
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.common.util.LogUtil;
import com.jiadoctor.entity.patient.JybLoginRecord;
import com.jiadoctor.entity.patient.JybUser;
import com.jiadoctor.service.patient.JybLoginRecordService;
import com.jiadoctor.service.patient.JybRolesService;
import com.jiadoctor.service.patient.JybUserService;
import com.jiadoctor.web.filter.JsonFiltermmm;


/**
 * @author Microcat
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/jybUser")
@CrossOrigin
public class JybUserController {

	@Autowired
	@Qualifier("jybUserService")
	protected JybUserService jybUserService;

	@Autowired
	@Qualifier("jybLoginRecordService")
	protected JybLoginRecordService jybLoginRecordService;

	@Autowired
	@Qualifier("baseService")
	protected BaseService baseService;

	@Autowired
	@Qualifier("jybRolesService")
	protected JybRolesService jybRolesService;
    
	@RequestMapping(value = "/addPatientPage", method = RequestMethod.GET)
	public String addPatientPage() {
		return "addPatient";
	}
	
	@RequestMapping(value = "/eidtPatientPage", method = RequestMethod.GET)
	public String eidtPatientPage(Long id, Model model) {
		JybUser jybUser=(JybUser) baseService.retrieveObject(JybUser.class, id);
		model.addAttribute(jybUser);
		return "editPatient";
	}
	
	/**
	 * 用户名登录
	 * 
	 * @return
	 * @author Ace
	 */
	@RequestMapping(value = "/loginByUsername", method = RequestMethod.POST)
	@ResponseBody
	public Object loginByUsername(JybUser jybUser, HttpSession session) {
		try {
			if (BeanUtil.isValid(jybUser.getAccount()) && BeanUtil.isValid(jybUser.getPassword())) {
				String message = jybUserService.loginByUsername(jybUser);
				if (message.equals("success")) {
					JybUser jybUser0 = jybUserService.retrieveJybUserByName(jybUser.getAccount());
					session.setAttribute("jybUser", jybUser0);
					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_EXECUTE_SUCCESS);
				} else if (message.equals("errorPassword")) {
					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PASSWORD_ERROR);
				} else {
					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_USER_NOEXIST_ERROR);
				}
			} else {
				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_PASSWORD_NULL_ERROR);
			}
		} catch (Exception e) {
			// 写日志
			LogUtil.logException("NewsController loginByUsername has exception:", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	/**
	 * token校验
	 * 
	 * @return
	 * @author Ace
	 */
	@RequestMapping(value = "/verifyToken", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyToken(String token, String username, HttpSession session) {
		try {
			if (BeanUtil.isValid(token) && BeanUtil.isValid(username)) {
				String[] otherTokenDetail = { "verifyToken", username };
				if (VerifyToken.verify(token, otherTokenDetail)) {
					JybUser jybUser0 = jybUserService.isExistJybUserByMobile(username);
					session.setAttribute("jybUser", jybUser0);
					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS);
				} else {
					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_FAIL);
				}
			} else {
				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
			}
		} catch (Exception e) {
			// 写日志
			LogUtil.logException("NewsController loginByMobile has exception:", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	/**
	 * 手机登录
	 * 
	 * @return
	 * @author Ace
	 */
	@RequestMapping(value = "/loginByMobile", method = RequestMethod.POST)
	@ResponseBody
	public Object loginByMobile(JybUser jybUser, HttpSession session) {
		try {
			if (BeanUtil.isValid(jybUser.getMobile()) && BeanUtil.isValid(jybUser.getPassword())) {
				String message = jybUserService.loginByMobile(jybUser);
				if (message.equals("success")) {
					JybUser jybUser0 = jybUserService.isExistJybUserByMobile(jybUser.getMobile());
					session.setAttribute("jybUser", jybUser0);
					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_EXECUTE_SUCCESS);
				} else if (message.equals("errorPassword")) {
					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_PASSWORD_ERROR);
				} else {
					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_USER_NOEXIST_ERROR);
				}
			} else {
				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_LOGINNAME_PASSWORD_NULL_ERROR);
			}
		} catch (Exception e) {
			// 写日志
			LogUtil.logException("NewsController loginByMobile has exception:", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	/**
	 * 手机验证码登录
	 * 
	 * @return
	 * @author Ace
	 */
//	@RequestMapping(value = "/loginBySms", method = RequestMethod.POST)
//	@ResponseBody
//	public Object loginBySms(JybUser jybUser, String code, String business, HttpSession session) {
//		try {
//			if (BeanUtil.isValid(jybUser.getMobile())) {
//				JybUser jybUser0 = jybUserService.isExistJybUserByMobile(jybUser.getMobile());
//				if (BeanUtil.isValid(jybUser0)) {
//					// 判断短信验证码是否一致
//					if (messageService.verifyAuthCode(business, jybUser.getMobile(), code, session)) {
//						jybUser0 = jybUserService.isExistJybUserByMobile(jybUser.getMobile());
//						session.setAttribute("jybUser", jybUser0);
//						return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_EXECUTE_SUCCESS);
//					} else {
//						return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_CODE_ERROR);
//					}
//				} else {
//					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_USER_NOEXIST_ERROR);
//				}
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
//			}
//		} catch (Exception e) {
//			// 写日志
//			LogUtil.logException("NewsController loginBySms has exception:", e);
//			e.printStackTrace();
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
//		}
//	}

	/**
	 * 用户名密码注册
	 * 
	 * @param jybUser
	 * @param ntRolesIds
	 * @return
	 * @author felicia
	 */
	@RequestMapping(value = "addJybUser", method = RequestMethod.POST)
	protected String addJybUser(JybUser jybUser, String ntRolesIds) {
		try {
			if (BeanUtil.isValid(jybUser.getAccount()) && BeanUtil.isValid(jybUser.getPassword())) {
				JybUser jybUser0 = jybUserService.retrieveJybUserByName(jybUser.getAccount());
					jybUserService.addJybUser(jybUser, ntRolesIds);
			}else{
				return "addPatient";
			}
		} catch (Exception e) {
			LogUtil.logException("JybUserController.addJybUser", e);
			return "addPatient";
		}
           return "redirect:/jybUser/getJybUserList";
	}
	
	/**
	 * 用户名密码注册
	 * 
	 * @param jybUser
	 * @param ntRolesIds
	 * @return
	 * @author felicia
	 */
//	@RequestMapping(value = "addJybUser", method = RequestMethod.POST)
//	@ResponseBody
//	protected Object addJybUser(JybUser jybUser, String ntRolesIds) {
//		try {
//			if (BeanUtil.isValid(jybUser.getAccount()) && BeanUtil.isValid(jybUser.getPassword())) {
//				JybUser jybUser0 = jybUserService.retrieveJybUserByName(jybUser.getAccount());
//				if (!BeanUtil.isValid(jybUser0)) {
//					jybUserService.addJybUser(jybUser, ntRolesIds);
//					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
//				} else {
//					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
//				}
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_PASSWORD_NULL_ERROR);
//			}
//		} catch (Exception e) {
//			LogUtil.logException("JybUserController.addJybUser", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
//		}
//
//	}


	/**
	 * 手机验证码注册
	 * 
	 * @param jybUser
	 * @param code
	 * @param business
	 * @param session
	 * @return
	 * @author Felicia
	 */
//	@RequestMapping(value = "addJybUserByMobile", method = RequestMethod.POST)
//	@ResponseBody
//	protected Object addJybUserByMobile(JybUser jybUser, String code, String business, HttpSession session) {
//		try {
//			if (BeanUtil.isValid(jybUser.getMobile())) {
//				JybUser jybUser0 = jybUserService.isExistJybUserByMobile(jybUser.getMobile());
//				if (!BeanUtil.isValid(jybUser0)) {
//					if (messageService.verifyAuthCode(business, jybUser.getMobile(), code, session)) {
//						jybUserService.addJybUserByMobile(jybUser);
//						return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
//					} else {
//						return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_CODE_ERROR);
//					}
//				} else {
//					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
//				}
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
//			}
//		} catch (Exception e) {
//			LogUtil.logException("JybUserController.addJybUserByMobile", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
//		}
//
//	}

	/**
	 * 手机密码注册
	 * 
	 * @param jybUser
	 * @return
	 * @author felicia
	 */
	@RequestMapping(value = "addJybUserByMobilePwd", method = RequestMethod.POST)
	@ResponseBody
	public Object addJybUserByMobilePwd(JybUser jybUser) {
		try {
			if (BeanUtil.isValid(jybUser.getMobile()) && BeanUtil.isValid(jybUser.getPassword())) {
				JybUser jybUser0 = jybUserService.isExistJybUserByMobile(jybUser.getMobile());
				if (!BeanUtil.isValid(jybUser0)) {
					jybUserService.addJybUserByMobilePwd(jybUser);
					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
				} else {
					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
				}
			} else {
				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_PASSWORD_NULL_ERROR);
			}

		} catch (Exception e) {
			LogUtil.logException("JybUserController.addJybUser", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
		}

	}

	/**
	 * 修改信息页面
	 * 
	 * @author ace
	 * @since 2016-03-14
	 */

	@RequestMapping(value = "updateJybUserInfoPager", method = RequestMethod.POST)
	@ResponseBody
	protected Object updateJybUserInfoPager(Long id) {
		try {
			JybUser jybUser = (JybUser) jybUserService.retrieveObject(JybUser.class, id);
			if (BeanUtil.isValid(jybUser)) {
				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS, jybUser);
			} else {
				return new ResponseBean(CommonConstant.SUCCESS_CODE_300, CommonConstant.MSG_FIND_FAIL);
			}
		} catch (Exception e) {
			LogUtil.logException("JybUserController updateJybUserInfoPager", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}

	}

	/**
	 * 修改密码
	 * 
	 * @param jybUser
	 * @param newPassword
	 * @return
	 * @author felicia
	 */

	@RequestMapping(value = "updateJybUserSafe", method = RequestMethod.POST)
	@ResponseBody
	protected Object updateJybUserSafe(JybUser jybUser, String newPassword) {
		try {
			jybUserService.modifyPasswordJybUser(jybUser, newPassword);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_PASSWORD_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("JybUserController.updateUserSafe", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_UPDATE_PASSWORD_FAIL);
		}
	}

	/**
	 * 获取用户编辑信息
	 * 
	 * @param id
	 * @return jybUser
	 * @author felicia
	 * @since 2016-03-14
	 */
	@RequestMapping(value = "getJybUserInfo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object getJybUserInfo(@PathVariable Long id) {
		try {
			JybUser jybUser = jybUserService.retrieveJybUserById(id);
			if (jybUser != null) {
				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, jybUser);
			} else {
				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
			}
		} catch (Exception e) {
			LogUtil.logException("JybUserController.getJybUserInfo", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}

	}

	/**
	 * 编辑信息（不含密码）
	 * 
	 * @param jybUser
	 * @param ntRolesIds
	 * @return
	 * @author felicia
	 */

//	@RequestMapping(value = "updateJybUserInfo", method = RequestMethod.POST)
//	@ResponseBody
//	public Object updateJybUserInfo(JybUser jybUser, String ntRolesIds) {
//		try {
//			jybUserService.modifyJybUser(jybUser, ntRolesIds);
//			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_SUCCESS);
//		} catch (Exception e) {
//			LogUtil.logException("JybUserController.updateJybUserInfo", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_UPDATE_FAIL);
//		}
//
//	}
	@RequestMapping(value = "updateJybUserInfo", method = RequestMethod.POST)
	public String updateJybUserInfo(JybUser jybUser, String ntRolesIds) {
		try {
			jybUserService.modifyJybUser(jybUser, ntRolesIds);
			return "redirect:/jybUser/getJybUserList";
		} catch (Exception e) {
			LogUtil.logException("JybUserController.updateJybUserInfo", e);
			return "redirect:/jybUser/eidtPatientPage?id="+jybUser.getId();
		}

	}

	/**
	 * 快速拉黑(批量)
	 * 
	 * @author ace
	 * @since 2016-03-14
	 */
	@RequestMapping(value = "modifyBlock", method = RequestMethod.POST)
	@ResponseBody
	protected Object modifyBlock(String ids) {
		try {
			if (BeanUtil.isValid(ids)) {
				this.jybUserService.modifyBlock(ids);
				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_SUCCESS);
			} else {
				return new ResponseBean(CommonConstant.SUCCESS_CODE_300, CommonConstant.MSG_UPDATE_FAIL);
			}
		} catch (Exception e) {
			LogUtil.logException("JybUserController updateJybUserInfoPager", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	/**
	 * 删除会员
	 * 
	 * @param id
	 * @return
	 */
//	@RequestMapping(value = "delJybUser/{id}", method = RequestMethod.DELETE)
//	@ResponseBody
//	public Object delJybUser(@PathVariable Long id) {
//		try {
//			jybUserService.removeJybUser(id);
//			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_DELETE_SUCCESS);
//		} catch (Exception e) {
//			LogUtil.logException("JybUserController.delJybUser", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_DELETE_FAIL);
//		}
//	}
	/**
	 * 删除会员
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delJybUser/{id}", method = RequestMethod.GET)
	public String delJybUser(@PathVariable Long id) {
		try {
			jybUserService.removeJybUser(id);
			
		} catch (Exception e) {
			LogUtil.logException("JybUserController.delJybUser", e);
		}
		return "redirect:/jybUser/getJybUserList";
	}

	

	/**
	 * 按用户名查找
	 * 
	 * @param username
	 * @return
	 * @author felicia
	 */

	@RequestMapping(value = "getJybUser/{username}", method = RequestMethod.GET)
	@ResponseBody
	public Object getJybUser(@PathVariable String username) {
		try {
			JybUser jybUser = jybUserService.retrieveJybUserByName(username);
			if (jybUser != null) {
				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, jybUser);
			} else {
				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_USER_NOEXIST_ERROR);
			}
		} catch (Exception e) {
			LogUtil.logException("JybUserController.getJybUser", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	/**
	 * 查询用户 空参数默认查询全部
	 * 
	 * @param jybUser
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author felicia
	 */

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getJybUserList", method = RequestMethod.GET)
	public String getJybUserList(JybUser jybUser, Integer pageNo, Integer pageSize,Model model) {
		try {
			Pager pager = jybUserService.retrieveJybUserList(jybUser, pageNo, pageSize);
			 model.addAttribute(pager);
//			if (pager != null) {
//				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,
//					new JsonFiltermmm().jsonJybUserInfoInfoFilter(pager));
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_USER_NOEXIST_ERROR);
//			}
		} catch (Exception e) {
			LogUtil.logException("JybUserController.getJybUserList", e);
			e.printStackTrace();
			return "patients";
		}
		
		return "patients";
	}

	/**
	 * 查询某位用户的最新20条登陆记录
	 * 
	 * @return
	 * @since 2016-03-04
	 * @author Ace
	 */
	@RequestMapping(value = "/getJybLoginRecordById", method = RequestMethod.POST)
	@ResponseBody
	public Object getJybLoginRecordById(Long jybUserId) {
		try {
			if (BeanUtil.isValid(jybUserId)) {
				List<JybLoginRecord> loginRecords = jybLoginRecordService.getLoginRecordById(jybUserId);
				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,
					new JsonFiltermmm().jsonJybLoginRecordInfoFilter(loginRecords));
			} else {

				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
			}
		} catch (Exception e) {
			// 写日志
			LogUtil.logException("NewsController getLoginRecordById has exception:", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	/**
	 * 判断昵称是否存在
	 * 
	 * @param nickname
	 * @return
	 * @author Felicia
	 */
	@RequestMapping(value = "isExistNickname", method = RequestMethod.GET)
	@ResponseBody
	public Object isExistNickname(String nickname) {
		try {
			if (BeanUtil.isValid(nickname)) {
				JybUser jybUser0 = jybUserService.isExistJybUserByNickname(nickname);
				if (jybUser0 != null) {
					return new ResponseBean(CommonConstant.FAIL_CODE_400, "昵称已存在");
				} else {
					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, "昵称不存在");
				}
			} else {
				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
			}
		} catch (Exception e) {
			LogUtil.logException("JybUserController isExistNickname has exception:", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}

	}

	/**
	 * 判断用户是否存在
	 * 
	 * @param username
	 * @return
	 * @author felicia
	 */
	@RequestMapping(value = "isExistUsername", method = RequestMethod.GET)
	@ResponseBody
	public Object isExistUsername(String username) {
		try {
			if (BeanUtil.isValid(username)) {
				JybUser jybUser0 = jybUserService.retrieveJybUserByName(username);
				if (jybUser0 != null) {
					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
				} else {
					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_USER_NOEXIST_ERROR);
				}
			} else {
				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
			}
		} catch (Exception e) {
			LogUtil.logException("JybUserController isExistUsername has exception:", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

}