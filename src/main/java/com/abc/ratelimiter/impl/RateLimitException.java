package com.abc.ratelimiter.impl;

/**
 * Custom exception thrown when rate limit is exceeded
 * 
 * @author ajay.jeswani
 *
 */
public class RateLimitException extends Exception {
	
	private static final long serialVersionUID = 1L;

	RateLimitException(String s) {
		super(s);
	}
}
