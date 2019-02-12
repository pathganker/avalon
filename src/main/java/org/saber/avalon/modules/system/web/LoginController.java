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
 * 
 * <p>类名称: LoginController </p> 
 * <p>描述: 登录控制器  </p>
 * <p>创建时间 : 2019年2月12日 下午12:02:33 </p>
 * @author lijunliang
 * @version 1.0
 *
 */
@RestController
@RequestMapping("login")
public class LoginController {
	/** 日志 */
	private final static  Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	/**
	 * 
	 * <p>方法名:  login </p> 
	 * <p>描述:    登录 </p>
	 * <p>创建时间:  2019年2月12日下午12:04:12 </p>
	 * @version 1.0
	 * @author lijunliang
	 * @param request
	 * @param username
	 * @param password
	 * @return  
	 * Result
	 */
	@RequestMapping(value="/signin")
	public Result login(HttpServletRequest request, String username, String password) {
		Result result = new Result();
		AuthenticationToken token = createToken(username, password, CustomWebUtils.getRemoteIpAddr(request));
		try {
			Subject subject = SecurityUtils.getSubject();
			String user = (String) subject.getPrincipal();
			if(null != user &&  user.equals(username)){
	            result.setData(token.getPrincipal());
	            result.setCode(ApiCodeEnum.API_AUTHORITY);
	            return result;
			}
            subject.login(token);
            LOGGER.info("登陆成功,对应的用户名为:{}",username);
            result.setData(token.getPrincipal());
            result.setCode(ApiCodeEnum.SUCCESS);
            return result;
		}catch (AuthenticationException e) {
			LOGGER.info("登陆失败,对应的用户名为:{}",username);
			//登录错误，返回失败码
			result.setCode(ApiCodeEnum.USER_NAME_OR_PWD);
        	return result;
		}
	}
	/**
	 * 
	 * <p>方法名:  logout </p> 
	 * <p>描述:    登出 </p>
	 * <p>创建时间:  2019年2月12日下午12:45:34 </p>
	 * @version 1.0
	 * @author lijunliang
	 * @return  
	 * Result
	 */
	@RequestMapping(value="/signout")
	public Result logout() {
		Result rs = new Result();
		Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
            LOGGER.info("用户{}登出系统",subject.getPrincipal());
        } catch (SessionException ise) {
        	LOGGER.debug("登出错误:{}", ise);
        }
		rs.setCode(ApiCodeEnum.SUCCESS);
		return rs;
	}
	/**
	 * 
	 * <p>方法名:  createToken </p> 
	 * <p>描述:    创建token </p>
	 * <p>创建时间:  2019年2月12日下午12:46:08 </p>
	 * @version 1.0
	 * @author lijunliang
	 * @param username
	 * @param password
	 * @param host
	 * @return  
	 * AuthenticationToken
	 */
	private AuthenticationToken createToken(String username, String password, String host) {
		return new UsernamePasswordToken(username, password, true, host);
	}
}
