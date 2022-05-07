package com.zakiis.spring.feign.impl;

import java.io.IOException;

import com.zakiis.spring.feign.FeignFilter;
import com.zakiis.spring.feign.FeignFilterChain;

import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.Response;

public class DelegateFeignFilter implements FeignFilter {

	Client delegate;
	
	public DelegateFeignFilter(Client delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public Response filter(Request request, Options options, FeignFilterChain filterChain) throws IOException {
		return delegate.execute(request, options);
	}

}
