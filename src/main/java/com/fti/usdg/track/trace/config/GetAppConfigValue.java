/**
 * 
 */
package com.fti.usdg.track.trace.config;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fti.usdg.track.trace.models.ApplicationConfig;
import com.fti.usdg.track.trace.repository.ApplicationConfigRepository;

/**
 * @author Anup
 *
 */
@Component
public class GetAppConfigValue {

	@Autowired
	ApplicationConfigRepository AppConfRepo = null;

	Properties appProps = new Properties();

	public String getPropertiesValue(String key) {
		if (appProps.size() < 1) {
			loadProps();
		}
		return appProps.getProperty(key);
	}

	private void loadProps() {
		List<ApplicationConfig> apConfList = AppConfRepo.findAll();
		if (apConfList != null && apConfList.size() > 0) {
			for (ApplicationConfig apc : apConfList) {
				appProps.put(apc.getConfigName(), apc.getConfigValue());
			}
		}

	}

}
