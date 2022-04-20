package com.zakiis.boot.autoconfigure;

import java.util.List;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebAutoConfiguration {
	
	@Configuration
	@ConditionalOnClass(RestTemplate.class)
	protected static class RestTemplateConfiguration {
		
		@Bean
		@ConditionalOnMissingBean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
		
		@Bean
		public SmartInitializingSingleton restTemplateInitailizing(List<RestTemplateCustomizer> customizers, List<RestTemplate> restTemplates) {
			return new SmartInitializingSingleton() {
				@Override
				public void afterSingletonsInstantiated() {
					for (RestTemplate restTemplate : restTemplates) {
						for (RestTemplateCustomizer customizer : customizers) {
							customizer.customize(restTemplate);
						}
					}
				}
			};
		}
	}

	
	
	
}
