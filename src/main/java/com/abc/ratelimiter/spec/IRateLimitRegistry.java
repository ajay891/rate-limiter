package com.abc.ratelimiter.spec;

/**
 * Interface defining contract of Rate Limit Registry
 * 
 * @author ajay.jeswani
 *
 */
public interface IRateLimitRegistry {
	
	/**
	 * Register rate-limit for APIid 
	 * 
	 * @param rateLimit
	 * @param apiID
	 */
	public void registerRateLimitForAPI(String apiID, IRateLimit rateLimit);

	
	/**
	 * Get rate-limit for APIid
	 * 
	 * @param apiID
	 * @return IRateLimit
	 */
	public IRateLimit getRateLimit(String apiID);


}