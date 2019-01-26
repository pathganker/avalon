/**
 * 
 */
package org.saber.avalon.web.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.saber.avalon.config.api.ApiCodeEnum;
import org.saber.avalon.exception.api.TokenException;
import org.saber.avalon.pojo.Result;
import org.saber.avalon.service.api.ITokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**   
 * @ClassName:  LoginController   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月20日 下午1:23:13   
 *     
 */
@RestController
@RequestMapping("login")
public class LoginController {
	/** 日志 */
	private final static  Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	/** TOKEN标记名*/
	private static final String PARAMTER_TOKEN_NAME = "Auth-Token";
	/** DIVICEID标记名*/
	private static final String PARAMTER_DIVICEID_NAME = "Auth-Device";
	@Autowired
	private ITokenService tokenService;
	/**
	 * 
	 * @Title: login   
	 * @Description: TODO
	 * @author: lijunliang 
	 * @date:   2019年1月24日 上午12:55:46   
	 * @param: @param request
	 * @param: @param username
	 * @param: @param password
	 * @param: @return      
	 * @return: Result      
	 * @throws
	 */
	@RequestMapping(value="/signin")
	public Result login(HttpServletRequest request, String username, 
			String password) {
		Result result = new Result();
		if(request.getHeaderNames().hasMoreElements()) {
			LOGGER.info("headers:{}",request.getHeaderNames().nextElement());
		}
		if(request.getParameterNames().hasMoreElements()) {
			LOGGER.info("param:{}",request.getParameterNames().nextElement());
		}
		LOGGER.info("url:{}",request.getRequestURL());
		if("admin@saber.org".equals(username)&&"123456".equals(password)) {
			String deviceId = request.getHeader(PARAMTER_DIVICEID_NAME);
			try {
				String token = tokenService.insertToken(deviceId, username);
				result.setData(token);
				result.setCode(ApiCodeEnum.SUCCESS);
				return result;
			} catch (TokenException e) {
				LOGGER.error("创建Token错误:{}",e.getEc());
			}
			
		}
		result.setCode(ApiCodeEnum.USER_NAME_OR_PWD);
		return result;
	}
	/**
	 * 
	 * @Title: logout   
	 * @Description: TODO
	 * @author: lijunliang 
	 * @date:   2019年1月24日 上午1:01:35   
	 * @param: @param request
	 * @param: @return      
	 * @return: Result      
	 * @throws
	 */
	@RequestMapping(value="/signout")
	public Result logout(HttpServletRequest request) {
		Result rs = new Result();
		String token = request.getHeader(PARAMTER_TOKEN_NAME);
		try {
			tokenService.deleteToken(token);
		} catch (TokenException e) {
			LOGGER.error("删除Token错误:{}",e.getEc());
		}
		rs.setCode(ApiCodeEnum.SUCCESS);
		return rs;
	}
}
