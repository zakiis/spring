package com.zakiis.spring;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.zakiis.spring.interceptor.AuthorizationHandlerInterceptor;

public interface Realm {

	/**
	 * Get user roles from current request. {@link AuthorizationHandlerInterceptor} will check if current user has right to the method.
	 * @param request
	 * @return
	 */
	Set<String> getFunctions(HttpServletRequest request);
}
