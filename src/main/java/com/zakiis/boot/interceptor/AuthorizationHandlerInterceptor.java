package com.zakiis.boot.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zakiis.security.PermissionUtil;
import com.zakiis.security.annotation.Permission;
import com.zakiis.security.exception.NoPermissionException;
import com.zakiis.spring.Realm;

public class AuthorizationHandlerInterceptor implements HandlerInterceptor {
	
	Realm realm;
	String errorResponseText;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
			List<String> roles = realm.getRoles(request);
			try {
				PermissionUtil.checkFunctionAccess(roles, permission);
			} catch (NoPermissionException e) {
//				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().write(errorResponseText);
				return false;
			}
			return true;
		} else {
			return HandlerInterceptor.super.preHandle(request, response, handler);
		}
	}

	public AuthorizationHandlerInterceptor(Realm realm, String errorResponseText) {
		this.realm = realm;
		this.errorResponseText = errorResponseText;
	}

}
