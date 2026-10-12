package com.dukankhata.user;
import com.dukankhata.common.audit.AuditResource; import com.dukankhata.common.api.ApiResponse; import com.dukankhata.user.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.*;
@AuditResource("USER") @RestController @RequestMapping("/api/v1/users") @PreAuthorize("hasRole('OWNER')")
public class UserManagementController {
 private final UserManagementService service; public UserManagementController(UserManagementService s){service=s;}
 @GetMapping public ApiResponse<List<UserResponse>> list(){return ApiResponse.ok(service.list());}
 @PostMapping("/staff") public ApiResponse<UserResponse> createStaff(@Valid @RequestBody CreateStaffUserRequest r){return ApiResponse.ok(service.createStaffUser(r));}
 @PatchMapping("/{id}/active") public ApiResponse<UserResponse> active(@PathVariable UUID id,@RequestParam boolean value){return ApiResponse.ok(service.setActive(id,value));}
}
