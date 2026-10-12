package com.dukankhata.product;

import com.dukankhata.product.dto.ProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductCacheService {
    private static final Duration TTL = Duration.ofMinutes(5);
    private final StringRedisTemplate redis;
    private final ObjectMapper objectMapper;

    public ProductCacheService(StringRedisTemplate redis, ObjectMapper objectMapper) {
        this.redis = redis;
        this.objectMapper = objectMapper;
    }

    private String key(UUID shopId) {
        return "dk:cache:products:" + shopId;
    }

    public Optional<List<ProductResponse>> get(UUID shopId) {
        final String value;
        try {
            value = redis.opsForValue().get(key(shopId));
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
        if (value == null) return Optional.empty();
        try {
            return Optional.of(objectMapper.readValue(value, new TypeReference<List<ProductResponse>>() {}));
        } catch (JsonProcessingException ex) {
            redis.delete(key(shopId));
            return Optional.empty();
        }
    }

    public void put(UUID shopId, List<ProductResponse> products) {
        try {
            redis.opsForValue().set(key(shopId), objectMapper.writeValueAsString(products), TTL);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Unable to cache products", ex);
        } catch (DataAccessException ignored) {
            // Cache is an optimization; database remains the source of truth.
        }
    }

    public void evict(UUID shopId) {
        try {
            redis.delete(key(shopId));
        } catch (DataAccessException ignored) {
            // Cache eviction failure does not invalidate the database write.
        }
    }

    public String keyFor(UUID shopId) {
        return key(shopId);
    }
}
