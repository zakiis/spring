package com.zakiis.boot.interceptor.damboard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class DamboardClientHttpRequestFilter implements ClientHttpRequestInterceptor {
	
	Map<String, String> ruleMap;
	Map<Pattern, String> rulePatternMap;
	final static Logger log = LoggerFactory.getLogger(DamboardClientHttpRequestFilter.class);
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		String path = request.getURI().getPath();
		String damboardContent = null;
		if (ruleMap.containsKey(path)) {
			damboardContent = ruleMap.get(path);
		} else {
			for (Map.Entry<Pattern, String> entry : rulePatternMap.entrySet()) {
				if (entry.getKey().matcher(path).find()) {
					damboardContent = entry.getValue();
					break;
				}
			}
		}
		if (damboardContent != null) {
			byte[] bodyBytes = damboardContent.getBytes(StandardCharsets.UTF_8);
			request.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			request.getHeaders().setContentLength(bodyBytes.length);
			return new OkHttpClientHttpResponse(new ByteArrayInputStream(bodyBytes), request.getHeaders());	
		} else {
			return execution.execute(request, body);	
		}
	}

	static class OkHttpClientHttpResponse implements ClientHttpResponse {
		
		public OkHttpClientHttpResponse(InputStream body, HttpHeaders headers) {
			this.body = body;
			this.headers = headers;
		}
		
		HttpHeaders headers;
		InputStream body;

		@Override
		public InputStream getBody() throws IOException {
			return body;
		}

		@Override
		public HttpHeaders getHeaders() {
			return headers;
		}

		@Override
		public HttpStatus getStatusCode() throws IOException {
			return HttpStatus.OK;
		}

		@Override
		public int getRawStatusCode() throws IOException {
			return HttpStatus.OK.value();
		}

		@Override
		public String getStatusText() throws IOException {
			return HttpStatus.OK.getReasonPhrase();
		}

		@Override
		public void close() {
			try {
				getBody().close();
			} catch (IOException ex) {
				log.error("close reponse body got an exception", ex);
			}
		}
	}

	public DamboardClientHttpRequestFilter(Map<String, String> ruleMap) {
		this.ruleMap = ruleMap;
		rulePatternMap = new HashMap<Pattern, String>(ruleMap.size());
		for (Map.Entry<String, String> entry : ruleMap.entrySet()) {
			Pattern pattern = Pattern.compile(entry.getKey());
			rulePatternMap.put(pattern, entry.getValue());
		}
	}
}
