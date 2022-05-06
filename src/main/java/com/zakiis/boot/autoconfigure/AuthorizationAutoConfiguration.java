package com.zakiis.boot.autoconfigure;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zakiis.boot.autoconfigure.properties.AuthorizationProperties;
import com.zakiis.boot.interceptor.AuthorizationHandlerInterceptor;
import com.zakiis.spring.Realm;

@Configuration
@EnableConfigurationProperties(AuthorizationProperties.class)
@ConditionalOnProperty(name = "http.authorization.enabled", havingValue = "true")
@ConditionalOnClass(HandlerInterceptor.class)
public class AuthorizationAutoConfiguration {

	Logger log = LoggerFactory.getLogger(AuthorizationAutoConfiguration.class);
	
	@Bean
	public AuthorizationHandlerInterceptor authorizationHandlerInterceptor(AuthorizationProperties authorizationProperties, Realm realm) {
		log.info("Feature authorization handler interceptor enabled.");
		return new AuthorizationHandlerInterceptor(realm, authorizationProperties.getErrorResponseText());
	}
	
	@Configuration
	protected static class WebMvcConfigure implements WebMvcConfigurer {

		@Autowired
		AuthorizationProperties authorizationProperties;
		@Autowired
		AuthorizationHandlerInterceptor authorizationHandlerInterceptor;
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			ArrayList<String> skipPathList = new ArrayList<String>(authorizationProperties.getSkipPath());
			skipPathList.add("/error");
			registry.addInterceptor(authorizationHandlerInterceptor).addPathPatterns("/**")
					.excludePathPatterns(skipPathList);
		}
		
	}
}
