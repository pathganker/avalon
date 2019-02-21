/**   */
package org.saber.avalon.modules.system.service.impl;

import java.util.Set;

import org.saber.avalon.modules.system.dao.IUserDao;
import org.saber.avalon.modules.system.pojo.dos.UserDO;
import org.saber.avalon.modules.system.pojo.dtos.UserDTO;
import org.saber.avalon.modules.system.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**   
 * 类名称:  UserServiceImpl   
 * 描述: 用户服务实现   
 * 创建人: lijunliang
 * 创建时间:   2019年2月11日 上午11:52:24   
 */
@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserDao userDao;

	/** (non-Javadoc)
	 * @see org.saber.avalon.modules.system.service.IUserService#requestUserByName(java.lang.String)
	 */
	@Override
	public UserDTO requestUserByName(String username) {
		UserDTO dto = new UserDTO();
		UserDO user = this.userDao.queryUserByName(username);
		if(null == user) {
			return dto;
		}
		BeanUtils.copyProperties(user, dto);
		return dto;
	}

	/** (non-Javadoc)
	 * @see org.saber.avalon.modules.system.service.IUserService#requestPermiUrlsByName(java.lang.String)
	 */
	@Override
	public Set<String> requestPermiUrlsByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/** (non-Javadoc)
	 * @see org.saber.avalon.modules.system.service.IUserService#requestRoleIdByName(java.lang.String)
	 */
	@Override
	public Set<String> requestRoleIdByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
