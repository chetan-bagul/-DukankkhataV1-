package com.dukankhata.customer.dto;
import com.dukankhata.customer.Customer;
import java.math.BigDecimal; import java.util.UUID;
public record CustomerResponse(UUID id,Integer customerNumber,String name,String mobile,String address,String gstin,String stateCode,BigDecimal balance,boolean active){
 public static CustomerResponse from(Customer c){return new CustomerResponse(c.getId(),c.getCustomerNumber(),c.getName(),c.getMobile(),c.getAddress(),c.getGstin(),c.getStateCode(),c.getBalance(),c.isActive());}
}
