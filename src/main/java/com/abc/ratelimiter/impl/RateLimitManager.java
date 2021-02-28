package com.abc.ratelimiter.impl;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a thread safe static singleton class which maintains all the rate
 * limiting policies in the form of a concurrent hash map. The key of the map is
 * the api id while the value is the Rate Limit Executor object. Based on the
 * key supplied the appropriate Rate Limiter is invoked.
 */
public final class RateLimitManager {

	// Static singleton class which is thread safe from multiple instantiation race
	// conditions in traiditonal sigletons
	public static final RateLimitManager _instance = new RateLimitManager();

	// Container for keeping track of all provisioned rate limits.
	private ConcurrentHashMap<String, RateLimitImpl> rateLimitMap = new ConcurrentHashMap<String, RateLimitImpl>();

	// To provision a new Rate Limit Policy and executor. Callback interface to
	// receive notifications
	public void provisionRateLimit(RateLimitImpl builder, String apiId) {
		rateLimitMap.put(apiId, builder);
	}

	public void pegTraffic(String apiID, String userID) throws RateLimitException {
		rateLimitMap.get(apiID).checkIfUserExceededRateLimit(userID);
	}

}