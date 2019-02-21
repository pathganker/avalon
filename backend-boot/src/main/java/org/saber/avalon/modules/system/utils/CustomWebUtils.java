/**
 * 
 */
package org.saber.avalon.modules.system.utils;

import javax.servlet.http.HttpServletRequest;

/**   
 * @ClassName:  WebUtils   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2018年10月31日 下午5:45:09   
 *     
 */
public class CustomWebUtils {
	
	/**
	 * 
	 * @Title: getRemoteIpAddr   
	 * @Description: TODO
	 * @author: lijunliang 
	 * @date:   2018年11月14日 下午7:39:23   
	 * @param: @param request
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
