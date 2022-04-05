package com.zakiis.boot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zakiis.boot.autoconfigure.properties.TraceIdProperties;
import com.zakiis.boot.interceptor.TraceIdFilter;

@Configuration
@ConditionalOnProperty(name = "log.trace-id.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(TraceIdProperties.class)
public class TraceIdAutoConfiguration {

	Logger log = LoggerFactory.getLogger(TraceIdAutoConfiguration.class);
	
	@Bean
	public TraceIdFilter traceIdFilter(TraceIdProperties traceIdProperties) {
		log.info("trace id filter enabled.");
		TraceIdFilter traceIdFilter = new TraceIdFilter(traceIdProperties);
		return traceIdFilter;
	}
}
