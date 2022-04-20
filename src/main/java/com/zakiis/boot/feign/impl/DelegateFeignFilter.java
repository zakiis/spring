package com.zakiis.boot.feign.impl;

import java.io.IOException;

import com.zakiis.boot.feign.FeignFilter;
import com.zakiis.boot.feign.FeignFilterChain;

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
