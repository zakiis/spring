package com.zakiis.spring.interceptor.traceid;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.zakiis.common.TraceIdUtil;

public class TraceIdClientHttpRequestFilter implements ClientHttpRequestInterceptor {

	private String traceIdHeaderName;
	
	public TraceIdClientHttpRequestFilter(String appName, String headerName) {
		TraceIdUtil.init(appName + "_");
		traceIdHeaderName = headerName;
	}
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		request.getHeaders().add(traceIdHeaderName, TraceIdUtil.get());
		return execution.execute(request, body);
	}

}
