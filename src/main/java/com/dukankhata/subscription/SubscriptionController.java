package com.dukankhata.subscription;
import com.dukankhata.auth.User; import com.dukankhata.common.api.ApiResponse; import com.dukankhata.subscription.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/v1/subscription") public class SubscriptionController {
 private final SubscriptionService service; public SubscriptionController(SubscriptionService service){this.service=service;}
 @GetMapping("/plans") public ApiResponse<List<PlanResponse>> plans(){return ApiResponse.ok(service.plans());}
 @GetMapping("/current") public ApiResponse<SubscriptionResponse> current(Authentication a){return ApiResponse.ok(service.current((User)a.getPrincipal()));}
 @PreAuthorize("hasRole('OWNER')") @PostMapping("/orders") public ApiResponse<OrderResponse> order(@Valid @RequestBody CreateOrderRequest r,Authentication a){return ApiResponse.ok(service.createOrder((User)a.getPrincipal(),r.planCode()));}
 @PreAuthorize("hasRole('OWNER')") @PostMapping("/verify") public ApiResponse<SubscriptionResponse> verify(@Valid @RequestBody VerifyPaymentRequest r,Authentication a){return ApiResponse.ok(service.verify((User)a.getPrincipal(),r));}
 @PostMapping("/webhook") public ApiResponse<Void> webhook(@RequestHeader(value="X-Razorpay-Signature",required=false) String sig,@RequestBody String body){service.processWebhook(body,sig==null?"":sig);return ApiResponse.success("Webhook received");}
}
