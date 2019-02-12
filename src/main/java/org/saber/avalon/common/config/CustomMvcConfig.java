/**
 * 
 */
package org.saber.avalon.common.config;


import org.saber.avalon.common.web.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * <p>类名称: CustomMvcConfig </p> 
 * <p>描述: TODO  </p>
 * <p>创建时间 : 2019年2月12日 上午11:55:30 </p>
 * @author lijunliang
 * @version 1.0
 *
 */
//@Configuration
public class CustomMvcConfig implements WebMvcConfigurer{
	@Bean
	public TokenInterceptor tokenInterceptor() {
		return new TokenInterceptor();
	}
	/**
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**");
	}
	
}
