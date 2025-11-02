package com.abhishek.sweet_kata_shop.service;

import com.abhishek.sweet_kata_shop.dto.SweetCreateRequest;
import com.abhishek.sweet_kata_shop.model.Sweet;
import com.abhishek.sweet_kata_shop.repository.SweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
@Transactional
public class SweetService {

    private final SweetRepository repository;

    public Sweet create(SweetCreateRequest req){
        Sweet sweet = Sweet.builder().
                name(req.getName()).
                category(req.getCategory()).
                price(req.getPrice()).
                quantity(req.getQuantity())
                .build();


        return repository.save(sweet);

    }
}
