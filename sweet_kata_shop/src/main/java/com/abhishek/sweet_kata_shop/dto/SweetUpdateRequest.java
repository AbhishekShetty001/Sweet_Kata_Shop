package com.abhishek.sweet_kata_shop.dto;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SweetUpdateRequest {
    public String name;
    public String category;
    public BigDecimal price;
    public Integer quantity;
}