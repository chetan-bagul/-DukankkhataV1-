package com.dukankhata.idempotency;

import com.dukankhata.common.exception.BusinessException;
import com.dukankhata.common.security.CurrentUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class IdempotencyService {
    private final IdempotencyRepository records;
    private final PostgresIdempotencyLockRepository locks;
    private final CurrentUserService current;
    private final ObjectMapper objectMapper;

    public IdempotencyService(IdempotencyRepository records, PostgresIdempotencyLockRepository locks,
                              CurrentUserService current, ObjectMapper objectMapper) {
        this.records = records; this.locks = locks; this.current = current; this.objectMapper = objectMapper;
    }

    @Transactional
    public <T> T execute(String operation, String rawKey, Object request, Class<T> responseType, Supplier<T> action) {
        String key = normalize(rawKey);
        UUID shopId = current.shopId(); UUID userId = current.requireUser().getId();
        String requestHash = sha256(write(request));
        String lockKey = shopId + ":" + userId + ":" + operation + ":" + key;
        locks.lock(lockKey);

        var existing = records.findByShopIdAndUserIdAndOperationAndIdempotencyKey(shopId, userId, operation, key);
        if (existing.isPresent()) {
            if (!existing.get().getRequestHash().equals(requestHash)) {
                throw new BusinessException("This Idempotency-Key was already used with a different request");
            }
            return read(existing.get().getResponseJson(), responseType);
        }

        T result = action.get();
        IdempotencyRecord record = new IdempotencyRecord();
        record.setShopId(shopId); record.setUserId(userId); record.setOperation(operation);
        record.setIdempotencyKey(key); record.setRequestHash(requestHash); record.setResponseJson(write(result));
        records.save(record);
        return result;
    }

    private String normalize(String raw) {
        if (raw == null || raw.isBlank()) throw new BusinessException("Idempotency-Key header is required for this operation");
        String key = raw.trim();
        if (key.length() > 128) throw new BusinessException("Idempotency-Key must be at most 128 characters");
        return key;
    }
    private String write(Object value) { try { return objectMapper.writeValueAsString(value); } catch (JsonProcessingException e) { throw new IllegalStateException("Could not serialize idempotent operation"); } }
    private <T> T read(String json, Class<T> type) { try { return objectMapper.readValue(json, type); } catch (JsonProcessingException e) { throw new IllegalStateException("Could not replay idempotent operation"); } }
    private String sha256(String value) { try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8))); } catch (Exception e) { throw new IllegalStateException(e); } }
}
