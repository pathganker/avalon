/**   */
package org.saber.avalon.common.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;

import org.saber.avalon.common.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 
 * <p>类名称: HandlerUtils </p> 
 * <p>描述: TODO  </p>
 * <p>创建时间 : 2019年2月14日 下午9:15:22 </p>
 * @author lijunliang
 * @version 1.0
 *
 */
public class HandlerUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HandlerUtils.class);

	
	public static void handlerReturnJSON(ServletResponse response, Result rt) {
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(rt));
		} catch (IOException e) {
			LOGGER.info("返回登录验证失败异常:{}", e);		
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
