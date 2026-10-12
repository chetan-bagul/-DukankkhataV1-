package com.dukankhata.shop.dto;
import jakarta.validation.constraints.*;
public record ShopSettingsRequest(@NotBlank(message="Shop name is required") @Size(max=120) String name,@Size(max=15) String gstin,@Size(max=160) String legalName,@Size(max=300) String addressLine,@Size(max=80) String city,@Size(max=80) String state,@Pattern(regexp="^$|^\\d{2}$",message="State code must be 2 digits") String stateCode,@Pattern(regexp="^$|^\\d{6}$",message="Pincode must be 6 digits") String pincode) {}
