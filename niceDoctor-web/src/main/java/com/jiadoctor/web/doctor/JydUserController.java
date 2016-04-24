/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.web.doctor;

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

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.model.ResponseBean;
import com.jiadoctor.common.security.token.VerifyToken;
import com.jiadoctor.common.service.BaseService;
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.common.util.LogUtil;
import com.jiadoctor.entity.doctor.JydLoginRecord;
import com.jiadoctor.entity.doctor.JydUser;
import com.jiadoctor.entity.patient.JybUser;
import com.jiadoctor.service.doctor.JydLoginRecordService;
import com.jiadoctor.service.doctor.JydRolesService;
import com.jiadoctor.service.doctor.JydUserService;
import com.jiadoctor.web.filter.JsonFiltermmm;


/**
 * @author Ace
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/jydUser")
@CrossOrigin
public class JydUserController {

	@Autowired
	@Qualifier("jydUserService")
	protected JydUserService jydUserService;

	@Autowired
	@Qualifier("jydLoginRecordService")
	protected JydLoginRecordService jydLoginRecordService;

	@Autowired
	@Qualifier("baseService")
	protected BaseService baseService;

	@Autowired
	@Qualifier("jydRolesService")
	protected JydRolesService jydRolesService;
    
	
	
	/**
	 * 查询用户 空参数默认查询全部  jsp
	 * 
	 * @param jydUser
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author felicia
	 */
	@RequestMapping(value = "getJydUserList", method = RequestMethod.GET)
	public String getJydUserList(JydUser jydUser, Integer pageNo, Integer pageSize,Model model) {
		try {
			// response.addHeader("Access-Control-Allow-Origin", "*");
			Pager pager = jydUserService.retrieveJydUserList(jydUser, pageNo, pageSize);
			 model.addAttribute(pager);
		} catch (Exception e) {
			LogUtil.logException("JydUserController.getJydUserList", e);
			e.printStackTrace();
			return "doctors";
		}
		return "doctors";
	}
	/**
	 * 添加医生页面
	 * @return
	 */
	@RequestMapping(value = "/addDoctorPage", method = RequestMethod.GET)
	public String addPatientPage() {
		return "addDoctor";
	}
	
	/**
	 * 添加医生
	 * @param jydUser
	 * @param ntRolesIds
	 * @return
	 */
	@RequestMapping(value = "addJydUser", method = RequestMethod.POST)
	protected String addJydUser(JydUser jydUser, String ntRolesIds) {
		try {
			if (BeanUtil.isValid(jydUser.getAccount()) && BeanUtil.isValid(jydUser.getPassword())) {
				JydUser jydUser0 = jydUserService.retrieveJydUserByName(jydUser.getAccount());
				jydUserService.addJydUser(jydUser, ntRolesIds);
			} else {
				return "addDoctor";
			}
		} catch (Exception e) {
			LogUtil.logException("JydUserController.addJydUser", e);
			return "addDoctor";
		}
		return "redirect:/jydUser/getJydUserList";
	}
	
	/**
	 * 获取医生资料(修改页面)
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/eidtDoctorPage", method = RequestMethod.GET)
	public String eidtDoctorPage(Long id, Model model) {
		JydUser jydUser=(JydUser) baseService.retrieveObject(JydUser.class, id);
		model.addAttribute(jydUser);
		return "editDoctor";
	}
	
	/**
	 * 编辑信息（不含密码）
	 * 
	 * @param jydUser
	 * @param ntRolesIds
	 * @return
	 * @author felicia
	 */
	@RequestMapping(value = "updateJydUserInfo", method = RequestMethod.POST)
	public String updateJydUserInfo(JydUser jydUser, String ntRolesIds) {
		try {
			jydUserService.modifyJydUser(jydUser, ntRolesIds);
			return "redirect:/jydUser/getJydUserList";
		} catch (Exception e) {
			LogUtil.logException("JydUserController.updateJydUserInfo", e);
			return "redirect:/jydUser/eidtDoctorPage?id="+jydUser.getId();
		}
	}
	
//	/**
//	 * 用户名登录
//	 * 
//	 * @return
//	 * @author Ace
//	 */
//	@RequestMapping(value = "/loginByUsername", method = RequestMethod.POST)
//	@ResponseBody
//	public Object loginByUsername(JydUser jydUser, HttpSession session) {
//		try {
//			if (BeanUtil.isValid(jydUser.getAccount()) && BeanUtil.isValid(jydUser.getPassword())) {
//				String message = jydUserService.loginByUsername(jydUser);
//				if (message.equals("success")) {
//					JydUser jydUser0 = jydUserService.retrieveJydUserByName(jydUser.getAccount());
//					session.setAttribute("jydUser", jydUser0);
//					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_EXECUTE_SUCCESS);
//				} else if (message.equals("errorPassword")) {
//					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PASSWORD_ERROR);
//				} else {
//					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_USER_NOEXIST_ERROR);
//				}
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_PASSWORD_NULL_ERROR);
//			}
//		} catch (Exception e) {
//			// 写日志
//			LogUtil.logException("NewsController loginByUsername has exception:", e);
//			e.printStackTrace();
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
//		}
//	}
//
//	/**
//	 * token校验
//	 * 
//	 * @return
//	 * @author Ace
//	 */
//	@RequestMapping(value = "/verifyToken", method = RequestMethod.POST)
//	@ResponseBody
//	public Object verifyToken(String token, String username, HttpSession session) {
//		try {
//			if (BeanUtil.isValid(token) && BeanUtil.isValid(username)) {
//				String[] otherTokenDetail = { "verifyToken", username };
//				if (VerifyToken.verify(token, otherTokenDetail)) {
//					JydUser jydUser0 = jydUserService.isExistJydUserByMobile(username);
//					session.setAttribute("jydUser", jydUser0);
//					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS);
//				} else {
//					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_FAIL);
//				}
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
//			}
//		} catch (Exception e) {
//			// 写日志
//			LogUtil.logException("NewsController loginByMobile has exception:", e);
//			e.printStackTrace();
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
//		}
//	}
//
//	/**
//	 * 手机登录
//	 * 
//	 * @return
//	 * @author Ace
//	 */
//	@RequestMapping(value = "/loginByMobile", method = RequestMethod.POST)
//	@ResponseBody
//	public Object loginByMobile(JydUser jydUser, HttpSession session) {
//		try {
//			if (BeanUtil.isValid(jydUser.getMobile()) && BeanUtil.isValid(jydUser.getPassword())) {
//				String message = jydUserService.loginByMobile(jydUser);
//				if (message.equals("success")) {
//					JydUser jydUser0 = jydUserService.isExistJydUserByMobile(jydUser.getMobile());
//					session.setAttribute("jydUser", jydUser0);
//					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_EXECUTE_SUCCESS);
//				} else if (message.equals("errorPassword")) {
//					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_PASSWORD_ERROR);
//				} else {
//					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_USER_NOEXIST_ERROR);
//				}
//			} else {
//				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_LOGINNAME_PASSWORD_NULL_ERROR);
//			}
//		} catch (Exception e) {
//			// 写日志
//			LogUtil.logException("NewsController loginByMobile has exception:", e);
//			e.printStackTrace();
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
//		}
//	}
//
//	/**
//	 * 手机验证码登录
//	 * 
//	 * @return
//	 * @author Ace
//	 */
////	@RequestMapping(value = "/loginBySms", method = RequestMethod.POST)
////	@ResponseBody
////	public Object loginBySms(JydUser jydUser, String code, String business, HttpSession session) {
////		try {
////			if (BeanUtil.isValid(jydUser.getMobile())) {
////				JydUser jydUser0 = jydUserService.isExistJydUserByMobile(jydUser.getMobile());
////				if (BeanUtil.isValid(jydUser0)) {
////					// 判断短信验证码是否一致
////					if (messageService.verifyAuthCode(business, jydUser.getMobile(), code, session)) {
////						jydUser0 = jydUserService.isExistJydUserByMobile(jydUser.getMobile());
////						session.setAttribute("jydUser", jydUser0);
////						return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_EXECUTE_SUCCESS);
////					} else {
////						return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_CODE_ERROR);
////					}
////				} else {
////					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_USER_NOEXIST_ERROR);
////				}
////			} else {
////				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
////			}
////		} catch (Exception e) {
////			// 写日志
////			LogUtil.logException("NewsController loginBySms has exception:", e);
////			e.printStackTrace();
////			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
////		}
////	}
//
//	/**
//	 * 用户名密码注册
//	 * 
//	 * @param jydUser
//	 * @param ntRolesIds
//	 * @return
//	 * @author felicia
//	 */
////	@RequestMapping(value = "addJydUser", method = RequestMethod.POST)
////	@ResponseBody
////	protected Object addJydUser(JydUser jydUser, String ntRolesIds) {
////		try {
////			if (BeanUtil.isValid(jydUser.getAccount()) && BeanUtil.isValid(jydUser.getPassword())) {
////				JydUser jydUser0 = jydUserService.retrieveJydUserByName(jydUser.getAccount());
////				if (!BeanUtil.isValid(jydUser0)) {
////					jydUserService.addJydUser(jydUser, ntRolesIds);
////					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
////				} else {
////					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
////				}
////			} else {
////				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_PASSWORD_NULL_ERROR);
////			}
////		} catch (Exception e) {
////			LogUtil.logException("JydUserController.addJydUser", e);
////			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
////		}
////
////	}
//
//	/**
//	 * 手机验证码注册
//	 * 
//	 * @param jydUser
//	 * @param code
//	 * @param business
//	 * @param session
//	 * @return
//	 * @author Felicia
//	 */
////	@RequestMapping(value = "addJydUserByMobile", method = RequestMethod.POST)
////	@ResponseBody
////	protected Object addJydUserByMobile(JydUser jydUser, String code, String business, HttpSession session) {
////		try {
////			if (BeanUtil.isValid(jydUser.getMobile())) {
////				JydUser jydUser0 = jydUserService.isExistJydUserByMobile(jydUser.getMobile());
////				if (!BeanUtil.isValid(jydUser0)) {
////					if (messageService.verifyAuthCode(business, jydUser.getMobile(), code, session)) {
////						jydUserService.addJydUserByMobile(jydUser);
////						return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
////					} else {
////						return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_CODE_ERROR);
////					}
////				} else {
////					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
////				}
////			} else {
////				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
////			}
////		} catch (Exception e) {
////			LogUtil.logException("JydUserController.addJydUserByMobile", e);
////			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
////		}
////
////	}
//
//	/**
//	 * 手机密码注册
//	 * 
//	 * @param jydUser
//	 * @return
//	 * @author felicia
//	 */
//	@RequestMapping(value = "addJydUserByMobilePwd", method = RequestMethod.POST)
//	@ResponseBody
//	public Object addJydUserByMobilePwd(JydUser jydUser) {
//		try {
//			if (BeanUtil.isValid(jydUser.getMobile()) && BeanUtil.isValid(jydUser.getPassword())) {
//				JydUser jydUser0 = jydUserService.isExistJydUserByMobile(jydUser.getMobile());
//				if (!BeanUtil.isValid(jydUser0)) {
//					jydUserService.addJydUserByMobilePwd(jydUser);
//					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
//				} else {
//					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
//				}
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_PASSWORD_NULL_ERROR);
//			}
//
//		} catch (Exception e) {
//			LogUtil.logException("JydUserController.addJydUser", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
//		}
//
//	}
//
//	/**
//	 * 修改信息页面
//	 * 
//	 * @author ace
//	 * @since 2016-03-14
//	 */
//
//	@RequestMapping(value = "updateJydUserInfoPager", method = RequestMethod.POST)
//	@ResponseBody
//	protected Object updateJydUserInfoPager(Long id) {
//		try {
//			JydUser jydUser = (JydUser) jydUserService.retrieveObject(JydUser.class, id);
//			if (BeanUtil.isValid(jydUser)) {
//				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS, jydUser);
//			} else {
//				return new ResponseBean(CommonConstant.SUCCESS_CODE_300, CommonConstant.MSG_FIND_FAIL);
//			}
//		} catch (Exception e) {
//			LogUtil.logException("JydUserController updateJydUserInfoPager", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
//		}
//
//	}
//
//	/**
//	 * 修改密码
//	 * 
//	 * @param jydUser
//	 * @param newPassword
//	 * @return
//	 * @author felicia
//	 */
//
//	@RequestMapping(value = "updateJydUserSafe", method = RequestMethod.POST)
//	@ResponseBody
//	protected Object updateJydUserSafe(JydUser jydUser, String newPassword) {
//		try {
//			jydUserService.modifyPasswordJydUser(jydUser, newPassword);
//			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_PASSWORD_SUCCESS);
//		} catch (Exception e) {
//			LogUtil.logException("JydUserController.updateUserSafe", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_UPDATE_PASSWORD_FAIL);
//		}
//	}
//
//	/**
//	 * 获取用户编辑信息
//	 * 
//	 * @param id
//	 * @return jydUser
//	 * @author felicia
//	 * @since 2016-03-14
//	 */
////	@RequestMapping(value = "getJydUserInfo/{id}", method = RequestMethod.GET)
////	@ResponseBody
////	public Object getJydUserInfo(@PathVariable Long id) {
////		try {
////			JydUser jydUser = jydUserService.retrieveJydUserById(id);
////			if (jydUser != null) {
////				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, jydUser);
////			} else {
////				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
////			}
////		} catch (Exception e) {
////			LogUtil.logException("JydUserController.getJydUserInfo", e);
////			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
////		}
////
////	}
//
////	/**
////	 * 编辑信息（不含密码）
////	 * 
////	 * @param jydUser
////	 * @param ntRolesIds
////	 * @return
////	 * @author felicia
////	 */
////
////	@RequestMapping(value = "updateJydUserInfo", method = RequestMethod.POST)
////	@ResponseBody
////	public Object updateJydUserInfo(JydUser jydUser, String ntRolesIds) {
////		try {
////			jydUserService.modifyJydUser(jydUser, ntRolesIds);
////			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_SUCCESS);
////		} catch (Exception e) {
////			LogUtil.logException("JydUserController.updateJydUserInfo", e);
////			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_UPDATE_FAIL);
////		}
////
////	}
//
//	/**
//	 * 快速拉黑(批量)
//	 * 
//	 * @author ace
//	 * @since 2016-03-14
//	 */
//	@RequestMapping(value = "modifydlock", method = RequestMethod.POST)
//	@ResponseBody
//	protected Object modifydlock(String ids) {
//		try {
//			if (BeanUtil.isValid(ids)) {
//				this.jydUserService.modifydlock(ids);
//				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_SUCCESS);
//			} else {
//				return new ResponseBean(CommonConstant.SUCCESS_CODE_300, CommonConstant.MSG_UPDATE_FAIL);
//			}
//		} catch (Exception e) {
//			LogUtil.logException("JydUserController updateJydUserInfoPager", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
//		}
//	}
//
//	/**
//	 * 删除会员
//	 * 
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value = "delJydUser/{id}", method = RequestMethod.DELETE)
//	@ResponseBody
//	public Object delJydUser(@PathVariable Long id) {
//		try {
//			jydUserService.removeJydUser(id);
//			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_DELETE_SUCCESS);
//		} catch (Exception e) {
//			LogUtil.logException("JydUserController.delJydUser", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_DELETE_FAIL);
//		}
//	}
//
//	/**
//	 * 查询全部
//	 * 
//	 * @param pageNo
//	 * @param pageSize
//	 * @return
//	 * @author felicia
//	 */
//	@RequestMapping(value = "getJydUserAll", method = RequestMethod.GET)
//	@ResponseBody
//	public Object getJydUserAll(Integer pageNo, Integer pageSize) {
//		try {
//			Pager pager = jydUserService.retrieveJydUserList(pageNo, pageSize);
//			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, pager);
//		} catch (Exception e) {
//			LogUtil.logException("JydUserController.getJydUserAll", e);
//			e.printStackTrace();
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
//		}
//	}
//
//	/**
//	 * 按用户名查找
//	 * 
//	 * @param username
//	 * @return
//	 * @author felicia
//	 */
//
//	@RequestMapping(value = "getJydUser/{username}", method = RequestMethod.GET)
//	@ResponseBody
//	public Object getJydUser(@PathVariable String username) {
//		try {
//			JydUser jydUser = jydUserService.retrieveJydUserByName(username);
//			if (jydUser != null) {
//				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, jydUser);
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_USER_NOEXIST_ERROR);
//			}
//		} catch (Exception e) {
//			LogUtil.logException("JydUserController.getJydUser", e);
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
//		}
//	}
//
//	/**
//	 * 查询用户 空参数默认查询全部
//	 * 
//	 * @param jydUser
//	 * @param pageNo
//	 * @param pageSize
//	 * @return
//	 * @author felicia
//	 */
//
////	@RequestMapping(value = "getJydUserList", method = RequestMethod.GET)
////	@ResponseBody
////	public Object getJydUserList(JydUser jydUser, Integer pageNo, Integer pageSize) {
////		try {
////			// response.addHeader("Access-Control-Allow-Origin", "*");
////			Pager pager = jydUserService.retrieveJydUserList(jydUser, pageNo, pageSize);
////			if (pager != null) {
////				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,
////					new JsonFiltermmm().jsonJybUserInfoInfoFilter(pager));
////			} else {
////				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_USER_NOEXIST_ERROR);
////			}
////
////		} catch (Exception e) {
////			LogUtil.logException("JydUserController.getJydUserList", e);
////			e.printStackTrace();
////			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
////		}
////	}
//
//	/**
//	 * 查询某位用户的最新20条登陆记录
//	 * 
//	 * @return
//	 * @since 2016-03-04
//	 * @author Ace
//	 */
//	@RequestMapping(value = "/getJydLoginRecordById", method = RequestMethod.POST)
//	@ResponseBody
//	public Object getJydLoginRecordById(Long jydUserId) {
//		try {
//			if (BeanUtil.isValid(jydUserId)) {
//				List<JydLoginRecord> loginRecords = jydLoginRecordService.getLoginRecordById(jydUserId);
//				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,
//					new JsonFiltermmm().jsonJydLoginRecordInfoFilter(loginRecords));
//			} else {
//
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
//			}
//		} catch (Exception e) {
//			// 写日志
//			LogUtil.logException("NewsController getLoginRecordById has exception:", e);
//			e.printStackTrace();
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
//		}
//	}
//
//	/**
//	 * 判断昵称是否存在
//	 * 
//	 * @param nickname
//	 * @return
//	 * @author Felicia
//	 */
//	@RequestMapping(value = "isExistNickname", method = RequestMethod.GET)
//	@ResponseBody
//	public Object isExistNickname(String nickname) {
//		try {
//			if (BeanUtil.isValid(nickname)) {
//				JydUser jydUser0 = jydUserService.isExistJydUserByNickname(nickname);
//				if (jydUser0 != null) {
//					return new ResponseBean(CommonConstant.FAIL_CODE_400, "昵称已存在");
//				} else {
//					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, "昵称不存在");
//				}
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
//			}
//		} catch (Exception e) {
//			LogUtil.logException("JydUserController isExistNickname has exception:", e);
//			e.printStackTrace();
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
//		}
//
//	}
//
//	/**
//	 * 判断用户是否存在
//	 * 
//	 * @param username
//	 * @return
//	 * @author felicia
//	 */
//	@RequestMapping(value = "isExistUsername", method = RequestMethod.GET)
//	@ResponseBody
//	public Object isExistUsername(String username) {
//		try {
//			if (BeanUtil.isValid(username)) {
//				JydUser jydUser0 = jydUserService.retrieveJydUserByName(username);
//				if (jydUser0 != null) {
//					return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_LOGINNAME_EXIST_ERROR);
//				} else {
//					return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_USER_NOEXIST_ERROR);
//				}
//			} else {
//				return new ResponseBean(CommonConstant.FAIL_CODE_400, CommonConstant.MSG_PARAMETER_EMPTY);
//			}
//		} catch (Exception e) {
//			LogUtil.logException("JydUserController isExistUsername has exception:", e);
//			e.printStackTrace();
//			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
//		}
//	}

}