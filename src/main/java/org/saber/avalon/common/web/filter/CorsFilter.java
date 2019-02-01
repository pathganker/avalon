/**
 * 
 */
package org.saber.avalon.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**   
 * @ClassName:  CorsFilter   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月27日 上午12:47:05   
 *     
 */
@Component
public class CorsFilter implements Filter{

	/**   
	 * <p>Title: init</p>   
	 * <p>Description: </p>   
	 * @param filterConfig
	 * @throws ServletException   
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)   
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	/**   
	 * <p>Title: doFilter</p>   
	 * <p>Description: </p>   
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException   
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)   
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
        //response.reset(); 
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACES");
        res.addHeader("Access-Control-Max-Age", "3600");
        res.addHeader("Access-Control-Allow-Headers", "Auth-Token, Auth-Device");
        chain.doFilter(request, response);
	}

	/**   
	 * <p>Title: destroy</p>   
	 * <p>Description: </p>      
	 * @see javax.servlet.Filter#destroy()   
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
