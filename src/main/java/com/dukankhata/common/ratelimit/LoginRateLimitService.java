package com.dukankhata.common.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class LoginRateLimitService {
    private final TokenBucketRateLimiter limiter;
    private final int capacity;
    private final Duration refillInterval;
    private final Duration stateTtl;

    public LoginRateLimitService(
            TokenBucketRateLimiter limiter,
            @Value("${security.rate-limit.login.capacity:5}") int capacity,
            @Value("${security.rate-limit.login.refill-interval-ms:12000}") long refillIntervalMs,
            @Value("${security.rate-limit.login.state-ttl-ms:120000}") long stateTtlMs) {
        this.limiter = limiter;
        this.capacity = capacity;
        this.refillInterval = Duration.ofMillis(refillIntervalMs);
        this.stateTtl = Duration.ofMillis(stateTtlMs);
    }

    public void check(String mobile, HttpServletRequest request) {
        String normalizedMobile = mobile == null ? "unknown" : mobile.trim();
        String ip = clientIp(request);

        String mobileKey = "dk:rate:login:mobile:" + normalizedMobile;
        String ipKey = "dk:rate:login:ip:" + ip;

        TokenBucketRateLimiter.Result result = limiter.tryConsume(
                mobileKey, ipKey, capacity, refillInterval, stateTtl);

        if (!result.allowed()) {
            throw new RateLimitExceededException(result.retryAfterSeconds());
        }
    }

    private String clientIp(HttpServletRequest request) {
        // Do not trust X-Forwarded-For until the deployment proxy is explicitly configured as trusted.
        String remote = request.getRemoteAddr();
        return remote == null || remote.isBlank() ? "unknown" : remote;
    }
}
