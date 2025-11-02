package com.abhishek.sweet_kata_shop.repository;

import com.abhishek.sweet_kata_shop.model.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SweetRepository extends JpaRepository<Sweet,Long> {

}
