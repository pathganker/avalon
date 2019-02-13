package org.saber.avalon.common.shiro;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.saber.avalon.common.pojo.Result;
import org.saber.avalon.common.pojo.api.ApiCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @ClassName:  KickoutSessionControlFilter   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2018年11月1日 上午10:12:18   
 *
 */
public class KickoutSessionControlFilter extends AccessControlFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KickoutSessionControlFilter.class);

	/** 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户 */
	private boolean kickoutAfter = false;
	/** 同一个帐号最大会话数 默认1 */
	private int maxSession = 1;
	/** session管理 */
	private SessionManager sessionManager;
	/** redis缓存管理 */
	private CacheManager cacheManager;
	/**
	 * 
	 */
	public KickoutSessionControlFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.AccessControlFilter#isAccessAllowed(javax
	 * .servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.
	 * servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			// 如果没有登录，直接进行之后的流程
			return true;
		}
		Session session = subject.getSession();
		String username = (String) subject.getPrincipal();
		Serializable sessionId = session.getId();
		Cache<String, Deque<Serializable>> cache = cacheManager.getCache("shiro-kickout-session");
		Deque<Serializable> deque = cache.get(username);
		if (deque == null) {
			deque = new LinkedList<Serializable>();
			cache.put(username, deque);
		}
		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId)
				&& session.getAttribute("kickout") == null) {
			deque.push(sessionId);
		}
		// 如果队列里的sessionId数超出最大会话数，开始踢人
		while (deque.size() > maxSession) {
			Serializable kickoutSessionId = null;
			if (kickoutAfter) { // 如果踢出后者
				kickoutSessionId = deque.removeFirst();
			} else { // 否则踢出前者
				kickoutSessionId = deque.removeLast();
			}
			try {
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null) {
					// 设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute("kickout", true);
				//	kickoutSession.setTimeout(0);
				}
			} catch (UnknownSessionException e) {
				LOGGER.debug("unknow session id:{}", kickoutSessionId);
			}  catch (Exception e) {// ignore exception
				LOGGER.info("并发登陆控制出现错误",e);
			}
		}
		//  更新kickout cache 
		cache.put(username, deque);
		if (session.getAttribute("kickout") != null) {
			Result rt = new Result();
			// 会话被踢出了
			try {
				subject.logout();
				rt.setCode(ApiCodeEnum.CHANGE_DEVICE);
			} catch (Exception e) { // ignore
				LOGGER.info("并发登陆控制出现错误",e);
				rt.setCode(ApiCodeEnum.SERVICE_WRONG);
			}
			saveRequest(request);
			handlerReturnJSON(response,rt);
			return false;
		}
		return true;
	}
	
	private void handlerReturnJSON(ServletResponse response, Result rt) {
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(rt));
		} catch (IOException e) {
			LOGGER.info("返回登录验证失败异常:{}", e);		
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}
	
	/**
	 * 使用cacheManager获取缓存对象
	 * @param cacheManager
	 */
	public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
	

	/**
	 * @param kickoutAfter
	 *            the kickoutAfter to set
	 */
	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	/**
	 * @param maxSession
	 *            the maxSession to set
	 */
	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	/**
	 * @param sessionManager
	 *            the sessionManager to set
	 */
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}
}
