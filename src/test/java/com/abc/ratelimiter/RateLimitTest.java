package com.abc.ratelimiter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.abc.ratelimiter.exception.RateLimitException;
import com.abc.ratelimiter.impl.RateLimitImpl;
import com.abc.ratelimiter.impl.RateLimitRegistry;
import com.abc.ratelimiter.spec.IRateLimit;

public class RateLimitTest {

	/**
	 * Logger to make sure code is PROD ready (No use of System.out.println)
	 */
	private static Logger logger = Logger.getLogger(RateLimitTest.class.getName());

	@Test
	@DisplayName("User exceeds rate-limit when traffic (1000) is higher than limit (100) per sec")
	public void testUserExceededRateLimit() throws InterruptedException {

		long iterations = 1000; // Send 1000 requests without any sleep. Default rate-limit is 100 hence this should fail

		List<RateLimitException> rateLimitExceptions = new ArrayList<RateLimitException>();

		String apiID = "getOrder";
		String userID = "ajay";
		RateLimitRegistry._instance.registerRateLimitForAPI(apiID, new RateLimitImpl(apiID));
		IRateLimit rateLimit = RateLimitRegistry._instance.getRateLimit(apiID);
		for (int i = 0; i < iterations; i++) {
			try {
				rateLimit.checkIfUserExceededRateLimit(userID);
			} catch (RateLimitException rateLimitException) {
				logger.warning("Rate Limit exceeed for user '" + rateLimitException.getUserID() + "' for API '"
						+ rateLimitException.getAPIid() + "'. Rate limit for API '" + rateLimitException.getRateLimit()
						+ "'");
				rateLimitExceptions.add(rateLimitException);
			}
		}
		assertNotEquals(rateLimitExceptions.size(), 0);

	}

	@Test
	@DisplayName("User does not exceed rate-limit when traffic (10) is less than limit (100) per sec")
	public void testUserDoesNotExceededRateLimit() throws InterruptedException {

		long iterations = 10; // Send 10 requests 
		long sleepTime = 100; // Give a sleep time of 100ms to achieve tps of 10  

		List<RateLimitException> rateLimitExceptions = new ArrayList<RateLimitException>();

		String apiID = "getOrder";
		String userID = "ajay";
		RateLimitRegistry._instance.registerRateLimitForAPI(apiID, new RateLimitImpl(apiID));
		IRateLimit rateLimit = RateLimitRegistry._instance.getRateLimit(apiID);
		for (int i = 0; i < iterations; i++) {
			try {
				rateLimit.checkIfUserExceededRateLimit(userID);
			} catch (RateLimitException rateLimitException) {
				logger.warning("Rate Limit exceeed for user '" + rateLimitException.getUserID() + "' for API '"
						+ rateLimitException.getAPIid() + "'. Rate limit for API '" + rateLimitException.getRateLimit()
						+ "'");
				rateLimitExceptions.add(rateLimitException);
			}
			Thread.sleep(sleepTime);
		}
		assertEquals(rateLimitExceptions.size(), 0);
	}

	@Test
	@DisplayName("Default limit (200) can be configured for an API")
	public void testSetDefaultLimit() throws InterruptedException {
		IRateLimit rateLimit = new RateLimitImpl("getOrder");
		rateLimit.setDefaultLimit(200);
		assertEquals(rateLimit.getDefaultLimit(), 200);

	}

}
