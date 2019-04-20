package com.xieweihao;


import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DemoApplicationTests {
	//方式一
//	@Autowired
//	DataSourceProperties dataSourceProperties;
//	
//	@Autowired
//	ApplicationContext applicationContext;
//	
//	@Test
//	public void contextLoads() {
//		//获取配置的数据源
//		DataSource dataSource = applicationContext.getBean(DataSource.class);
//		System.out.println(dataSource);
//		System.out.println(dataSource.getClass().getName());
//		System.out.println(dataSourceProperties);
//		
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//		List<?> resultList = jdbcTemplate.queryForList("select * from user");
//		System.out.println("-->"+JSON.toJSONString(resultList));
//	}

	 @Autowired
	 @Qualifier("mysqlJdbcTemplate")
	 protected JdbcTemplate jdbcTemplate;
	 
	 @Test
	 public void test() {
		 List<?> resultList = jdbcTemplate.queryForList("select * from user");
		 System.out.println(JSON.toJSONString(resultList));
	 }
}

