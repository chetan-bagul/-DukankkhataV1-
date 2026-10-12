package com.dukankhata.common.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class CacheService {
    private final StringRedisTemplate redis;
    private final ObjectMapper objectMapper;

    public CacheService(StringRedisTemplate redis, ObjectMapper objectMapper) {
        this.redis = redis;
        this.objectMapper = objectMapper;
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        String value = redis.opsForValue().get(key);
        if (value == null) return Optional.empty();
        try {
            return Optional.of(objectMapper.readValue(value, type));
        } catch (JsonProcessingException ex) {
            redis.delete(key);
            return Optional.empty();
        }
    }

    public <T> void put(String key, T value, Duration ttl) {
        try {
            redis.opsForValue().set(key, objectMapper.writeValueAsString(value), ttl);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Unable to serialize cache value", ex);
        }
    }

    public void evict(String key) {
        redis.delete(key);
    }
}
