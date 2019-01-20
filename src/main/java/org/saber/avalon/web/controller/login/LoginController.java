/**
 * 
 */
package org.saber.avalon.web.controller.login;

import org.saber.avalon.config.api.ApiCodeEnum;
import org.saber.avalon.pojo.Result;
import org.saber.avalon.web.view.CustomView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @ClassName:  LoginController   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月20日 下午1:23:13   
 *     
 */
@Controller
@RequestMapping("login")
public class LoginController {
	
	@RequestMapping(value="/loginbyemail")
	public ModelAndView login(String email, String password) {
		Result result = new Result();
		result.setCode(ApiCodeEnum.SUCCESS);
		System.out.println(email);
		System.out.println(password);
		return new ModelAndView(new CustomView(result));
	}
}
