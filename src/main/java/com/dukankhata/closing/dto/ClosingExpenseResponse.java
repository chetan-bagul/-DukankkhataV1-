package com.dukankhata.closing.dto; import java.math.BigDecimal; import java.util.UUID; public record ClosingExpenseResponse(UUID id,String description,BigDecimal amount){}
