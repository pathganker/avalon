/**
 * 
 */
package org.saber.avalon.modules.system.web;

import java.util.List;

import org.saber.avalon.common.pojo.Result;
import org.saber.avalon.common.pojo.api.ApiCodeEnum;
import org.saber.avalon.modules.system.dao.UserDao;
import org.saber.avalon.modules.system.pojo.dos.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**   
 * @ClassName:  UserController   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月24日 上午12:40:49   
 *     
 */
@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 
	 * 方法名:  getUserInfo  
	 * 描述:    TODO
	 * 创建人：	lijunliang
	 * 创建时间:  2019年1月27日下午2:25:44
	 * 修改时间:    
	 * 参数:  @return  
	 * 返回类型  Result
	 */
	@RequestMapping(value="info")
	public Result getUserInfo() {
		Result rs = new Result();
		rs.setCode(ApiCodeEnum.SUCCESS);
		rs.setMessage("you are allowed");
		List<UserDO> dos= this.userDao.getAll();
		rs.setData(dos);
		return rs;
	}
}
