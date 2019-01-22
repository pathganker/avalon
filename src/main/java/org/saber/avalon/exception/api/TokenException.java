package org.saber.avalon.exception.api;


import org.saber.avalon.config.api.ApiCodeEnum;

/**
 * 
 * @ClassName:  TokenException   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2018年10月26日 上午10:25:35   
 *
 */
public class TokenException extends APIException {

	/** @Fields serialVersionUID: */
	private static final long serialVersionUID = 4358396461443001160L;

	public TokenException() {
		super();
	}

	public TokenException(ApiCodeEnum ec) {
		super(ec);
	}

	public TokenException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenException(String message) {
		super(message);
	}

	public TokenException(Throwable cause) {
		super(cause);
	}
	
}
