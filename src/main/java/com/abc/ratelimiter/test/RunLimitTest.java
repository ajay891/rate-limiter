package com.abc.ratelimiter.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.abc.ratelimiter.impl.RateLimitException;
import com.abc.ratelimiter.impl.RateLimitImpl;
import com.abc.ratelimiter.impl.RateLimitManager;


public class RunLimitTest {


	@Test
	public void testUserExceededRateLimit() throws InterruptedException{

		// Number of iterations to be executed (simulate load generation)
		long iterations = 500;
		// Give a sleep time of 1 ms for example to pump traffic every 1milliseconds 
		long sleepTime = 1;
		
		List<RateLimitException> rateLimitExceptions = new ArrayList<RateLimitException>();
		
		String apiID = "getOrder";
		String userID =  "ajay";
		RateLimitImpl rateLimiter = new RateLimitImpl(apiID);
		RateLimitManager._instance.provisionRateLimit(rateLimiter, apiID);
		for (int i = 0; i < iterations; i++) {
			try {
				RateLimitManager._instance.pegTraffic(apiID, userID);
			} catch (RateLimitException rateLimitException) {
				System.err.println("Rate Limit exceeed for user '" + userID + "' for API '" + apiID + "'. Error '" + rateLimitException.getMessage()  +"'");
				rateLimitExceptions.add(rateLimitException);
			}
			Thread.sleep(sleepTime);
		}
		org.junit.Assert.assertNotEquals(rateLimitExceptions.size() , 0);

	}
	
	@Test
	public void testUserDoesNotExceededRateLimit() throws InterruptedException{

		// Number of iterations to be executed (simulate load generation)
		long iterations = 1;
		// Give a sleep time of 1 ms for example to pump traffic every 1milliseconds 
		long sleepTime = 1;
		
		List<RateLimitException> rateLimitExceptions = new ArrayList<RateLimitException>();
		
		String apiID = "getOrder";
		String userID =  "ajay";
		RateLimitImpl rateLimiter = new RateLimitImpl(apiID);
		RateLimitManager._instance.provisionRateLimit(rateLimiter, apiID);
		for (int i = 0; i < iterations; i++) {
			try {
				RateLimitManager._instance.pegTraffic(apiID, userID);
			} catch (RateLimitException rateLimitException) {
				System.err.println("Rate Limit exceeed for user '" + userID + "' for API '" + apiID + "'. Error '" + rateLimitException.getMessage()  +"'");
				rateLimitExceptions.add(rateLimitException);
			}
			Thread.sleep(sleepTime);
		}
		Assert.assertEquals(rateLimitExceptions.size(), 0);
	}


	@Test
	public void testSetDefaultLimit() throws InterruptedException{
		String apiID = "getOrder";
		RateLimitImpl rateLimiter = new RateLimitImpl(apiID);
		rateLimiter.setDefaultLimits(200);
		Assert.assertEquals(rateLimiter.getDefaultLimits(), 200);

	}

}
