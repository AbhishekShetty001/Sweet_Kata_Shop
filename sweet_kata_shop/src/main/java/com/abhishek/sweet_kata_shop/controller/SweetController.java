package com.abhishek.sweet_kata_shop.controller;

import com.abhishek.sweet_kata_shop.dto.SweetCreateRequest;
import com.abhishek.sweet_kata_shop.model.Sweet;
import com.abhishek.sweet_kata_shop.service.SweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sweets")
public class SweetController {
    private final SweetService servie;


    @PostMapping
    public ResponseEntity<Sweet> create(@RequestBody SweetCreateRequest request){
        return ResponseEntity.ok(servie.create(request));
    }
}
