package com.abc.ratelimiter.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.abc.ratelimiter.spec.IRateLimit;
import com.abc.ratelimiter.spec.IRateLimitRegistry;

/**
 * This is a thread safe static singleton class that 
 * maintains mapping of APIs vs RateLimits
 */
public final class RateLimitRegistry implements IRateLimitRegistry {

	/**
	 * Logger to make sure code is PROD ready (No use of System.out.println)
	 */
	private static Logger logger = Logger.getLogger(RateLimitRegistry.class.getName());

	/**
	 * Singleton instance of registry calss
	 */
	public static final RateLimitRegistry _instance = new RateLimitRegistry();

	/**
	 * Concurrent hashmap to hold API vs RaeLimit
	 */
	private ConcurrentHashMap<String, IRateLimit> apiVSRateLimitMap = new ConcurrentHashMap<String, IRateLimit>();

	/**
	 * Register rate-limit for APIs 
	 * 
	 * @param rateLimit
	 * @param apiID
	 */
	public void registerRateLimitForAPI(String apiID, IRateLimit rateLimit) {
		logger.info("Registered rate-limit for API '" + apiID + "'");
		apiVSRateLimitMap.put(apiID, rateLimit);
	}

	/**
	 * Get rate-limit for APIid
	 * 
	 * @param apiID
	 * @return IRateLimit
	 */
	public IRateLimit getRateLimit(String apiID) {
		return apiVSRateLimitMap.get(apiID);
	}

}