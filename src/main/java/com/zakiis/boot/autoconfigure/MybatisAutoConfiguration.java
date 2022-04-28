package com.zakiis.boot.autoconfigure;

import org.apache.ibatis.plugin.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zakiis.boot.autoconfigure.properties.MybatisCipherProperties;
import com.zakiis.boot.autoconfigure.properties.MybatisPrintSqlProperties;
import com.zakiis.boot.interceptor.mybatis.MybatisCipherInterceptor;
import com.zakiis.boot.interceptor.mybatis.MybatisPrintSqlInterceptor;
import com.zakiis.security.codec.HexUtil;

@Configuration
@ConditionalOnClass(Invocation.class)
public class MybatisAutoConfiguration {

	final static Logger logger = LoggerFactory.getLogger(MybatisAutoConfiguration.class);
	
	@Configuration
	@ConditionalOnProperty(name = "mybatis.cipher.enabled", havingValue = "true")
	@EnableConfigurationProperties(MybatisCipherProperties.class)
	protected static class MybatisCipherConfiguration {
		
		@Bean
		public MybatisCipherInterceptor mybatisCipherInterceptor(MybatisCipherProperties mybatisCipherProperties) {
			logger.info("Mybatis Cipher Interceptor enabled");
			byte[] secret = HexUtil.toByteArray(mybatisCipherProperties.getSecret());
			byte[] iv = HexUtil.toByteArray(mybatisCipherProperties.getIv());
			return new MybatisCipherInterceptor(secret, iv, mybatisCipherProperties.isEnableFuzzyQuery());
		}
	}
	
	@Configuration
	@AutoConfigureBefore(MybatisCipherConfiguration.class)
	@ConditionalOnProperty(name = "mybatis.print-sql", havingValue = "true")
	@EnableConfigurationProperties(MybatisPrintSqlProperties.class)
	protected static class MybatisPrintSqlConfiguration {
		
		@Bean
		public MybatisPrintSqlInterceptor mybatisPrintSqlInterceptor() {
			logger.info("Mybatis Print SQL Interceptor enabled");
			return new MybatisPrintSqlInterceptor();
		}

	}
	
}
