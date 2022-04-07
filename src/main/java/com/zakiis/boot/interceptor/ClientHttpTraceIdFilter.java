package com.zakiis.boot.interceptor;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.zakiis.boot.autoconfigure.properties.TraceIdProperties;
import com.zakiis.common.TraceIdUtil;

public class ClientHttpTraceIdFilter implements ClientHttpRequestInterceptor {

	private String traceIdHeaderName;
	
	public ClientHttpTraceIdFilter(TraceIdProperties traceIdProperties) {
		TraceIdUtil.init(traceIdProperties.getAppName() + "_");
		traceIdHeaderName = traceIdProperties.getHeader();
	}
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		request.getHeaders().add(traceIdHeaderName, TraceIdUtil.get());
		return execution.execute(request, body);
	}

	

}
