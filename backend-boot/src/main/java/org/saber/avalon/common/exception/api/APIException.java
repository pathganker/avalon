package org.saber.avalon.common.exception.api;

import java.io.Serializable;

import org.saber.avalon.common.pojo.api.ApiCodeEnum;




/**
 * 
 * <p>类名称: APIException </p> 
 * <p>描述: TODO  </p>
 * <p>创建时间 : 2019年2月14日 下午9:06:09 </p>
 * @author lijunliang
 * @version 1.0
 *
 */
  	
@SuppressWarnings("serial")
public abstract class APIException  extends Exception implements Serializable{
	
	/** 异常错误码 */
	private ApiCodeEnum ec;
	
	public ApiCodeEnum getEc() {
		return ec;
	}
	public void setEc(ApiCodeEnum ec) {
		this.ec = ec;
	}
	public APIException() {
		super();
	}
	public APIException(ApiCodeEnum ec) {
		super();
		this.ec = ec;
	}
	public APIException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public APIException(String message, Throwable cause) {
		super(message, cause);
	}
	public APIException(String message) {
		super(message);
	}
	public APIException(Throwable cause) {
		super(cause);
	}
	

}
