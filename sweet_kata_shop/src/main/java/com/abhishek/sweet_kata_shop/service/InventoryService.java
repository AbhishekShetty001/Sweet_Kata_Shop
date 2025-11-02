package com.abhishek.sweet_kata_shop.service;



import com.abhishek.sweet_kata_shop.dto.InventoryAdjustmentResponse;
import com.abhishek.sweet_kata_shop.model.Sweet;
import com.abhishek.sweet_kata_shop.repository.SweetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final SweetRepository repo;

    public InventoryService(SweetRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public InventoryAdjustmentResponse restock(Long sweetId, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");
        Sweet s = repo.lockById(sweetId)
                .orElseThrow(() -> new IllegalArgumentException("Sweet not found"));
        int oldQ = s.getQuantity();
        s.setQuantity(oldQ + qty);
        repo.saveAndFlush(s);
        InventoryAdjustmentResponse resp = new InventoryAdjustmentResponse();
        resp.sweetId = sweetId;
        resp.oldQuantity = oldQ;
        resp.newQuantity = s.getQuantity();
        resp.operation = "RESTOCK";
        return resp;
    }

    @Transactional
    public InventoryAdjustmentResponse purchase(Long sweetId, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive");
        Sweet s = repo.lockById(sweetId)
                .orElseThrow(() -> new IllegalArgumentException("Sweet not found"));
        int oldQ = s.getQuantity();
        if (oldQ < qty) throw new IllegalStateException("Insufficient stock");
        s.setQuantity(oldQ - qty);
        repo.saveAndFlush(s);
        InventoryAdjustmentResponse resp = new InventoryAdjustmentResponse();
        resp.sweetId = sweetId;
        resp.oldQuantity = oldQ;
        resp.newQuantity = s.getQuantity();
        resp.operation = "PURCHASE";
        return resp;
    }
}