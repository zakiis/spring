package com.zakiis.boot.interceptor.traceid;

import com.zakiis.boot.autoconfigure.properties.TraceIdProperties;
import com.zakiis.common.TraceIdUtil;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class TraceIdFeignRequestFilter implements RequestInterceptor {

	private String traceIdHeaderName;
	
	public TraceIdFeignRequestFilter(TraceIdProperties traceIdProperties) {
		TraceIdUtil.init(traceIdProperties.getAppName() + "_");
		traceIdHeaderName = traceIdProperties.getHeader();
	}
	
	@Override
	public void apply(RequestTemplate template) {
		template.header(traceIdHeaderName, TraceIdUtil.get());
	}

}
