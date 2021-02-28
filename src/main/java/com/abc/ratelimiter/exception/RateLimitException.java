package com.abc.ratelimiter.exception;

/**
 * Custom exception thrown when rate limit is exceeded
 * 
 * @author ajay.jeswani
 *
 */
public class RateLimitException extends Exception {
	
	/**
	 * For serializing version of exception object
	 */
	private static final long serialVersionUID = 1L;
	
	private String appID;
	
	private long rateLimit;
	
	private String userID;

	public RateLimitException(String errorMessage, String appID, Long rateLimit, String userID) {
		super(errorMessage);
		this.appID = appID;
		this.rateLimit = rateLimit;
		this.userID = userID;
	}

	public String getAppID() {
		return appID;
	}

	public Long getRateLimit() {
		return rateLimit;
	}

	public String getUserID() {
		return userID;
	}

}
