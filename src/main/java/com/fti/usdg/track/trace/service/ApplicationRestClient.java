/**
 * 
 */
package com.fti.usdg.track.trace.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fti.usdg.track.trace.common.Constants;

/**
 * @author Anup
 *
 */
@Service
public class ApplicationRestClient {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationRestClient.class);

	public String callAPI(String payload, String endPoint, String token, HttpMethod method,
			Boolean authType)
			throws Exception {
		ResponseEntity<String> response = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			logger.info("payload " + payload + "\n " + endPoint);
			HttpEntity<?> httpEntity = getHttpEntityObject(payload,token,authType);
			response = restTemplate.exchange(endPoint, method,httpEntity ,String.class, new Object[0]);
			logger.info(" Response Received \n" + response);
		} catch (Exception e) {
			logger.error("Exception in calling External API" + e.getMessage());
			throw new Exception("Exception in calling External API" + e.getMessage());
		}
		return response.getBody();
	}

	private HttpEntity<?> getHttpEntityObject(String json, String token,Boolean authType)
			throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);	
		if(authType) {
			headers.add("app-internal-token", Constants.APP_INTERNAL_TOKEN);
		} 
		HttpEntity<?> httpEntity = new HttpEntity<Object>(json, headers);
		return httpEntity;
	}

	 
}
