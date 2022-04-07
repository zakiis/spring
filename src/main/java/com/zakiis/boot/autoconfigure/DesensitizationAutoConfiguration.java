package com.zakiis.boot.autoconfigure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.zakiis.boot.autoconfigure.properties.DesensitizationProperties;
import com.zakiis.security.logging.DesensitizationUtil;

@Configuration
@EnableConfigurationProperties(DesensitizationProperties.class)
public class DesensitizationAutoConfiguration implements InitializingBean {
	
	Logger log = LoggerFactory.getLogger(DesensitizationAutoConfiguration.class);

	@Autowired
	DesensitizationProperties desensitizationProperties;

	/**
	 * need add following to logback.xml to make it work, note that fields msg represents the field in logback pattern.
	 * &lt;conversionRule conversionWord="msg" converterClass="com.zakiis.security.logging.DesensitizationConverter"/&gt;
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("Log desensitization feature enabled");
		Set<String> replaceFieldSet = new HashSet<String>();
		if (StringUtils.isNotEmpty(desensitizationProperties.getReplaceFields())) {
			String[] replaceFields = desensitizationProperties.getReplaceFields().split(",");
			replaceFieldSet.addAll(Arrays.asList(replaceFields));
		}
		Set<String> erasseFieldSet = new HashSet<String>();
		if (StringUtils.isNotEmpty(desensitizationProperties.getEraseFields())) {
			String[] replaceFields = desensitizationProperties.getEraseFields().split(",");
			erasseFieldSet.addAll(Arrays.asList(replaceFields));
		}
		Set<String> dropFieldSet = new HashSet<String>();
		if (StringUtils.isNotEmpty(desensitizationProperties.getDropFields())) {
			String[] replaceFields = desensitizationProperties.getDropFields().split(",");
			dropFieldSet.addAll(Arrays.asList(replaceFields));
		}
		DesensitizationUtil.init(replaceFieldSet, erasseFieldSet, dropFieldSet);
		
	}
}
