package com.zakiis.boot.interceptor.damboard;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.zakiis.boot.feign.FeignFilter;
import com.zakiis.boot.feign.FeignFilterChain;

import feign.Request;
import feign.Request.Options;
import feign.Response;

public class DamBoardFeignFilter implements FeignFilter {

	private Map<String, String> ruleMap;
	private Map<Pattern, String> rulePatternMap;
	
	public DamBoardFeignFilter(Map<String, String> ruleMap) {
		this.ruleMap = ruleMap;
		rulePatternMap = new HashMap<Pattern, String>(ruleMap.size());
		for (Map.Entry<String, String> entry : ruleMap.entrySet()) {
			Pattern pattern = Pattern.compile(entry.getKey());
			rulePatternMap.put(pattern, entry.getValue());
		}
	}

	@Override
	public Response filter(Request request, Options options, FeignFilterChain filterChain) throws IOException {
		String path = new URL(request.url()).getPath();
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
			Map<String, Collection<String>> headers = new HashMap<String, Collection<String>>(request.headers());
			headers.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));
			return Response.builder()
					.request(request)
					.status(HttpStatus.OK.value())
					.headers(headers)
					.body(damboardContent, StandardCharsets.UTF_8)
					.build();
		} else {
			return filterChain.doFilter(request, options);
		}
	}

}