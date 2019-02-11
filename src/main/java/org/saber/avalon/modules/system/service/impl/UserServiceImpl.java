/**   */
package org.saber.avalon.modules.system.service.impl;

import java.util.Set;

import org.saber.avalon.modules.system.pojo.dtos.UserDTO;
import org.saber.avalon.modules.system.service.UserService;
import org.springframework.stereotype.Service;

/**   
 * 类名称:  UserServiceImpl   
 * 描述: 用户服务实现   
 * 创建人: lijunliang
 * 创建时间:   2019年2月11日 上午11:52:24   
 */
@Service
public class UserServiceImpl implements UserService{

	/** (non-Javadoc)
	 * @see org.saber.avalon.modules.system.service.UserService#requestUserByName(java.lang.String)
	 */
	@Override
	public UserDTO requestUserByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/** (non-Javadoc)
	 * @see org.saber.avalon.modules.system.service.UserService#requestPermiUrlsByName(java.lang.String)
	 */
	@Override
	public Set<String> requestPermiUrlsByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/** (non-Javadoc)
	 * @see org.saber.avalon.modules.system.service.UserService#requestRoleIdByName(java.lang.String)
	 */
	@Override
	public Set<String> requestRoleIdByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
