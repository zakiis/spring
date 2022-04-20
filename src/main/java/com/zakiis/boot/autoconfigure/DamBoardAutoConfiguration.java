package com.zakiis.boot.autoconfigure;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.zakiis.boot.autoconfigure.properties.DamBoardProperties;
import com.zakiis.boot.feign.FeignClientBuilderCustomizer;
import com.zakiis.boot.feign.FeignFilter;
import com.zakiis.boot.interceptor.damboard.DamBoardFeignFilter;
import com.zakiis.boot.interceptor.damboard.DamboardClientHttpRequestFilter;

import feign.Feign;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "http.dam-board.enabled", havingValue = "true")
@EnableConfigurationProperties(DamBoardProperties.class)
public class DamBoardAutoConfiguration {
	
	static Logger log = LoggerFactory.getLogger(DamBoardAutoConfiguration.class);
	
	@Configuration
	@ConditionalOnClass(RestTemplate.class)
	protected static class HttpClientDamBoardConfiguration {
	
		@Bean
		public DamboardClientHttpRequestFilter damboardClientHttpRequestFilter(DamBoardProperties properties) {
			log.info("Damboard http request filter enabled.");
			return new DamboardClientHttpRequestFilter(properties.ruleMap());
		}
		
		@Bean
		public RestTemplateCustomizer damBoardRestTemplateCustomizer(DamboardClientHttpRequestFilter filter) {
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
	
	@Configuration
	@ConditionalOnClass(Feign.class)
	@AutoConfigureBefore(FeignAutoConfiguration.class)	
	protected static class FeignClientDamBoardConfiguration {
		
		@Bean
		@ConditionalOnMissingBean
		public DamBoardFeignFilter damBoardFeignFilter(DamBoardProperties properties) {
			log.info("Damboard feign request filter enabled.");
			return new DamBoardFeignFilter(properties.ruleMap());
		}
		
		@Bean
		public FeignClientBuilderCustomizer decoratorFeignClientBuilderCustomizer(List<FeignFilter> filters) {
			return new FeignClientBuilderCustomizer(filters);
		}
		
	}
	
	
}
