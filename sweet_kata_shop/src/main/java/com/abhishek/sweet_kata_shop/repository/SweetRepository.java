package com.abhishek.sweet_kata_shop.repository;

import com.abhishek.sweet_kata_shop.model.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SweetRepository extends JpaRepository<Sweet, Long> {

    Optional<Sweet> findByNameIgnoreCase(String name);

    List<Sweet> findByCategoryIgnoreCase(String category);

    List<Sweet> findByPriceBetween(BigDecimal min, BigDecimal max);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Sweet s where s.id = :id")
    Optional<Sweet> lockById(@Param("id") Long id);
}
