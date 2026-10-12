package com.dukankhata.customer.dto;

import jakarta.validation.constraints.*;
public record CustomerRequest(
    @NotBlank(message="Customer name is required") @Size(max=120,message="Customer name must not exceed 120 characters") String name,
    @Pattern(regexp="^$|^[6-9]\\d{9}$", message="Mobile number must be a valid 10-digit Indian mobile number") String mobile,
    @Size(max=300,message="Address must not exceed 300 characters") String address, @jakarta.validation.constraints.Pattern(regexp="^$|^[0-9A-Z]{15}$", message="GSTIN must be 15 characters") String gstin, @jakarta.validation.constraints.Pattern(regexp="^$|^\\d{2}$", message="State code must be 2 digits") String stateCode
) {}
