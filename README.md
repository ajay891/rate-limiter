# Rate Limiter

## Introduction 
*Rate Limiter* is a tool that monitors the number of requests per a window time a service agrees to allow. If the request count exceeds the number agreed by the
service owner and the user (in a decided window time), the rate limiter blocks all the excess calls (say by throwing exceptions). The user can be a human or any other service (ex: in a micro service-based architecture)

## Objectives

- Different APIs would have different rate limits
- Should be possible to set default limits. This will be applied when an API specific limit has not been configured.
- The solution should consider rate limiting based on User+API combination
- The solution should be plug and play (easily configurable
- Production ready code (Automated tests are mandatory)

## Design

## How to Build & Test ?

- Project uses maven for packaging and it can be built by doing cd to project root aread and running below command windows command prompt or from Eclipse run configuration
```sh
mvn clean install
```
- After above build, output jar will **rate-limit-1.0.jar** will be available in target folder. This jar can be plugged in to any services.

![Screenshot](mvn-build.png) <!-- .element height="100%" width="100%" -->

- By running above maven build command, JUnit tests will be executed. Same JUnit tests can also be executed from Eclipse. 

![Screenshot](junit-test-automation.png) <!-- .element height="100%" width="100%" -->

## How to plug it ?

Below aspects have been taken in consideration in order to make PROD ready for 

- **Build & Test** - As shared above, *Rate Limiter* project has automatedt JUunit tests. Also, project uses maven for packaging
- **Logging** - Code also has logging in place (no system.out.println) for better understanding
- **Exception Handling** - Application throws custom RateLimitException which provides information about user is excedding rate-limit for which API and what is the default-max-limit for same API
- **Usage Example** - Below code sample & comments provide idea how **rate-limit-1.0.jar** can be used in other projects
```sh
// Register rate-limit for an API
RateLimitRegistry._instance.registerRateLimitForAPI(apiID, new RateLimitImpl(apiID)); 
// Get handle of rateLimit onject from register
IRateLimit rateLimit = RateLimitRegistry._instance.getRateLimit(apiID);
// Default rate-limit for API is 100. Override default max rate-liit for API if required
rateLimit.setDefaultLimit(200);
// Call checkIfUserExceededRateLimit method for every request
// RateLimitException will be thrown in case user exceeds max rate-limit for API
rateLimit.checkIfUserExceededRateLimit(userID);
```

