/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.constant;

/**
 * @author Microcat
 * @version 1.0
 */
public class CommonConstant {

	// 常量
	public static final String CONSTANT_STRING_GET = "get";
	public static final String CONSTANT_STRING_SET = "set";
	public static final String CONSTANT_STRING_STATUS = "status";
	public static final String CONSTANT_STRING_MESSAGE = "message";
	public static final String CONSTANT_STRING_RESULT = "result";
	public static final String CONSTANT_STRING_INT = "int";
	public static final String CONSTANT_STRING_VARCHAR = "varchar";
	public static final String CONSTANT_STRING_DATETIME = "datetime";
	public static final String CONSTANT_STRING_FLOAT = "float";
	public static final String CONSTANT_STRING_DOUBLE = "double";
	public static final String CONSTANT_STRING_LONGTEXT = "longtext";

	public static final String SUCCESS = "success";
	// 文件
	public static final String CONSTANT_STRING_FILE = "file";
	public static final String CONSTANT_STRING_FILENAME = "fileName";
	public final static String SYMBOL_SLASH = "\\";

	// 数据库备份地址
	public static final String DATA_BACKUP_PATH = "D:\\backupDatabase";
	public static final String DATA_BACKUP_SUFFIX = ".sql";

	// 编码
	public static final String ENCORD_UTF8 = "utf-8";
	public static final String ENCORD_ISO_8859_1 = "iso-8859-1";

	// 分页默认
	public static final int CONSTANT_PAGER_NO = 1;
	public static final int CONSTANT_PAGER_SIZE = 10;

	// Custom token key constant
	public static final String CUSTOM_TOKEN_KEY_CONSTANT = "messcat2016";

	// 公用返回码
	public static final String SUCCESS_CODE_200 = "200";
	public static final String SUCCESS_CODE_300 = "300";
	public static final String FAIL_CODE_400 = "400";
	public static final String EXCEPTION_CODE_500 = "500";

	// 通用STATUS状态
	public static final String STATUS_ZERO = "0";
	public static final String STATUS_ONE = "1";
	public static final String STATUS_TWO = "2";

	// 通用提示信息
	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_FAIL = "操作失败";
	public static final String MSG_EXCEPTION = "操作异常";
	public static final String MSG_EXCEPTION_POPUP = "系统错误，请稍后再试";
	public static final String MSG_NO_LOGIN = "请先登录";
	public static final String MSG_LOGIN_NAME_EMPTY = "登录名为空";
	public static final String MSG_EXECUTE_SUCCESS = "执行成功";
	public static final String MSG_FIND_SUCCESS = "查询成功";
	public static final String MSG_FIND_FAIL = "查询失败";
	public static final String MSG_ADD_SUCCESS = "添加成功";
	public static final String MSG_ADD_FAIL = "添加失败";
	public static final String MSG_UPDATE_SUCCESS = "修改成功";
	public static final String MSG_UPDATE_FAIL = "修改失败";
	public static final String MSG_DELETE_SUCCESS = "删除成功";
	public static final String MSG_DELETE_FAIL = "删除失败";
	public static final String MSG_DATA_EXCEPT = "数据异常";
	public static final String MSG_PARAMETER_EMPTY = "参数为空";
	public static final String MSG_INPUT_NOEMPTY = "输入不能为空";
	public static final String MSG_INPUT_FORMAT_ERROR = "输入格式不正确";
	public static final String MSG_BATCH_ERROR = "批量处理出现错误";
	public static final String MSG_UPDATE_PEMMISSION_FAIL = "您没有修改权限";
	// 下载
	public static final String MSG_DOWNLOAD_ERROR = "下载出现错误";

	// 账号
	public static final String MSG_USER_NOEXIST_ERROR = "用户不存在";
	public static final String MSG_LOGINNAME_EXIST_ERROR = "此用户名已存在";
	public static final String MSG_LOGINNAME_FORMAT_ERROR = "用户名必须由6-20位的字母和数字组成";
	public static final String MSG_LOGINNAME_FIRST_ERROR = "用户名首位必须是字母";

	// 密码
	public static final String MSG_PASSWORD_FORMAT_ERROR = "密码必须由6-20位的字母，数字组成";
	public static final String MSG_PASSWORD_EXIST_SPECIAL_ERROR = "密码中不能存在特殊字符";
	public static final String MSG_LOGINNAME_PASSWORD_NULL_ERROR = "账号或密码不能为空";
	public static final String MSG_PASSWORD_ERROR = "登录密码错误";
	public static final String MSG_ANSWER_ERROR = "答案错误";
	public static final String MSG_CODE_ERROR = "验证吗错误";

	// 修改密码
	public static final String MSG_UPDATE_PASSWORD_SUCCESS = "修改密码成功";
	public static final String MSG_UPDATE_PASSWORD_FAIL = "修改密码失败";
	public static final String MSG_UPDATE_PASSWORD_OLD_ERROR = "旧密码输入错误";
	public static final String MSG_UPDATE_PASSWORD_SAMEPASSW_ERROR = "您设置的新密码和旧密码相同";

	// 权限类型
	public static final String PERMISSION_TYPE_MENU = "0";
	public static final String PERMISSION_TYPE_FRONT = "1";
	public static final String PERMISSION_TYPE_OPT = "2";

	// 角色
	public static final String MSG_ROLE_EXCEPTION = "不存在角色";
	// 评论
	public static final String MSG_COMMENT_FAIL = "评论或回复失败";

	// 点赞
	public static final String MSG_DOLIKE_REPEAT_FAIL = "您已经点过赞罗";
	public static final String MSG_DOLIKE_FAIL = "点赞失败";

	// 收藏
	public static final String MSG_FAVORITES_SUCCESS = "收藏成功";
	public static final String MSG_MSG_FAVORITES_EXIST_FAIL = "收藏列表已存在相关信息";
	public static final String MSG_MSG_FAVORITES_FAIL = "收藏失败";

	public static final String MSG_FAVORITES_DELETE_SUCCESS = "已成功取消收藏";
	public static final String MSG_FAVORITES_DELETE_FAIL = "无法取消收藏";

	// 活动
	public static final String MSG_ACTIVITY_TIME_ERROR = "操作失败，起始时间必须小于截止时间";
	public static final String MSG_ACTIVITY_ENROLL_TIME_ERROR = "操作失败，报名时间与活动时间的配置有误";
	public static final String MSG_ACTIVITY_ENROLL_FAIL = "报名失败";
	public static final String MSG_ACTIVITY_ENROLL_SUCCESS = "报名成功";

	// 举报
	public static final String MSG_REPORT_SUCCESS = "举报成功";
	public static final String MSG_REPORT_FAIL = "举报失败";
	public static final String MSG_REPORT_LENGTH_FAIL = "举报原因必须由5-50个字构成";
	public static final String MSG_REPORT_REPEAT_FAIL = "您已提交过该举报信息，系统会尽快处理";

}
