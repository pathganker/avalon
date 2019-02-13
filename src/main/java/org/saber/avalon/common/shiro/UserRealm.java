package org.saber.avalon.common.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisCacheManager;
import org.saber.avalon.common.shiro.realm.AuthorizingRealm;
import org.saber.avalon.common.shiro.serializer.FastJsonRedisSerializer;
import org.saber.avalon.modules.system.pojo.dtos.UserDTO;
import org.saber.avalon.modules.system.service.IUserService;


/**
 * 
 * @ClassName:  UserRealm   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2018年11月1日 上午10:10:25   
 *
 */
public class UserRealm extends AuthorizingRealm {

	/** 用户管理Service */
    private IUserService userService;
    
    public UserRealm() {
    }
    
	 /**
	  * (non-Javadoc)
	  * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	  */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.requestRoleIdByName(username));
        authorizationInfo.setStringPermissions(userService.requestPermiUrlsByName(username));
        // 上面这段权限，会保存在cache里
        return authorizationInfo;
    }

	/**
	 * (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();

        UserDTO user = userService.requestUserByName(username);

        if(user == null || StringUtils.isBlank(user.getUsername())) {
            throw new UnknownAccountException();//没找到帐号
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		user.getUsername(), //用户名
        		user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
    
    
    /**
     * (non-Javadoc)
     * @see org.apache.shiro.realm.AuthorizingRealm#getAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            return null;
        }
        AuthorizationInfo info = null;
        Cache<Object, AuthorizationInfo> cache = super.getAuthorizationCache();
        if (cache != null) {
            Object key = getAuthorizationCacheKey(principals);
            info = cache.get(key);
        }
        if (info == null) {
            // Call template method if the info was not found in a cache
            info = doGetAuthorizationInfo(principals);
            // If the info is not null and the cache has been created, then cache the authorization info.
            if (info != null && cache != null) {
                Object key = getAuthorizationCacheKey(principals);
                cache.put(key, info);
            }
        }
        return info;
    }
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
    
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    public void setRedisCacheManager(RedisCacheManager cacheManager){
    	cacheManager.setValueSerializer(new FastJsonRedisSerializer<SimpleAuthenticationInfo>(SimpleAuthenticationInfo.class));
    	this.setCacheManager(cacheManager);
    }
}
