/**
 * 
 */
package org.saber.avalon.modules.system.service;

import java.util.Set;

import org.saber.avalon.modules.system.pojo.dtos.UserDTO;

/**   
 * @ClassName  UserService   
 * @Description	TODO
 * @author lijunliang 
 * @date  2019年2月2日 下午4:05:30   
 *     
 */
public interface UserService {

	/**   
	 * @Title requestUserByName   
	 * @Description TODO
	 * @author lijunliang 
	 * @date   2019年2月2日 下午4:22:38   
	 * @param @param username
	 * @param @return      
	 * @return UserDTO      
	 * @throws   
	 */
	UserDTO requestUserByName(String username);

	/**
	 * 
	 * @Title requestPermiUrlsByName   
	 * @Description TODO
	 * @author lijunliang 
	 * @date   2019年2月2日 下午4:36:37   
	 * @param @param username
	 * @param @return      
	 * @return Set<String>      
	 * @throws
	 */
	Set<String> requestPermiUrlsByName(String username);

	/**   
	 * @Title requestRoleIdByName   
	 * @Description TODO
	 * @author lijunliang 
	 * @date   2019年2月2日 下午4:42:45   
	 * @param @param username
	 * @param @return      
	 * @return Set<String>      
	 * @throws   
	 */
	Set<String> requestRoleIdByName(String username);

}
