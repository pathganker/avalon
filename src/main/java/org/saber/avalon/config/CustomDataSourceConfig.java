/**
 * 
 */
package org.saber.avalon.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**   
 * @ClassName:  CustomDataSourceConfig   
 * @Description: 数据源配置
 * @author: lijunliang 
 * @date:   2019年1月31日 下午1:19:35   
 *     
 */
@Configuration
public class CustomDataSourceConfig {
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.def")
	public Properties defSourceProperties() {
		return new Properties()	;
	}
	
	@Bean
	@Primary
	public DataSource defSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.configFromPropety(defSourceProperties());
        return dataSource;
	}
	
	@Bean
	public SqlSessionFactoryBean defSessionFactory() {
		 SqlSessionFactoryBean factoryBean= new SqlSessionFactoryBean();
		 factoryBean.setDataSource(defSource());
		 factoryBean.setMapperLocations(null);
		 return factoryBean;
	}
	
	@Bean
	public PlatformTransactionManager defManager() {
		return new DataSourceTransactionManager(defSource());
	}
	
	@Bean
	public SqlSessionTemplate defSqlSessionTemplate() {
		try {
			return new SqlSessionTemplate(defSessionFactory().getObject());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.craw")
	public Properties crawlSourceProperties() {
		return new Properties()	;
	}
	@Bean
	public DataSource crawSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.configFromPropety(crawlSourceProperties());
        return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager crawManager() {
		return new DataSourceTransactionManager(crawSource());
	}
		
}
