package com.zakiis.boot.autoconfigure;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.zakiis.boot.autoconfigure.properties.TraceIdProperties;
import com.zakiis.boot.interceptor.ClientHttpTraceIdFilter;

@Configuration
@ConditionalOnClass(ClientHttpRequestInterceptor.class)
public class SpringWebAutoConfiguration {

	Logger log = LoggerFactory.getLogger(SpringWebAutoConfiguration.class);
	
	@Bean
	@ConditionalOnProperty(name = "log.trace-id.enabled", havingValue = "true", matchIfMissing = true)
	public ClientHttpTraceIdFilter clientHttpTraceIdFilter(TraceIdProperties traceIdProperties) {
		log.info("Client http trace id filter enabled.");
		return new ClientHttpTraceIdFilter(traceIdProperties);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RestTemplate restTemplate(List<ClientHttpRequestInterceptor> interceptors) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}
}
