package com.zakiis.spring.interceptor.traceid;

import com.zakiis.common.TraceIdUtil;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class TraceIdFeignRequestFilter implements RequestInterceptor {

	private String traceIdHeaderName;
	
	public TraceIdFeignRequestFilter(String appName, String headerName) {
		TraceIdUtil.init(appName + "_");
		traceIdHeaderName = headerName;
	}
	
	@Override
	public void apply(RequestTemplate template) {
		template.header(traceIdHeaderName, TraceIdUtil.get());
	}

}
