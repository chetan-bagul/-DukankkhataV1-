package com.dukankhata.invoice;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.common.exception.ResourceNotFoundException; import com.dukankhata.common.security.CurrentUserService; import com.dukankhata.pos.SaleRepository; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.UUID;
@AuditResource("INVOICE")
@RestController @RequestMapping("/api/v1/invoices") public class InvoiceController {private final SaleRepository sales;private final InvoicePdfService pdf;private final CurrentUserService current;public InvoiceController(SaleRepository sales,InvoicePdfService pdf,CurrentUserService current){this.sales=sales;this.pdf=pdf;this.current=current;}
 @GetMapping(value="/{id}/pdf",produces="application/pdf") public ResponseEntity<byte[]> pdf(@PathVariable UUID id)throws Exception{var sale=sales.findByIdAndShopId(id,current.shopId()).orElseThrow(()->new ResourceNotFoundException("Sale not found"));return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+sale.getInvoiceNumber()+".pdf\"").contentType(MediaType.APPLICATION_PDF).body(pdf.generate(sale));}}
