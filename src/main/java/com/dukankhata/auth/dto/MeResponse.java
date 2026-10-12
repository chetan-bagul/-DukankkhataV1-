package com.dukankhata.auth.dto;

import java.util.UUID;

public record MeResponse(
        UUID userId,
        UUID shopId,
        String name,
        String mobile,
        String role,
        String shopName,
        String operatingMode,
        String planStatus
) {}
