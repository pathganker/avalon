/**
 * 
 */
package org.saber.avalon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
	//允许跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }
}
