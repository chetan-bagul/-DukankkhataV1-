package com.dukankhata.common.api;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        boolean success,
        int status,
        String code,
        String message,
        Map<String, String> errors,
        Instant timestamp,
        String path,
        String correlationId
) {}
