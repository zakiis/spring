package com.zakiis.boot.feign;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.util.ReflectionUtils;

import com.zakiis.boot.feign.impl.DelegateFeignFilter;

import feign.Client;
import feign.Feign.Builder;

public class FeignClientBuilderCustomizer implements FeignBuilderCustomizer {

	List<FeignFilter> filters;
	Logger log = LoggerFactory.getLogger(FeignFilterChain.class);
	
	public FeignClientBuilderCustomizer(List<FeignFilter> filters) {
		this.filters = filters;
	}
	
	@Override
	public void customize(Builder builder) {
		try {
			Field field = Builder.class.getDeclaredField("client");
			ReflectionUtils.makeAccessible(field);
			Client client = (Client)field.get(builder);
			DelegateFeignFilter delegateFeignFilter = new DelegateFeignFilter(client);
			filters.add(delegateFeignFilter);
			
			FilterChainFeignClient filterChainFeignClient = new FilterChainFeignClient(filters);
			builder.client(filterChainFeignClient);
		} catch (Exception e) {
			log.error("set feign client decorator got an exception.", e);
		}
	}

}
