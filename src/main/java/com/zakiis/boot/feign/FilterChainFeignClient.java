package com.zakiis.boot.feign;

import java.io.IOException;
import java.util.List;

import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.Response;

public class FilterChainFeignClient implements Client {
	
	List<FeignFilter> filters;
	
	FilterChainFeignClient(List<FeignFilter> filters) {
		this.filters = filters;
	}

	@Override
	public Response execute(Request request, Options options) throws IOException {
		FeignFilterChain filterChain = new FeignFilterChain(filters);
		return filterChain.doFilter(request, options);
	}

}
