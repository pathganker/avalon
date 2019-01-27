/**
 * 
 */
package org.saber.avalon.service.api.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.saber.avalon.config.api.ApiCodeEnum;
import org.saber.avalon.exception.api.TokenException;
import org.saber.avalon.service.api.ITokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;







/**   
 * @ClassName:  TokenServiceImpl   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月21日 下午11:25:09   
 *     
 */
@Service
public class TokenServiceImpl implements ITokenService{

	/** 日志打印*/
	private final static Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

	/** token的前缀 组成规则  + diviceId*/
	private final static String SABER_API_TOKEN_KEY_PREFIX = "saber.api.token.uid_";

	/** token的有效期24小时*/
	private final static Long SABER_API_TOKEN_ACTIVE_SECONED = (long) (24 * 60 * 60);
	
	/** redis持久化服务*/
	@Autowired
	private  StringRedisTemplate redis;
	/**   
	 * <p>Title: insertToken</p>   
	 * <p>Description: </p>   
	 * @param diviceId
	 * @param userId
	 * @return
	 * @throws TokenException   
	 * @see org.saber.avalon.service.api.ITokenService#insertToken(java.lang.String, java.lang.String)   
	 */
	@Override
	public String insertToken(String diviceId, String userId) throws TokenException {
		if (StringUtils.isNotBlank(diviceId) && userId !=null && !userId.equals("")) {
			try {
				String token = RandomStringUtils.random(32, diviceId + System.currentTimeMillis() + ""); //生成token
				String value=diviceId+"&"+userId;
				redis.opsForValue().set(SABER_API_TOKEN_KEY_PREFIX + token, value, SABER_API_TOKEN_ACTIVE_SECONED, TimeUnit.SECONDS); //保存token
				return token;
			} catch (Exception e) {
				LOGGER.error("redis访问异常{}{}", e, diviceId);
				throw new TokenException(ApiCodeEnum.SERVICE_WRONG); //发生异常返回异常信息
			} 
		} else {
			if(StringUtils.isBlank(diviceId)){
				throw new TokenException(ApiCodeEnum.DEVICE_ID_NULL); //设备id不存在
			}else{
				throw new TokenException(ApiCodeEnum.USER_ID_ERROR); //用户id错误
			}
		}
	}

	/**   
	 * <p>Title: checkToken</p>   
	 * <p>Description: </p>   
	 * @param deviceId
	 * @param token
	 * @return
	 * @throws TokenException   
	 * @see org.saber.avalon.service.api.ITokenService#checkToken(java.lang.String, java.lang.String)   
	 */
	@Override
	public boolean checkToken(String deviceId, String token) throws TokenException {
		if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(token)) {
			String tk="";
			String device="";
			try {
				tk = redis.opsForValue().get(SABER_API_TOKEN_KEY_PREFIX + token); //获取保存的token
				LOGGER.info("checkToken start:{},diviceId:{}", token, deviceId);				
			}catch (Exception e) {
				LOGGER.error("redis访问异常{}", token);
				throw new TokenException(ApiCodeEnum.SERVICE_WRONG);
			} 
			if(StringUtils.isNotBlank(tk)){
				device=tk.split("&")[0]; //获取返回的结果中的DIVICEID
			}
			if(StringUtils.isNotBlank(device)){					
				if (device.trim().equals(deviceId.trim())) { //TOKEN和redis中的一致则返回存在
					redis.opsForValue().set(SABER_API_TOKEN_KEY_PREFIX + token,tk, SABER_API_TOKEN_ACTIVE_SECONED, TimeUnit.SECONDS);
					LOGGER.info("checkToken end:{},diviceId:{}", token, deviceId);	
					return true;
				} else {
					LOGGER.error("token失效参数 设备id：{};输入token:{}", deviceId, token);
					throw new TokenException(ApiCodeEnum.CHANGE_DEVICE); //TOKEN超时
				}
			}else{
				LOGGER.warn("token失效参数 设备id：{};输入token:{}", deviceId, token);
				return false;
			}
		} else if (StringUtils.isBlank(token)) {
			LOGGER.warn("token失效参数 设备id：{};输入token:{}", deviceId, token);
			return false;
		} else {
			LOGGER.warn("token失效参数 设备id：{};输入token:{}", deviceId, token);
			return false;
		}
	}

	/**   
	 * <p>Title: requestUserIdByToken</p>   
	 * <p>Description: </p>   
	 * @param token
	 * @return
	 * @throws TokenException   
	 * @see org.saber.avalon.service.api.ITokenService#requestUserIdByToken(java.lang.String)   
	 */
	@Override
	public String requestUserIdByToken(String token) throws TokenException {
		if (StringUtils.isNotBlank(token)) {
			try {
				String tk = redis.opsForValue().get(SABER_API_TOKEN_KEY_PREFIX + token); //获取保存的token
				if (StringUtils.isNotBlank(tk)&&tk.contains("&") ) { //TOKEN和redis中的一致则返回存在
					redis.opsForValue().set(SABER_API_TOKEN_KEY_PREFIX + token, tk, SABER_API_TOKEN_ACTIVE_SECONED, TimeUnit.SECONDS);
					return tk.split("&")[1];
				} else {
					LOGGER.error("获取token对应的用户信息异常token:{}", token);
					throw new TokenException(ApiCodeEnum.TOKEN_TIME_OUT); //TOKEN超时
				}
			} catch (Exception e) {
				LOGGER.error("获取token对应的用户信息异常token:{}{}", e, token);
				throw new TokenException(ApiCodeEnum.SERVICE_WRONG);
			} 
		}
		return null;
	}

	/**   
	 * <p>Title: deleteToken</p>   
	 * <p>Description: </p>   
	 * @param token
	 * @return
	 * @throws TokenException   
	 * @see org.saber.avalon.service.api.ITokenService#deleteToken(java.lang.String)   
	 */
	@Override
	public boolean deleteToken(String token) throws TokenException {
		if(StringUtils.isNotBlank(token)) {
			try {
				String tk = redis.opsForValue().get(SABER_API_TOKEN_KEY_PREFIX + token); //获取保存的token
				if (StringUtils.isNotBlank(tk)) { 
					redis.delete(SABER_API_TOKEN_KEY_PREFIX + token);
					return true;
				}else {
					LOGGER.error("获取token对应的用户信息异常token:{}", token);
					throw new TokenException(ApiCodeEnum.TOKEN_TIME_OUT); //TOKEN超时
				}
			}catch (Exception e) {
				LOGGER.error("获取token对应的用户信息异常token:{}{}", e, token);
				throw new TokenException(ApiCodeEnum.SERVICE_WRONG);
			}
		}
		return false;
	}

}
