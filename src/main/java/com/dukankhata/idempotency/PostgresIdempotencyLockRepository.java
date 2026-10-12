package com.dukankhata.idempotency;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresIdempotencyLockRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostgresIdempotencyLockRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void lock(String lockKey) {
        jdbcTemplate.execute((java.sql.Connection conn) -> {
            try (java.sql.PreparedStatement ps = conn.prepareStatement("select pg_advisory_xact_lock(hashtextextended(?, 0))")) {
                ps.setString(1, lockKey);
                ps.execute();
            }
            return null;
        });
    }
}
