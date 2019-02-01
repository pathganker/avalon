/**
 * 
 */
package org.saber.avalon.modules.system.dao;

import java.util.List;

import org.saber.avalon.modules.system.pojo.UserDO;

/**   
 * @ClassName:  UserDao   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年2月2日 上午12:34:29   
 *     
 */
public interface UserDao {
	
	List<UserDO> getAll();
	
	UserDO getOne(String id);
	
	void insert(UserDO user);
	
	void update(UserDO user);
	
	void delete(String id);
}
