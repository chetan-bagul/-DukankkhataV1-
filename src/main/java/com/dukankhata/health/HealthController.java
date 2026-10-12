package com.dukankhata.health;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HealthController {
    private final JdbcTemplate jdbcTemplate;
    private final RedisConnectionFactory redisConnectionFactory;

    public HealthController(JdbcTemplate jdbcTemplate, RedisConnectionFactory redisConnectionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "dukankhata-backend");
    }

    @GetMapping("/live")
    public Map<String, String> live() {
        return Map.of("status", "UP", "check", "liveness");
    }

    @GetMapping("/ready")
    public Map<String, String> ready() {
        jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        String redis = redisConnectionFactory.getConnection().ping();
        if (redis == null) {
            throw new IllegalStateException("Redis readiness check failed");
        }
        return Map.of("status", "UP", "check", "readiness", "redis", "UP", "database", "UP");
    }
}
