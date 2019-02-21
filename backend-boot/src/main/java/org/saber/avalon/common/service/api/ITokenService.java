package org.saber.avalon.common.service.api;

import org.saber.avalon.common.exception.api.TokenException;

/**
 * 
 * @ClassName:  ITokenService   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月21日 下午11:23:47   
 *
 */
  	
public interface ITokenService {
	
	 
	/**
	 * 
	 * @Title: insertToken   
	 * @Description: TODO
	 * @author: lijunliang 
	 * @date:   2019年1月21日 下午11:24:05   
	 * @param: @param diviceId
	 * @param: @param userId
	 * @param: @return
	 * @param: @throws TokenException      
	 * @return: String      
	 * @throws
	 */
	  	
	public String insertToken(String diviceId, String userId)throws TokenException;
	 
	/**
	 * 
	 * @Title: checkToken   
	 * @Description: TODO
	 * @author: lijunliang 
	 * @date:   2019年1月21日 下午11:24:12   
	 * @param: @param deviceId
	 * @param: @param token
	 * @param: @return
	 * @param: @throws TokenException      
	 * @return: boolean      
	 * @throws
	 */
	  	
	public boolean  checkToken(String deviceId, String token) throws TokenException;
	 
	/**
	 * 
	 * @Title: requestUserIdByToken   
	 * @Description: TODO
	 * @author: lijunliang 
	 * @date:   2019年1月21日 下午11:24:18   
	 * @param: @param token
	 * @param: @return
	 * @param: @throws TokenException      
	 * @return: String      
	 * @throws
	 */
	  	
	public String requestUserIdByToken(String token) throws TokenException;
	/**
	 * 
	 * @Title: deleteToken   
	 * @Description: TODO
	 * @author: lijunliang 
	 * @date:   2019年1月22日 上午12:36:53   
	 * @param: @param token
	 * @param: @return
	 * @param: @throws TokenException      
	 * @return: boolean      
	 * @throws
	 */
	public boolean deleteToken(String token) throws TokenException;
}
