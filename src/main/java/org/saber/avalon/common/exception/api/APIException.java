package org.saber.avalon.common.exception.api;

import java.io.Serializable;

import org.saber.avalon.common.pojo.api.ApiCodeEnum;




/**
 * 
 * @ClassName:  APIException   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2018年10月29日 下午10:36:47   
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
