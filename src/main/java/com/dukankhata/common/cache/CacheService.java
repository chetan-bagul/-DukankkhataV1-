package com.dukankhata.common.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataAccessException;
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
        String value = read(key);
        if (value == null) return Optional.empty();
        try {
            return Optional.of(objectMapper.readValue(value, type));
        } catch (JsonProcessingException ex) {
            deleteQuietly(key);
            return Optional.empty();
        }
    }

    public <T> Optional<T> get(String key, TypeReference<T> type) {
        String value = read(key);
        if (value == null) return Optional.empty();
        try {
            return Optional.of(objectMapper.readValue(value, type));
        } catch (JsonProcessingException ex) {
            deleteQuietly(key);
            return Optional.empty();
        }
    }

    public <T> void put(String key, T value, Duration ttl) {
        try {
            redis.opsForValue().set(key, objectMapper.writeValueAsString(value), ttl);
        } catch (JsonProcessingException ex) {
            return;
        } catch (DataAccessException ex) {
            return;
        }
    }

    public void evict(String key) {
        deleteQuietly(key);
    }

    private String read(String key) {
        try {
            return redis.opsForValue().get(key);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private void deleteQuietly(String key) {
        try {
            redis.delete(key);
        } catch (DataAccessException ignored) {
        }
    }
}
