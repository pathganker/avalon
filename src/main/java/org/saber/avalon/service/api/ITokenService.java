/**
 * 
 */
package org.saber.avalon.service.api;

/**   
 * @ClassName:  ITokenService   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月20日 下午6:03:48   
 *     
 */
public interface ITokenService {

	/**   
	 * @Title: checkToken   
	 * @Description: TODO
	 * @author: lijunliang 
	 * @date:   2019年1月20日 下午6:05:40   
	 * @param: @param diviceId
	 * @param: @param token
	 * @param: @return      
	 * @return: boolean      
	 * @throws   
	 */
	boolean checkToken(String diviceId, String token);


}
