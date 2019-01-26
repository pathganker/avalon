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
 * @Description: 自定义配置
 * @author: lijunliang 
 * @date:   2019年1月20日 下午5:25:56   
 *     
 */
@Configuration
public class CustomConfig implements WebMvcConfigurer{
	
	@Bean
    public TokenInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }
    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//Token拦截
    	registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**");
    }
}
