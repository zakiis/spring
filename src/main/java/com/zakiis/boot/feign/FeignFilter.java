package com.zakiis.boot.feign;

import java.io.IOException;

import feign.Request;
import feign.Request.Options;
import feign.Response;

public interface FeignFilter {

	/**
	 * @param request
	 * @param options
	 * @return 
	 * @throws IOException 
	 */
	Response filter(Request request, Options options, FeignFilterChain filterChain) throws IOException;
}
