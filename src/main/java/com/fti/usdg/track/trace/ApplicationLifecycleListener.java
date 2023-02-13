/**
 * 
 */
package com.fti.usdg.track.trace;

import org.springframework.stereotype.Component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import com.fti.usdg.track.trace.common.FtiHelper;
/**
 * @author Anup
 *
 */
@Component
public class ApplicationLifecycleListener implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationLifecycleListener.class);

	@Autowired
	private FtiHelper FtiHelper = null;
	
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.debug("Entering in to onApplicationEvent ");
		FtiHelper.refreshCache();
		logger.debug("Leaving from onApplicationEvent ");
	}
}
