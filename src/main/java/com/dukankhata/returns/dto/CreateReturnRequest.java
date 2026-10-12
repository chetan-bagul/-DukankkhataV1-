package com.dukankhata.returns.dto;
import com.dukankhata.returns.ReturnRefundMethod; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.util.*;
public record CreateReturnRequest(@NotBlank @Size(max=500) String reason,@NotEmpty @Valid List<Refund> refunds){ public record Refund(@NotNull ReturnRefundMethod method,@NotNull @DecimalMin("0.01") BigDecimal amount){} }
