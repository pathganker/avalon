/**   */
package org.saber.avalon.common.pojo.api;

/**   
 * 类名称:  ApiCodeEnum   
 * 描述: 接口返回码枚举   
 * 创建人: lijunliang
 * 创建时间:   2019年1月21日 下午1:02:32   
 */
public enum ApiCodeEnum {
	/** 成功 */
	SUCCESS("200"),
	/** 用户名和密码错误 请输入正确的用户名和密码 */
	USER_NAME_OR_PWD("101"),
	/** Token失效 请重新登陆 */
	TOKEN_TIME_OUT("102"),
	/** 缺少签名 请使用正确的签名 */
	TOKEN_LOST("103"),
	/** 错误参数 请输入正确参数 */
	TOKEN_WRONG("104"),
	/** 错误参数 请输入正确参数 */
	ARGS_WRONG("105"),
	/** 对该接口无权限 请申请使用该接口 */
	API_AUTHORITY("106"),
	/** 接口访问次数超限 请明天再试。（默认一天内最多访问1000次） */
	API_ACCESS_RATE_OUT("107"),
	/** 更换设备，请重新登陆 重新登录 */
	CHANGE_DEVICE("108"),
	/** 注册的邮箱不正确（不是邮箱） */
	REGIST_EMAIL_WRONG("109"),
	/** 邮箱已注册 */
	REGIST_EMAIL_EXIST("111"),
	/** 注册的手机号码不正确（不是手机号） */
	REGIST_MOBILEPHONE_WRONG("110"),
	/** 手机号已注册 */
	REGIST_MOBILEPHONE_EXIST("112"),
	/** 时间戳错误 */
	TIMESTAMP_ERROR("113"),
	/** 服务异常 稍后再试 */
	SERVICE_WRONG("201"),
	/** 参数没有问题没有先要的结果 */
	NO_RESULT("202"),
	/** 设备id为空 */
	DEVICE_ID_NULL("214"),
	/** 用户id错误 */
	USER_ID_ERROR("215");
	
	/** 错误码 */
	private String errorCode;

	private ApiCodeEnum(String code) {
		this.errorCode = code;
	}

	/**
	 * @return errorCode
	 */

	public String getErrorCode() {
		return errorCode;
	}
}
