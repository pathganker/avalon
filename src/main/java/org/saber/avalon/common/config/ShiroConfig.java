/**
 * 
 */
package org.saber.avalon.common.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.saber.avalon.common.service.api.impl.TokenServiceImpl;
import org.saber.avalon.common.shiro.KickoutSessionControlFilter;
import org.saber.avalon.common.shiro.TokenFilter;
import org.saber.avalon.common.shiro.UrlFilter;
import org.saber.avalon.common.shiro.UserRealm;
import org.saber.avalon.common.shiro.serializer.FastJsonRedisSerializer;
import org.saber.avalon.modules.system.service.impl.UserServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**   
 * @ClassName  ShiroConfig   
 * @Description	shiro配置
 * @author lijunliang 
 * @date  2019年2月2日 下午3:46:44   
 *     
 */
@Configuration
public class ShiroConfig {
	
	//凭证管理
	public HashedCredentialsMatcher credentialsMatcher(){
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}
	//用户服务
	@Bean
	public UserServiceImpl userService(){
		return new UserServiceImpl();
	}
	//realm
	public UserRealm userRealm(){
		UserRealm userRealm = new UserRealm();
		userRealm.setUserService(userService());
		userRealm.setCredentialsMatcher(credentialsMatcher());
		userRealm.setCachingEnabled(true);
		userRealm.setRedisCacheManager(cacheManager());
		userRealm.setAuthenticationCachingEnabled(true);
		userRealm.setAuthenticationCacheName("authenticationCache");
		return userRealm;
	}

	
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory){
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//		redisTemplate.setConnectionFactory(connectionFactory);
//		redisTemplate.setKeySerializer(keySerializer());
//		redisTemplate.setValueSerializer(valueSerializer());
//		redisTemplate.setHashValueSerializer(valueSerializer());
//		return redisTemplate;
//	}
//	private RedisSerializer<String> keySerializer(){
//		return new StringRedisSerializer();
//	}
//	private RedisSerializer<Object> valueSerializer(){
//		return new FastJsonRedisSerializer<Object>(Object.class);
//	}
//	
//	@Bean
//	public CacheManager redisManger(RedisConnectionFactory connectionFactory){
//		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30L))
//				.disableCachingNullValues()
//				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(null))
//				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(null));
//		return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
//				.cacheDefaults(redisCacheConfiguration).build();
//	}
	/*shiro-redis */
	//读取redis配置
	@Bean
	@ConfigurationProperties(prefix="spring.redis")
	public Properties redisProperties() {
		return new Properties()	;
	}
	//redis管理
	public RedisManager redisManager(){
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(redisProperties().getProperty("host")+":"+redisProperties().getProperty("port"));
		redisManager.setPassword(redisProperties().getProperty("password"));
		redisManager.setTimeout(Integer.parseInt(redisProperties().getProperty("timeout")));
		redisManager.setDatabase(Integer.parseInt(redisProperties().getProperty("database")));
		return redisManager;
	}
	//session dao
	public RedisSessionDAO sessionDao(){
		RedisSessionDAO sessionDao = new RedisSessionDAO();
		sessionDao.setRedisManager(redisManager());
		return sessionDao;
	}
	//redis缓存
	public  RedisCacheManager cacheManager(){
		RedisCacheManager cacheManager = new RedisCacheManager();
		cacheManager.setRedisManager(redisManager());
		return cacheManager;
	}
	//cookie
	public SimpleCookie sessionIdCookie(){
		SimpleCookie sessionIdCookie = new SimpleCookie();
		sessionIdCookie.setName("sid");
		sessionIdCookie.setHttpOnly(true);
		sessionIdCookie.setMaxAge(180000);
		return sessionIdCookie;
	}
	//session管理
	public DefaultWebSessionManager sessionManager(){
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(sessionDao());
		sessionManager.setGlobalSessionTimeout(3600*1000*24);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		return sessionManager;
	}
	//安全管理
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm());
		securityManager.setSessionManager(sessionManager());
		//securityManager.setCacheManager(cacheManager());
		return securityManager;
	}
	
	//并发登录人数控制 
	private KickoutSessionControlFilter kickoutFilter(){
		KickoutSessionControlFilter kickoutFilter = new KickoutSessionControlFilter();
		kickoutFilter.setSessionManager(sessionManager());
		kickoutFilter.setKickoutAfter(false);
		kickoutFilter.setMaxSession(1);
		kickoutFilter.setCacheManager(cacheManager());
		return kickoutFilter;
	}
	
	//URL权限验证
	private UrlFilter urlFilter(){
		UrlFilter urlFilter = new UrlFilter();
		urlFilter.setUserService(userService());
		return urlFilter;
	}
	//匿名权限
	private AnonymousFilter anon(){
		return new AnonymousFilter(); 
	}
	
	private TokenServiceImpl tokenService() {
		return new TokenServiceImpl();
	}
	//Token验证
	private TokenFilter tokenFilter() {
		TokenFilter tokenFilter = new TokenFilter();
		tokenFilter.setTokenService(tokenService());
		return tokenFilter;
	}
	//web过滤器
	@Bean
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager());
		Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
		filterMap.put("kickout", kickoutFilter());
		filterMap.put("token", tokenFilter());
		filterMap.put("url", urlFilter());
		filterMap.put("anon", anon());
		shiroFilter.setFilters(filterMap);
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/login/**", "anon");
		filterChainDefinitionMap.put("/**", "kickout,token,url");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilter;
	}
	
}
