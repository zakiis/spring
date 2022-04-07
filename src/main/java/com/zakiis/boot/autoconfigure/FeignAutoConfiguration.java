package com.zakiis.boot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zakiis.boot.autoconfigure.properties.TraceIdProperties;
import com.zakiis.boot.interceptor.FeignTraceIdFilter;

import feign.RequestInterceptor;

/**
 * ConditionalOnClass annotation not work on the method if the return type can't found on class path. So we need put it on an individual configuration class.
 * @author 10901
 */
@Configuration
@ConditionalOnClass(RequestInterceptor.class)
public class FeignAutoConfiguration {
	
	Logger log = LoggerFactory.getLogger(FeignAutoConfiguration.class);

	@Bean
	@ConditionalOnProperty(name = "log.trace-id.enabled", havingValue = "true", matchIfMissing = true)
	public FeignTraceIdFilter feignTraceIdFilter(TraceIdProperties traceIdProperties) {
		log.info("Feign trace id filter enabled.");
		return new FeignTraceIdFilter(traceIdProperties);
	}
}
