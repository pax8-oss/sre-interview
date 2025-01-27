package com.pax8.catalog.service;

import com.pax8.catalog.dto.ProductRequest;
import com.pax8.catalog.dto.ProductResponse;
import com.pax8.catalog.dto.ProductUpdateRequest;
import com.pax8.catalog.entity.Product;
import com.pax8.catalog.exception.ResourceNotFoundException;
import com.pax8.catalog.mapper.ProductMapper;
import com.pax8.catalog.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequest productRequest;
    private ProductUpdateRequest productUpdateRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID productId = UUID.randomUUID();
        Instant now = Instant.now();

        product = Product.builder()
            .id(productId)
            .name("Test Product")
            .description("Test Description")
            .price(BigDecimal.valueOf(99.99))
            .created(now)
            .updated(now)
            .build();

        productRequest = new ProductRequest(
            "Test Product",
            "Test Description",
            BigDecimal.valueOf(99.99)
        );

        productUpdateRequest = new ProductUpdateRequest(
            "Test Product",
            "Test Description",
            BigDecimal.valueOf(99.99)
        );

        productResponse = new ProductResponse(
            productId,
            "Test Product",
            "Test Description",
            BigDecimal.valueOf(99.99),
            now,
            now
        );
    }

    @Test
    void testGetProducts() {
        Pageable pageable = mock(Pageable.class);
        Page<Product> productPage = new PageImpl<>(List.of(product));
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<ProductResponse> responses = productService.getProducts(pageable);

        verify(productRepository, times(1)).findAll(pageable);
        assertThat(responses.getContent()).hasSize(1);
        assertThat(responses.getContent().get(0).name()).isEqualTo(product.getName());
    }

    @Test
    void testGetProduct() throws ResourceNotFoundException {
        UUID productId = product.getId();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        assertThat(response.name()).isEqualTo(product.getName());
        assertThat(response.description()).isEqualTo(product.getDescription());
    }

    @Test
    void testGetProduct_NotFound() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(productId));

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(productRequest);

        verify(productRepository, times(1)).save(any(Product.class));
        assertThat(response.name()).isEqualTo(product.getName());
        assertThat(response.price()).isEqualTo(product.getPrice());

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        assertThat(savedProduct.getName()).isEqualTo(productRequest.name());
    }

    @Test
    void testUpdateProduct() throws ResourceNotFoundException {
        UUID productId = product.getId();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.updateProduct(productId, productUpdateRequest);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
        assertThat(response.name()).isEqualTo(productRequest.name());
    }

    @Test
    void testUpdateProduct_NotFound() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, productUpdateRequest));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() throws ResourceNotFoundException {
        UUID productId = product.getId();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_NotFound() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productId));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).delete(any(Product.class));
    }
}
