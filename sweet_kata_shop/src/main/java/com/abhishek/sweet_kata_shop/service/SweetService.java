package com.abhishek.sweet_kata_shop.service;

import com.abhishek.sweet_kata_shop.dto.SweetCreateRequest;
import com.abhishek.sweet_kata_shop.dto.SweetResponse;
import com.abhishek.sweet_kata_shop.dto.SweetUpdateRequest;
import com.abhishek.sweet_kata_shop.model.Sweet;
import com.abhishek.sweet_kata_shop.repository.SweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

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

    @Transactional(readOnly = true)
    public List<Sweet> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Sweet> search(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        if(category!= null) return repository.findByCategoryIgnoreCase(category);
        if(minPrice!=null && maxPrice!=null) return repository.findByPriceBetween(minPrice,maxPrice);
        return repository.findAll();

    }

    @Transactional(readOnly = true)
    public Sweet getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Sweet not found"));
    }

    public void deletebyid(Long id) {
        repository.deleteById(id);
    }

    public SweetResponse update(Long id, SweetUpdateRequest req) {
        Sweet s = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        if (req.getName() != null) s.setName(req.getName());
        if (req.getCategory() != null) s.setCategory(req.getCategory());
        if (req.getPrice() != null) s.setPrice(req.getPrice());
        if (req.getQuantity() != null) s.setQuantity(req.getQuantity());

        Sweet updated = repository.save(s);
        return new SweetResponse(
                updated.getName(),
                updated.getCategory(),
                updated.getPrice(),
                updated.getQuantity()
        );
    }



    public Sweet restock(Long id, int delta) {
        Sweet s = getById(id);
        s.setQuantity(s.getQuantity() + delta);
        return repository.save(s);
    }

    public Sweet purchase(Long id, int qty) {
        Sweet s = getById(id);
        if (s.getQuantity() < qty) throw new IllegalArgumentException("Insufficient stock");
        s.setQuantity(s.getQuantity() - qty);
        return repository.save(s);
    }
}
