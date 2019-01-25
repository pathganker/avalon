/**
 * 
 */
package org.saber.avalon.web.controller;

import javax.servlet.http.HttpServletRequest;


/**   
 * @ClassName:  BaseController   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月25日 上午12:12:04   
 *     
 */
public class BaseController {
	/** TOKEN标记名*/
	private static final String PARAMTER_TOKEN_NAME = "Auth-Token";
	/** DIVICEID标记名*/
	private static final String PARAMTER_DIVICEID_NAME = "Auth-Device";
	
	protected String getDevice(HttpServletRequest request) {
		return request.getHeader(PARAMTER_DIVICEID_NAME);
	}
	
	protected String getToken(HttpServletRequest request) {
		return request.getHeader(PARAMTER_TOKEN_NAME);
	}
}
