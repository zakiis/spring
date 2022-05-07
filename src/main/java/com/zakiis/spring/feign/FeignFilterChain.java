package com.zakiis.spring.feign;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import feign.Request;
import feign.Request.Options;
import feign.Response;

public class FeignFilterChain {
	
	List<FeignFilter> filters = new ArrayList<FeignFilter>();
	int index = 0;

	public Response doFilter(Request request, Options options) throws IOException {
		if (index < filters.size()) {
			index = index + 1;
			return filters.get(index - 1).filter(request, options, this);
		}
		throw new RuntimeException("last filter didn't return a reponse object");
	}
	
	public FeignFilterChain(List<FeignFilter> filters) {
		this.filters = filters;
	}
}
