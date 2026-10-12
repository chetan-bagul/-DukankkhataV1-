package com.dukankhata.user.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record CreateStaffUserRequest(
 @NotNull UUID staffId,
 @NotBlank @Pattern(regexp="^[6-9]\\d{9}$", message="Mobile number must be a valid 10-digit Indian mobile number") String mobile,
 @NotBlank @Size(min=8,max=72) String password
) {}
