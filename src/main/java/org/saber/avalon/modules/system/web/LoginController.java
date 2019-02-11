/**
 * 
 */
package org.saber.avalon.modules.system.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.saber.avalon.common.pojo.Result;
import org.saber.avalon.common.pojo.api.ApiCodeEnum;
import org.saber.avalon.modules.system.utils.CustomWebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	public Result login(HttpServletRequest request, String username, String password) {
		Result result = new Result();
		AuthenticationToken token = createToken(username, password, CustomWebUtils.getRemoteIpAddr(request));
		try {
			Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            result.setCode(ApiCodeEnum.SUCCESS);
            return result;
		}catch (AuthenticationException e) {
			LOGGER.info("登陆失败,对应的用户名为:{},{}",username,e);
			//登录错误，回到登录页面
			result.setCode(ApiCodeEnum.USER_NAME_OR_PWD);
        	return result;
		}
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
	public Result logout() {
		Result rs = new Result();
		Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        } catch (SessionException ise) {
        	LOGGER.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
		rs.setCode(ApiCodeEnum.SUCCESS);
		return rs;
	}
	
	private AuthenticationToken createToken(String username, String password, String host) {
		return new UsernamePasswordToken(username, password, true, host);
	}
}
