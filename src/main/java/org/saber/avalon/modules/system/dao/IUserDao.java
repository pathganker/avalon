/**
 * 
 */
package org.saber.avalon.modules.system.dao;

import java.util.List;
import java.util.Set;

import org.saber.avalon.modules.system.pojo.dos.UserDO;


/**   
 * @ClassName:  UserDao   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年2月2日 上午12:34:29   
 *     
 */
public interface IUserDao {
	
	List<UserDO> getAll();
	
	UserDO getOne(String id);
	
	void insert(UserDO user);
	
	void update(UserDO user);
	
	void delete(String id);
	
	UserDO queryUserByName(String username);
	
	Set<String> queryPermiUrlsByName(String username);
	
	Set<String> queryRoleIdByName(String username);
}
