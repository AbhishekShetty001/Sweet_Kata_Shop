package com.abhishek.sweet_kata_shop;

import com.abhishek.sweet_kata_shop.dto.SweetCreateRequest;
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
}
