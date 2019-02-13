/**
 * 
 */
package org.saber.avalon.common.config;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 
 * <p>类名称: CustomMvcConfig </p> 
 * <p>描述: TODO  </p>
 * <p>创建时间 : 2019年2月12日 上午11:55:30 </p>
 * @author lijunliang
 * @version 1.0
 *
 */
@Configuration
public class CustomMvcConfig implements WebMvcConfigurer{
	/**
	 */
    private FastJsonHttpMessageConverter customFastJsonHttpMessageConverter() {
    	FastJsonHttpMessageConverter jsonConverter = new FastJsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        MediaType textmedia = new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8"));
        MediaType jsonmedia = new MediaType(MediaType.APPLICATION_JSON, Charset.forName("UTF-8"));
        supportedMediaTypes.add(textmedia);
        supportedMediaTypes.add(jsonmedia);
        jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customFastJsonHttpMessageConverter());
    }
	
}
