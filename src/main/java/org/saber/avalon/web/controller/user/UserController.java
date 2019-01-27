/**
 * 
 */
package org.saber.avalon.web.controller.user;

import org.saber.avalon.config.api.ApiCodeEnum;
import org.saber.avalon.pojo.Result;
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
		return rs;
	}
}
