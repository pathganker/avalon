/**
 * 
 */
package org.saber.avalon.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.saber.avalon.common.exception.api.TokenException;
import org.saber.avalon.common.pojo.Result;
import org.saber.avalon.common.pojo.api.ApiCodeEnum;
import org.saber.avalon.common.service.api.ITokenService;
import org.saber.avalon.common.utils.HandlerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;

/**   
 * @ClassName  TokenFilter   
 * @Description	TODO
 * @author lijunliang 
 * @date  2019年2月12日 上午12:01:11   
 *     
 */
public class TokenFilter extends AccessControlFilter{
	
	private ITokenService tokenService;
	
	/** 日志打印*/
	private static final Logger	LOGGER = LoggerFactory.getLogger(TokenFilter.class);
	/** TOKEN标记名*/
	private static final String PARAMTER_TOKEN_NAME = "Auth-Token";
	/** DIVICEID标记名*/
	private static final String PARAMTER_DIVICEID_NAME = "Auth-Device";

	private static final String OPTIONS = "OPTIONS";
	public TokenFilter() {
	}

	/**   
	 * <p>Title: isAccessAllowed</p>   
	 * <p>Description: </p>   
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 * @throws Exception   
	 * @see org.apache.shiro.web.filter.AccessControlFilter#isAccessAllowed(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)   
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Result rt= new Result();
		if(OPTIONS.equals(httpRequest.getMethod())) {
			return true;
		}
		try {
			String token = httpRequest.getHeader(PARAMTER_TOKEN_NAME);
			String diviceId=httpRequest.getHeader(PARAMTER_DIVICEID_NAME);
			if (StringUtils.isNotBlank(token)&&StringUtils.isNotBlank(diviceId)) {
				try {
					if(this.tokenService.checkToken(diviceId, token)){
						return true;
					}else {
						rt.setCode(ApiCodeEnum.TOKEN_TIME_OUT);//时间戳问题
						HandlerUtils.handlerReturnJSON(response,rt);
						return false;
					}
				} catch (TokenException e) {
					rt.setCode(e.getEc());//参数问题
					HandlerUtils.handlerReturnJSON(response,rt);
					return false;
				}
			}else if (StringUtils.isBlank(token)) {
				rt.setCode(ApiCodeEnum.TOKEN_LOST);//token丢失
				HandlerUtils.handlerReturnJSON(response,rt);
				LOGGER.info("token验证错误:{}",JSON.toJSONString(rt));
				return false;
			}
			rt.setCode(ApiCodeEnum.DEVICE_ID_NULL);//设备id为空
			HandlerUtils.handlerReturnJSON(response,rt);
			return false;
		} catch (Exception e) {
			rt.setCode(ApiCodeEnum.SERVICE_WRONG);//token丢失
			HandlerUtils.handlerReturnJSON(response,rt);
			LOGGER.info("token验证异常:{}",JSON.toJSONString(rt),e);
			return false;
		}
	}

	/**   
	 * <p>Title: onAccessDenied</p>   
	 * <p>Description: </p>   
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception   
	 * @see org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)   
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		LOGGER.trace("access denied, token:{},uri:{}",WebUtils.toHttp(request).getHeader(PARAMTER_TOKEN_NAME), getPathWithinApplication(request));
		return false;
	}

	public ITokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(ITokenService tokenService) {
		this.tokenService = tokenService;
	}

}
