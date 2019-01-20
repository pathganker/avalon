/**
 * 
 */
package org.saber.avalon.web.view;

import org.saber.avalon.pojo.Result;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

/**   
 * @ClassName:  CustomView   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月20日 下午4:06:19   
 *     
 */
public class CustomView extends FastJsonJsonView{
	CustomView(){
		super();
	}
	public CustomView(Result result){
		this.addStaticAttribute("result", result);
	}

}
