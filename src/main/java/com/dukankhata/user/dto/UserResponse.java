package com.dukankhata.user.dto;
import com.dukankhata.auth.User;
import java.time.Instant; import java.util.UUID;
public record UserResponse(UUID id, UUID staffId, String name, String mobile, String role, boolean active, Instant createdAt) {
 public static UserResponse from(User u){return new UserResponse(u.getId(),u.getStaff()==null?null:u.getStaff().getId(),u.getName(),u.getMobile(),u.getRole(),u.isActive(),u.getCreatedAt());}
}
