package com.zakiis.boot.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mybatis.cipher")
public class MybatisCipherProperties {

	private boolean enabled;
	/** AES secret key in hex format*/
	private String secret;
	/** Initialization vector in hex format*/
	private String iv;
	/** fuzzy query would make the encrypted content more bigger */
	private boolean enableFuzzyQuery;
	
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isEnableFuzzyQuery() {
		return enableFuzzyQuery;
	}
	public void setEnableFuzzyQuery(boolean enableFuzzyQuery) {
		this.enableFuzzyQuery = enableFuzzyQuery;
	}
	
}
