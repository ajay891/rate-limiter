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
	@DisplayName("Test to confirm user exceeds rate-limit when traffic (500) is higher than limit (100) per sec")
	public void testUserExceededRateLimit() throws InterruptedException {

		long iterations = 500; // Number of requests in test
		long sleepTime = 0; // Give a sleep time of 0 ms for example to pump traffic without any delay

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
						+ rateLimitException.getAppID() + "'. Rate limit for API '" + rateLimitException.getRateLimit()
						+ "'");
				rateLimitExceptions.add(rateLimitException);
			}
			Thread.sleep(sleepTime);
		}
		assertNotEquals(rateLimitExceptions.size(), 0);

	}

	@Test
	@DisplayName("Test to confirm user exceeds rate-limit when traffic (1) is less than limit (100) per sec")
	public void testUserDoesNotExceededRateLimit() throws InterruptedException {

		long iterations = 1; // Number of requests in test
		long sleepTime = 1; // Give a sleep time of 1 ms for example to pump traffic every 1milliseconds

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
						+ rateLimitException.getAppID() + "'. Rate limit for API '" + rateLimitException.getRateLimit()
						+ "'");
				rateLimitExceptions.add(rateLimitException);
			}
			Thread.sleep(sleepTime);
		}
		assertEquals(rateLimitExceptions.size(), 0);
	}

	@Test
	@DisplayName("Test to confirm default limit (200) can be configured for an API")
	public void testSetDefaultLimit() throws InterruptedException {
		RateLimitImpl rateLimiter = new RateLimitImpl("getOrder");
		rateLimiter.setDefaultLimit(200);
		assertEquals(rateLimiter.getDefaultLimit(), 200);

	}

}
