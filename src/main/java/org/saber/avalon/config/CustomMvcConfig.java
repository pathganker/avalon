/**
 * 
 */
package org.saber.avalon.config;


import org.saber.avalon.web.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**   
 * @ClassName:  CustomConfig   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年1月28日 下午6:50:04   
 *     
 */
@Configuration
public class CustomMvcConfig implements WebMvcConfigurer{
	
	@Bean
	public TokenInterceptor tokenInterceptor() {
		return new TokenInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**");
	}
	
}
