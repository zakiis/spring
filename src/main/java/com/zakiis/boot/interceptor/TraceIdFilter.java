package com.zakiis.boot.interceptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.zakiis.boot.autoconfigure.properties.TraceIdProperties;
import com.zakiis.common.TraceIdUtil;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter extends OncePerRequestFilter {

	private String traceIdHeaderName;
	private Logger log = LoggerFactory.getLogger(TraceIdFilter.class);
	
	public TraceIdFilter(TraceIdProperties traceIdProperties) {
		TraceIdUtil.init(traceIdProperties.getAppName() + "_");
		traceIdHeaderName = traceIdProperties.getHeader();
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		try {
			String traceId = request.getHeader(traceIdHeaderName);
			TraceIdUtil.set(traceId);
			log.info("------------->{} start", request.getRequestURI());
			filterChain.doFilter(requestWrapper, response);
		} finally {
			log.info("------------->{} end, request body:{}", request.getRequestURI(), new String(requestWrapper.getContentAsByteArray()));
			TraceIdUtil.clear();
		}
	}
}
