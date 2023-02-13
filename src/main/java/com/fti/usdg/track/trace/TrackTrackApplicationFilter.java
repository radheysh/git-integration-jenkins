/**
 * 
 */
package com.fti.usdg.track.trace;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;


import org.apache.catalina.Globals;
import org.slf4j.Logger;
/**
 * @author Anup
 *
 */

@CrossOrigin("*")
@Component
@Order(1)
public class TrackTrackApplicationFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(TrackTrackApplicationFilter.class);
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String apiName = String.valueOf(req.getAttribute(Globals.DISPATCHER_REQUEST_PATH_ATTR));
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		logger.info("API name apiName "+apiName);
				//httpResponse.setHeader("Access-Control-Allow-Origin", "*");
				httpResponse.setHeader("Access-Control-Allow-Origin", "*");
				httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
				httpResponse.setHeader("Access-Control-Max-Age", "3600");
				httpResponse.setHeader("Access-Control-Allow-Headers",
						"X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,x-access-token,Access-Control-Allow-Origin");
				httpResponse.addHeader("Access-Control-Expose-Headers", "x-access-token");
				if (req.getMethod().equals("OPTIONS")) {
					httpResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
					return;
				}
				chain.doFilter(request, response);
		 
	}

}