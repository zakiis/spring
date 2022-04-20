package com.zakiis.boot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zakiis.boot.autoconfigure.properties.MybatisPrintSqlProperties;
import com.zakiis.boot.interceptor.mybatis.MybatisPrintSqlInterceptor;

@Configuration
@AutoConfigureBefore(MybatisCipherAutoConfiguration.class)
@ConditionalOnProperty(name = "mybatis.print-sql", havingValue = "true")
@EnableConfigurationProperties(MybatisPrintSqlProperties.class)
public class MybatisPrintSqlAutoConfiguration {
	
	Logger logger = LoggerFactory.getLogger(MybatisPrintSqlAutoConfiguration.class);
	
	@Bean
	public MybatisPrintSqlInterceptor mybatisPrintSqlInterceptor() {
		logger.info("Mybatis Print SQL Interceptor enabled");
		return new MybatisPrintSqlInterceptor();
	}

}
