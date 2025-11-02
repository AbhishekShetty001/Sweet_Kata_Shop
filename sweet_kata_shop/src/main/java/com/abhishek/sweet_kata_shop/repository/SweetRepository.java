package com.abhishek.sweet_kata_shop.repository;

import com.abhishek.sweet_kata_shop.model.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SweetRepository extends JpaRepository<Sweet,Long> {
    Optional<Sweet> findByNameIgnoreCase(String name);
    List<Sweet> findByCategoryIgnoreCase(String category);
    List<Sweet> findByPriceBetween(BigDecimal min, BigDecimal max);
}
