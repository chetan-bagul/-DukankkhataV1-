package com.dukankhata.supplier.dto;
import jakarta.validation.constraints.NotBlank;
public record SupplierRequest(@NotBlank(message="Supplier name is required") String name,String mobile,String address){}
