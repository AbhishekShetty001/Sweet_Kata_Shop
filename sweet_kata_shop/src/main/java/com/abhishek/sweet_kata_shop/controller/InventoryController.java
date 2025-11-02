package com.abhishek.sweet_kata_shop.controller;

import com.abhishek.sweet_kata_shop.dto.InventoryAdjustmentResponse;
import com.abhishek.sweet_kata_shop.dto.PurchaseRequest;
import com.abhishek.sweet_kata_shop.dto.RestockRequest;
import com.abhishek.sweet_kata_shop.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping("/{sweetId}/purchase")
    public ResponseEntity<InventoryAdjustmentResponse> purchase(
            @PathVariable Long sweetId, @RequestBody PurchaseRequest req) {
        return ResponseEntity.ok(service.purchase(sweetId, req.quantity));
    }

    @PostMapping("/{sweetId}/restock")
    public ResponseEntity<InventoryAdjustmentResponse> restock(
            @PathVariable Long sweetId, @RequestBody RestockRequest req) {
        return ResponseEntity.ok(service.restock(sweetId, req.quantity));
    }
}
