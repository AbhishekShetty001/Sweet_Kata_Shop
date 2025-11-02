package com.abhishek.sweet_kata_shop;
import com.abhishek.sweet_kata_shop.dto.PurchaseRequest;
import com.abhishek.sweet_kata_shop.dto.RestockRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPurchaseSweet() throws Exception {
        PurchaseRequest request = new PurchaseRequest();
        request.quantity = 3;

        mockMvc.perform(post("/api/inventory/{sweetId}/purchase", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newQuantity").exists());
    }

    @Test
    void testRestockSweet() throws Exception {
        RestockRequest request = new RestockRequest();
        request.quantity = 10;

        mockMvc.perform(post("/api/inventory/{sweetId}/restock", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newQuantity").exists());
    }
}
