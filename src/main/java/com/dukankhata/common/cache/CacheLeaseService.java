package com.dukankhata.common.cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class CacheLeaseService {
    private static final String RELEASE_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private final StringRedisTemplate redis;

    public CacheLeaseService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public Lease tryAcquire(String key, Duration ttl) {
        String token = UUID.randomUUID().toString();
        try {
            Boolean acquired = redis.opsForValue().setIfAbsent(key, token, ttl);
            return Boolean.TRUE.equals(acquired) ? new Lease(key, token) : null;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public void release(Lease lease) {
        if (lease == null) return;
        try {
            redis.execute(
                    new org.springframework.data.redis.core.script.DefaultRedisScript<>(RELEASE_SCRIPT, Long.class),
                    java.util.List.of(lease.key()), lease.token()
            );
        } catch (DataAccessException ignored) {
            // Lease has a TTL; Redis availability should not break the request.
        }
    }

    public record Lease(String key, String token) {}
}
