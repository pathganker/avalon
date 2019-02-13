package org.saber.avalon.common.shiro;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.saber.avalon.common.pojo.Result;
import org.saber.avalon.common.pojo.api.ApiCodeEnum;
import org.saber.avalon.common.utils.HandlerUtils;
import org.saber.avalon.modules.system.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 
 * @ClassName  UrlFilter   
 * @Description 资源路径过滤
 * @author lijunliang 
 * @date   2018年11月1日 上午10:12:24   
 *
 */
public class UrlFilter extends AccessControlFilter {

    /** 当前用户有访问权限的url列表在session 中的key */
    private final static String PERMISSIONS_URL_SESSION_KEY = "pmUrlsKey";
    
	private final Logger logger = LoggerFactory.getLogger(UrlFilter.class);
	
	/** 用户管理 */
	private IUserService userService;
	
	/**
	 * 
	 */
	public UrlFilter() {
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
		ServletResponse response, Object mappedValue) throws Exception {
    	Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			// 如果没有登录，直接进行之后的流程
			return false;
		}
        //根据username取到权限url集合
    	@SuppressWarnings("unchecked")
        Set<String> urls = (Set<String>)  subject.getSession().getAttribute(PERMISSIONS_URL_SESSION_KEY);
    	if (urls == null) {
    		urls = (userService.requestPermiUrlsByName((String) subject.getPrincipal()) == null) ? new HashSet<String>():userService.requestPermiUrlsByName((String) subject.getPrincipal());
            subject.getSession().setAttribute(PERMISSIONS_URL_SESSION_KEY, urls);
        }
        boolean hasPermission = urls == null ? false : checkPermission(urls, request);
        if (!hasPermission) {
        	Result rt = new Result();
        	rt.setCode(ApiCodeEnum.API_AUTHORITY);
        	HandlerUtils.handlerReturnJSON(response, rt);
            return false;
		} else {
			return true;
		}
        
    }
	
	/**
	 * 
	 * @Title checkPermission   
	 * @Description TODO
	 * @author lijunliang 
	 * @date   2019年2月12日 上午12:04:07   
	 * @param @param urls
	 * @param @param request
	 * @param @return
	 * @param @throws IOException      
	 * @return boolean      
	 * @throws
	 */
	private boolean checkPermission(Set<String> urls, ServletRequest request) throws IOException {
	    // 判断首页
        if (pathsMatch("/index", request)) {
            return true;
        }
        String uri = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
        if ("/".equalsIgnoreCase(uri)) {
            return true;
        }
        // 再判断其它权限
	    for (String url : urls) {
    		if (url == null || "".equals(url) ) {
    			continue;
    		}
        	if (!url.endsWith("/")) {
				url = url + "/";
			}
        	//权限url匹配	
        	if (pathsMatch("/" + url + "**", request)) {
				return true;
			}
		}
    	return false;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		logger.trace("access denied, user:{},uri:{}", getSubject(request, response).getPrincipal(), getPathWithinApplication(request));
		return false;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
}
