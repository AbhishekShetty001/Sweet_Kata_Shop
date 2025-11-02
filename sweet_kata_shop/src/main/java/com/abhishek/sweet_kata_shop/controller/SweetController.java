package com.abhishek.sweet_kata_shop.controller;

import com.abhishek.sweet_kata_shop.dto.SweetCreateRequest;
import com.abhishek.sweet_kata_shop.dto.SweetResponse;
import com.abhishek.sweet_kata_shop.dto.SweetUpdateRequest;
import com.abhishek.sweet_kata_shop.model.Sweet;
import com.abhishek.sweet_kata_shop.service.SweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sweets")
public class SweetController {
    private final SweetService servie;


    @PostMapping
    public ResponseEntity<Sweet> create(@RequestBody SweetCreateRequest request){
        return ResponseEntity.ok(servie.create(request));
    }

    @GetMapping
    public  ResponseEntity<List<Sweet>>getAllSweets( @RequestParam(required = false) String category,
                                                     @RequestParam(required = false) BigDecimal minPrice,
                                                     @RequestParam(required = false) BigDecimal maxPrice){
        return ResponseEntity.ok(servie.search(category,minPrice,maxPrice));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Sweet> findSweet(@PathVariable Long id){
        return ResponseEntity.ok(servie.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SweetResponse> updatesweet(@PathVariable Long id, SweetUpdateRequest request){
        return ResponseEntity.ok(servie.update(id,request));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delelteById(@PathVariable Long id){

        servie.deletebyid(id);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/purchase")
    public ResponseEntity<Sweet> purchase(@PathVariable Long id, @RequestParam int qty) {
        return ResponseEntity.ok(servie.purchase(id, qty));
    }

    @PostMapping("/{id}/restock")
    public ResponseEntity<Sweet> restock(@PathVariable Long id, @RequestParam int qty) {
        return ResponseEntity.ok(servie.restock(id, qty));
    }
}
