package com.dukankhata.inventory;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.common.api.ApiResponse; import com.dukankhata.inventory.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*; import java.util.*;
@AuditResource("INVENTORY")
@RestController @RequestMapping("/api/v1/inventory") public class InventoryController { private final InventoryService service; public InventoryController(InventoryService service){this.service=service;}
 @PreAuthorize("hasRole('OWNER')")
 @PostMapping("/{productId}/receive") public ApiResponse<StockResponse> receive(@PathVariable UUID productId,@Valid @RequestBody StockReceiptRequest r){return ApiResponse.ok(service.receive(productId,r));}
 @PreAuthorize("hasRole('OWNER')")
 @PostMapping("/{productId}/adjust") public ApiResponse<StockResponse> adjust(@PathVariable UUID productId,@Valid @RequestBody StockAdjustmentRequest r){return ApiResponse.ok(service.adjust(productId,r));}
 @GetMapping("/low-stock") public ApiResponse<List<StockResponse>> lowStock(){return ApiResponse.ok(service.lowStock());}
 @GetMapping("/{productId}/history") public ApiResponse<List<StockMovementResponse>> history(@PathVariable UUID productId){return ApiResponse.ok(service.history(productId));}
}
