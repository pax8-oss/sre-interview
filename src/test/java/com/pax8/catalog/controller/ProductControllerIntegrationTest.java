package com.pax8.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pax8.catalog.dto.ProductRequest;
import com.pax8.catalog.dto.ProductResponse;
import com.pax8.catalog.dto.ProductUpdateRequest;
import com.pax8.catalog.repository.ProductRepository;
import com.pax8.catalog.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

    private ProductResponse sampleProductResponse;
    private ProductRequest sampleProductRequest;

    @BeforeEach
    void setup() {
        UUID productId = UUID.randomUUID();
        sampleProductResponse = new ProductResponse(
            productId,
            "Sample Product",
            "Description of Product",
            BigDecimal.valueOf(99.99),
            null,
            null
        );

        sampleProductRequest = new ProductRequest(
            "Sample Product",
            "Description of Product",
            BigDecimal.valueOf(99.99)
        );
    }

    @Test
    void getProducts_ReturnsPagedProducts() throws Exception {
        Page<ProductResponse> productPage = new PageImpl<>(
            List.of(sampleProductResponse),
            PageRequest.of(0, 10),
            1
        );

        Mockito.when(productService.getProducts(any(PageRequest.class)))
            .thenReturn(productPage);

        mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name", is("Sample Product")))
            .andExpect(jsonPath("$.content[0].price", is(99.99)));
    }

    @Test
    void getProduct_ReturnsProductById() throws Exception {
        Mockito.when(productService.getProduct(any(UUID.class)))
            .thenReturn(sampleProductResponse);

        mockMvc.perform(get("/products/{id}", sampleProductResponse.id())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Sample Product")))
            .andExpect(jsonPath("$.price", is(99.99)));
    }

    @Test
    void createProduct_CreatesAndReturnsProduct() throws Exception {
        Mockito.when(productService.createProduct(any(ProductRequest.class)))
            .thenReturn(sampleProductResponse);

        mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(sampleProductRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Sample Product")))
            .andExpect(jsonPath("$.price", is(99.99)));
    }

    @Test
    void updateProduct_UpdatesAndReturnsProduct() throws Exception {
        Mockito.when(productService.updateProduct(any(UUID.class), any(ProductUpdateRequest.class)))
            .thenReturn(sampleProductResponse);

        mockMvc.perform(put("/products/{id}", sampleProductResponse.id())
                .content(objectMapper.writeValueAsString(sampleProductRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Sample Product")))
            .andExpect(jsonPath("$.price", is(99.99)));
    }

    @Test
    void deleteProduct_DeletesProductById() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(any(UUID.class));

        mockMvc.perform(delete("/products/{id}", sampleProductResponse.id())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}
