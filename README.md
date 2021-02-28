# Rate Limiter

# Introduction 
A rate limiter is a tool that monitors the number of requests per a window time a service agrees to allow. If the request count exceeds the number agreed by the
service owner and the user (in a decided window time), the rate limiter blocks all the excess calls (say by throwing exceptions). The user can be a human or any other service (ex: in a micro service-based architecture)

# Objectives


- Different APIs would have different rate limits
- Should be possible to set default limits. This will be applied when an API specific limit has not been configured.
- The solution should consider rate limiting based on User+API combination
- The solution should be plug and play (easily configurable
- Production ready code (Automated tests are mandatory)

# Design

# Test results 

# How to build & plug it ?

