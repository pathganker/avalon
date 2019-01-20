/**
 * 
 */
package org.saber.avalon.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.saber.avalon.pojo.Result;
import org.saber.avalon.service.api.ITokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;


/**   
 * @ClassName:  TokenInterceptor   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2018年10月25日 下午6:03:18   
 *     
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{
	/** 日志打印*/
	private static final Logger	LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);
	
	/** TOKEN标记名*/
	private static final String PARAMTER_TOKEN_NAME = "token";
	
	/** DIVICEID标记名*/
	private static final String PARAMTER_DIVICEID_NAME = "deviceId";
	
	/** TOKEN验证服务*/
	@Autowired
	private ITokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		Result rt= new Result();
		try {
			String token = request.getParameter(PARAMTER_TOKEN_NAME);
			String diviceId=request.getParameter(PARAMTER_DIVICEID_NAME);
			if (StringUtils.isNotBlank(token)&&StringUtils.isNotBlank(diviceId)) {
				try {
					if(tokenService.checkToken(diviceId, token)){
						return true;
					}else {
						rt.setCode(ApiCode.TOKEN_TIME_OUT.getErrorCode());//时间戳问题
						handlerReturnJSON(response,rt);
					}
				} catch (TokenException e) {
					rt.setCode(e.getEc().getErrorCode());//参数问题
					handlerReturnJSON(response,rt);
				LOGGER.info("token验证异常：{}",JSON.toJSONString(rt),e);
				}
			}else if (StringUtils.isBlank(token)) {
				rt.setCode(ApiCode.TOKEN_LOST.getErrorCode());//token丢失
				handlerReturnJSON(response,rt);
			}
			rt.setCode(ApiCode.DEVICE_ID_NULL.getErrorCode());//设备id为空
			handlerReturnJSON(response,rt);
			return false;
		} catch (Exception e) {
			rt.setCode(ApiCode.SERVICE_WRONG.getErrorCode());//token丢失
			handlerReturnJSON(response,rt);
			LOGGER.info("token验证异常：{}",JSON.toJSONString(rt),e);
			return false;
		}
	}
	/**
	 * 
	 * 方法名:  handlerReturnJSON  
	 * 描述:    TODO
	 * 创建人：	lijunliang
	 * 创建时间:  2019年1月11日下午2:15:54
	 * 修改时间:    
	 * 参数:  @param response
	 * 参数:  @param rt  
	 * 返回类型  void
	 */

	private void handlerReturnJSON(HttpServletResponse response, ResultVO rt) {
//		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(rt));
		} catch (IOException e) {
			LOGGER.info("返回登录验证失败异常", e);		
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}


}
