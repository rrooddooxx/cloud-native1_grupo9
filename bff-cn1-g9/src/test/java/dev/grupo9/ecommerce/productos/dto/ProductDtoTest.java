package dev.bast.ecommerce.productos.dto;

import dev.bast.ecommerce.productos.model.Promotion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class ProductDtoTest {

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setTitle("Test Product");
        productDto.setDescription("Test Description");
        productDto.setImageUrl("https://example.com/image.jpg");
        productDto.setPrice(99.99);
        productDto.setOwnerId(1L);
        productDto.setOwnerUsername("testuser");
    }

    @Test
    void testProductDtoCreation() {
        assertNotNull(productDto);
        assertEquals(1L, productDto.getId());
        assertEquals("Test Product", productDto.getTitle());
        assertEquals("Test Description", productDto.getDescription());
        assertEquals("https://example.com/image.jpg", productDto.getImageUrl());
        assertEquals(99.99, productDto.getPrice());
        assertEquals(1L, productDto.getOwnerId());
        assertEquals("testuser", productDto.getOwnerUsername());
    }

    @Test
    void testProductDtoWithPromotion() {
        Promotion promotion = new Promotion();
        promotion.setDiscount(15);
        promotion.setEndsAt(LocalDateTime.now().plusDays(5));
        
        productDto.setPromotion(promotion);
        
        assertNotNull(productDto.getPromotion());
        assertEquals(15, productDto.getPromotion().getDiscount());
        assertTrue(productDto.getPromotion().isActive());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        ProductDto dto = new ProductDto(1L, "Title", "Description", "imageUrl", 50.0, 2L, "owner", null, now, now);
        
        assertEquals(1L, dto.getId());
        assertEquals("Title", dto.getTitle());
        assertEquals("Description", dto.getDescription());
        assertEquals("imageUrl", dto.getImageUrl());
        assertEquals(50.0, dto.getPrice());
        assertEquals(2L, dto.getOwnerId());
        assertEquals("owner", dto.getOwnerUsername());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }
}