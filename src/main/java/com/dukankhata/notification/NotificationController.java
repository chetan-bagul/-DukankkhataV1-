package com.dukankhata.notification;
import com.dukankhata.common.api.ApiResponse; import com.dukankhata.notification.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/v1/notifications")
public class NotificationController{
 private final NotificationService service; public NotificationController(NotificationService s){service=s;}
 @GetMapping public ApiResponse<List<NotificationResponse>> latest(){return ApiResponse.ok(service.latest());}
 @PostMapping @PreAuthorize("hasRole('OWNER')") public ApiResponse<NotificationResponse> send(@Valid @RequestBody CreateNotificationRequest r){return ApiResponse.ok(service.createAndSend(r));}
 @PostMapping("/{id}/retry") @PreAuthorize("hasRole('OWNER')") public ApiResponse<NotificationResponse> retry(@PathVariable UUID id){return ApiResponse.ok(service.retry(id));}
}
