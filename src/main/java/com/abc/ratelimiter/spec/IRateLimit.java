package com.abc.ratelimiter.spec;

import com.abc.ratelimiter.exception.RateLimitException;

/**
 * Interface defining contract of Rate Limit API
 * 
 * @author ajay.jeswani
 *
 */
public interface IRateLimit {
	
	/**
	 * Different APIs would have different rate limits. Set default limits for an API.
	 * 
	 * @param threshold in seconds
	 */
	public void setDefaultLimit(long limit);

	/**
	 * Get default limits for an API.
	 * 
	 * @return long
	 */
	public long getDefaultLimit();

	/**
	 * Check if user is exceeding max rate limit 
	 * 
	 * @throws RateLimitException
	 */
	public void checkIfUserExceededRateLimit(String userId) throws RateLimitException;



}