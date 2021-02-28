package com.abc.ratelimiter.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Logger;

import com.abc.ratelimiter.exception.RateLimitException;
import com.abc.ratelimiter.spec.IRateLimit;

/**
 * This class checks the rate limit for API for a user
 */
public class RateLimitImpl implements IRateLimit {

	/**
	 * Logger to make sure code is PROD ready (No use of System.out.println)
	 */
	private static Logger logger = Logger.getLogger(RateLimitImpl.class.getName());
	
	private String appID; // Hold appID, will be used to make custom exception aware of appID
	private long maxLimit = 100; // Default Limit
	private long tps;
	private long timeStamp;
	private Map<String, Long> userTransactionMap = new HashMap<String, Long>();
	private WriteLock wLock = new ReentrantReadWriteLock().writeLock();

	/**
	 * Default constructor with default limit 
	 * 
	 * @param apiId
	 */
	public RateLimitImpl(String appID) {
		this.appID = appID;
		this.timeStamp = System.currentTimeMillis();
	}
	
	/**
	 * Overloaded constructor to set default limit 
	 * 
	 * @param apiId
	 * @param limit
	 */
	public RateLimitImpl(String appID, long limit) {
		this.appID = appID;
		this.maxLimit = limit;
		this.timeStamp = System.currentTimeMillis();
	}


	/**
	 * Check if user is exceeding max rate limit 
	 * 
	 * @throws RateLimitException
	 */
	public void checkIfUserExceededRateLimit(String userID) throws RateLimitException {
		Object transactionCountCached = this.userTransactionMap.get(userID);
		long transactionCount;
		if (null == transactionCountCached) {
			transactionCount = 0;
		} else {
			transactionCount = (Long)transactionCountCached;
		}
		++transactionCount;
		wLock.lock();
		// Take the current timestamp
		long currentTime = System.currentTimeMillis();
		// Get the delta time elapsed
		double deltaTime = (currentTime - timeStamp);
		// Calculate transactionCount per second
		tps = (long) (transactionCount / deltaTime * 1000L);
		if (transactionCount != 1)
			logger.info("Request within max rate-limit '" + maxLimit + "', tps for user is '" + tps + "'");
		if (tps >= maxLimit && transactionCount != 1) {
			throw new RateLimitException("Rate limit has been exceeded", appID, maxLimit, userID);
		}
		wLock.unlock();
		userTransactionMap.put(userID, transactionCount);
	}
	
	
	/**
	 * Different APIs would have different rate limits. Set default limits for an API.
	 * 
	 * @param threshold in seconds
	 */
	public void setDefaultLimit(long limit) {
		this.maxLimit = limit;
	}

	/**
	 * Get default limits for an API.
	 * 
	 * @return long
	 */
	public long getDefaultLimit() {
		return this.maxLimit;
	}


}