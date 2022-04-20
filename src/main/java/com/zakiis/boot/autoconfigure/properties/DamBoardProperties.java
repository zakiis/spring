package com.zakiis.boot.autoconfigure.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http.dam-board")
public class DamBoardProperties {

	private boolean enabled = false;
	private List<DamBoard> rules;
	
	public static class DamBoard {
		
		private String url;
		private String response;
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getResponse() {
			return response;
		}
		public void setResponse(String response) {
			this.response = response;
		}
		
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<DamBoard> getRules() {
		return rules;
	}

	public void setRules(List<DamBoard> rules) {
		this.rules = rules;
	}
	
	public Map<String, String> ruleMap() {
		HashMap<String, String> ruleMap = new HashMap<String, String>();
		if (rules != null && rules.size() > 0) {
			for (DamBoard damBoard : rules) {
				ruleMap.put(damBoard.getUrl(), damBoard.getResponse());
			}
		}
		return ruleMap;
	}
}
