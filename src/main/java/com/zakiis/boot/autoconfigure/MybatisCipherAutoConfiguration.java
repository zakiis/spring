package com.zakiis.boot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zakiis.boot.interceptor.MybatisCipherInterceptor;
import com.zakiis.security.codec.HexUtil;

@Configuration
@ConditionalOnProperty(name = "mybatis.cipher.enabled", havingValue = "true")
@EnableConfigurationProperties(MybatisCipherProperties.class)
public class MybatisCipherAutoConfiguration {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Bean
	public MybatisCipherInterceptor mybatisCipherInterceptor(MybatisCipherProperties mybatisCipherProperties) {
		logger.info("Mybatis Cipher Interceptor enabled");
		byte[] secret = HexUtil.toByteArray(mybatisCipherProperties.getSecret());
		byte[] iv = HexUtil.toByteArray(mybatisCipherProperties.getIv());
		return new MybatisCipherInterceptor(secret, iv);
	}
	
}
