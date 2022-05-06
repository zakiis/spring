package com.zakiis.boot.autoconfigure.properties;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http.authorization")
public class AuthorizationProperties {

	private boolean enabled = false;
	private Set<String> skipPath;
	private String errorResponseText;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<String> getSkipPath() {
		return skipPath;
	}

	public void setSkipPath(Set<String> skipPath) {
		this.skipPath = skipPath;
	}

	public String getErrorResponseText() {
		return errorResponseText;
	}

	public void setErrorResponseText(String errorResponseText) {
		this.errorResponseText = errorResponseText;
	}
	
}
