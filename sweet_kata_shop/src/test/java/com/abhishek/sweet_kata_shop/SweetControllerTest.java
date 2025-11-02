package com.abhishek.sweet_kata_shop;

import com.abhishek.sweet_kata_shop.dto.SweetCreateRequest;
import com.abhishek.sweet_kata_shop.dto.SweetUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.jdbc.Sql;



@SpringBootTest
@AutoConfigureMockMvc
public class SweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateSweet()throws Exception{
        SweetCreateRequest sweetCreateRequest = new SweetCreateRequest();
        sweetCreateRequest.setName("Kaju Katli");
        sweetCreateRequest.setCategory("DryFruit");
        sweetCreateRequest.setPrice(new java.math.BigDecimal("350.50"));
        sweetCreateRequest.setQuantity(10);

        mockMvc.perform(post("/api/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweetCreateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kaju Katli"))
                .andExpect(jsonPath("$.category").value("DryFruit"));
    }

    @Test
    void testGetAllSweets() throws Exception {
        mockMvc.perform(get("/api/sweets"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindSweetById() throws Exception {
        mockMvc.perform(get("/api/sweets/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSweet() throws Exception {
        SweetUpdateRequest update = new SweetUpdateRequest();
        update.setName("Updated Sweet");
        update.setCategory("Bakery");
        update.setPrice(new BigDecimal("250.00"));
        update.setQuantity(20);

        mockMvc.perform(put("/api/sweets/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteSweet() throws Exception {
        mockMvc.perform(delete("/api/sweets/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPurchaseSweet() throws Exception {
        mockMvc.perform(post("/api/sweets/{id}/purchase", 1)
                        .param("qty", "5"))
                .andExpect(status().isOk());
    }

    @Test
    void testRestockSweet() throws Exception {
        mockMvc.perform(post("/api/sweets/{id}/restock", 1)
                        .param("qty", "10"))
                .andExpect(status().isOk());
    }
}
