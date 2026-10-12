package com.dukankhata.shop.dto;
import com.dukankhata.shop.Shop;
import java.util.UUID;
public record ShopSettingsResponse(UUID id,String name,String gstin,String legalName,String addressLine,String city,String state,String stateCode,String pincode,String operatingMode,String planStatus){public static ShopSettingsResponse from(Shop s){return new ShopSettingsResponse(s.getId(),s.getName(),s.getGstin(),s.getLegalName(),s.getAddressLine(),s.getCity(),s.getState(),s.getStateCode(),s.getPincode(),s.getOperatingMode(),s.getPlanStatus());}}
