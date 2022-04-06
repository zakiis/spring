package com.zakiis.boot.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "log.desensitization")
public class DesensitizationProperties {

	/** separated by comma, replace center value to * */
	private String replaceFields;
	/** separated by comma, replace value to * */
	private String eraseFields;
	/** separated by comma, drop value */
	private String dropFields;
	
	public String getReplaceFields() {
		return replaceFields;
	}
	public void setReplaceFields(String replaceFields) {
		this.replaceFields = replaceFields;
	}
	public String getEraseFields() {
		return eraseFields;
	}
	public void setEraseFields(String eraseFields) {
		this.eraseFields = eraseFields;
	}
	public String getDropFields() {
		return dropFields;
	}
	public void setDropFields(String dropFields) {
		this.dropFields = dropFields;
	}
	
	
	
}
