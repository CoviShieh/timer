package com.xieweihao.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {
	
	/**
     * 数据源配置对象
     * Primary 表示默认的对象，Autowire可注入，不是默认的得明确名称注入
     * @return
     */
	//方式一
//	@Primary
//    @Bean("mysqlDataSourceProperties")
//    @ConfigurationProperties("spring.datasource")
//	public DataSourceProperties mysqlDataSourceProperties(){
//		return new DataSourceProperties();
//	}
//	
//	@Primary
//	@Bean("mysqlDatasource")
//	public DataSource mysqlDataSource(@Autowired @Qualifier("mysqlDataSourceProperties") DataSourceProperties props){
//		return props.initializeDataSourceBuilder().build();
//	}
	
	@Primary
	@Bean(name = "mysqlDatasource")
    @Qualifier("mysqlDatasource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource mysqlDatasource() {
        return DataSourceBuilder.create().build();
    }
	
	@Primary
	@Bean("mysqlJdbcTemplate")
	public JdbcTemplate mysqlJdbcTemplate(@Autowired @Qualifier("mysqlDatasource") DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
	
}
