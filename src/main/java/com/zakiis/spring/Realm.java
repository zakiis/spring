package com.zakiis.spring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zakiis.boot.interceptor.AuthorizationHandlerInterceptor;

public interface Realm {

	/**
	 * Get user roles from current request. {@link AuthorizationHandlerInterceptor} will check if current user has right to the method.
	 * @param request
	 * @return
	 */
	List<String> getRoles(HttpServletRequest request);
}
