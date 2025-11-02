package com.abhishek.sweet_kata_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryAdjustmentResponse {
    public Long sweetId;
    public int oldQuantity;
    public int newQuantity;
    public String operation;
}
