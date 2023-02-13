/**
 * 
 */
package com.fti.usdg.track.trace.common;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
/**
 * @author Anup
 *
 */
 
public class AppCacheUtils {


	private static final Logger logger = LoggerFactory.getLogger(AppCacheUtils.class);

	
	static DefaultCacheManager manager = null;

	public static Object getValue(String key) {
		Cache<String, Object> cache = manager.getCache("local");
		Object value = cache.get(key);
		logger.info("Key "+key +" Successfully fetched from Cache "+value);
		return value;
	}

	public static void putValue(String key, Object value) {
		if (manager == null) {
			init();
		}
		Cache<String, Object> cache = manager.getCache("local");
		cache.put(key, value);
		logger.info("Key "+key +" Successfully storeded in Cache "+value);
	}
	
	
	public static void clearCacheAllEntry() {
		Cache<String, Object> cache = manager.getCache("local");
		if (cache != null) {
			cache.clear();
		}
		logger.info(" Clear Cache");
	}
	private static void init() {
		 manager =  new DefaultCacheManager();
		 manager.defineConfiguration("local", new ConfigurationBuilder().build());
		 //  cacheManager.stop();
		    
	}
	public static void putValueWithExpiration(String key, Object obj, Integer time) {
		if (manager == null) {
			init();
		}
		Cache<String, Object> cache = manager.getCache("local");
		cache.put(key, obj, time, TimeUnit.MINUTES);
		logger.info("Key " + key + " Successfully storeded in Cache for time " + time);
	}
	public static void removeValue(String key, Object value) {
		if (manager == null) {
			init();
		}
		Cache<String, Object> cache = manager.getCache("local");
		cache.remove(key);
		logger.info("Key "+key +" Successfully removed in Cache "+value);
	}
	
}

