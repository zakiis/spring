package com.zakiis.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mybatis.cipher")
public class MybatisCipherProperties {

	/** AES secret key in base64 format*/
	private String secret;
	/** Initialization vector in base64 format*/
	private String iv;
	
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
	
}
