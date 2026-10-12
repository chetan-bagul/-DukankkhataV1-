package com.dukankhata.subscription.dto; import jakarta.validation.constraints.NotBlank; public record CreateOrderRequest(@NotBlank String planCode){}
