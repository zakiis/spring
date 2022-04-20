package com.zakiis.boot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.zakiis.boot.autoconfigure.properties.TraceIdProperties;
import com.zakiis.boot.interceptor.traceid.TraceIdClientHttpRequestFilter;
import com.zakiis.boot.interceptor.traceid.TraceIdFeignRequestFilter;
import com.zakiis.boot.interceptor.traceid.TraceIdHttpRequestFilter;

import feign.RequestInterceptor;

@Configuration
@ConditionalOnProperty(name = "log.trace-id.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(TraceIdProperties.class)
public class TraceIdAutoConfiguration {

	static Logger log = LoggerFactory.getLogger(TraceIdAutoConfiguration.class);
	
	@Bean
	public TraceIdHttpRequestFilter traceIdFilter(TraceIdProperties traceIdProperties) {
		log.info("Trace id filter for http request enabled.");
		TraceIdHttpRequestFilter traceIdFilter = new TraceIdHttpRequestFilter(traceIdProperties);
		return traceIdFilter;
	}
	
	@Configuration
	@ConditionalOnClass(RequestInterceptor.class)
	protected static class FeignClientTraceIdConfiguration {

		@Bean
		public TraceIdFeignRequestFilter feignTraceIdFilter(TraceIdProperties traceIdProperties) {
			log.info("Trace id filter for feign client request enabled.");
			return new TraceIdFeignRequestFilter(traceIdProperties);
		}
	}
	
	@Configuration
	@ConditionalOnClass(RestTemplate.class)
	protected static class HttpClientTraceIdConfiguration {

		@Bean
		public TraceIdClientHttpRequestFilter clientHttpTraceIdFilter(TraceIdProperties traceIdProperties) {
			log.info("Trace id filter for http client request enabled.");
			return new TraceIdClientHttpRequestFilter(traceIdProperties);
		}
		
		@Bean
		public RestTemplateCustomizer traceIdRestTemplateCustomizer(TraceIdClientHttpRequestFilter filter) {
			return new RestTemplateCustomizer() {
				@Override
				public void customize(RestTemplate restTemplate) {
					if (!restTemplate.getInterceptors().contains(filter)) {
						restTemplate.getInterceptors().add(filter);
					}
				}
			};
		}
	}
}
