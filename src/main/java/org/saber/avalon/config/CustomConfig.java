/**
 * 
 */
package org.saber.avalon.config;

import java.util.List;

import org.saber.avalon.web.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

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
                .allowedHeaders("Accept","Content-Type","Auth-Token","Auth-Device");
    }
    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//Token拦截
    	registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**");
    }
    //解析JSON数据
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        // 1、需要先定义一个·convert转换消息的对象；
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        // 2、添加fastjson的配置信息，比如 是否要格式化返回json数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        // 3、在convert中添加配置信息.
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        // 4、将convert添加到converters当中.
//        converters.add(fastConverter);
//    }
}
