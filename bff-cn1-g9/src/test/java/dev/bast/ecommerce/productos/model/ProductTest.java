package dev.bast.ecommerce.productos.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setImageUrl("https://example.com/image.jpg");
        product.setPrice(99.99);
        product.setOwnerId(1L);
    }

    @Test
    void testProductCreation() {
        assertNotNull(product);
        assertEquals("Test Product", product.getTitle());
        assertEquals("Test Description", product.getDescription());
        assertEquals("https://example.com/image.jpg", product.getImageUrl());
        assertEquals(99.99, product.getPrice());
        assertEquals(1L, product.getOwnerId());
    }

    @Test
    void testPromotionEmbedded() {
        Promotion promotion = new Promotion();
        promotion.setDiscount(20);
        promotion.setEndsAt(LocalDateTime.now().plusDays(7));
        
        product.setPromotion(promotion);
        
        assertNotNull(product.getPromotion());
        assertEquals(20, product.getPromotion().getDiscount());
        assertTrue(product.getPromotion().isActive());
    }

    @Test
    void testPromotionExpired() {
        Promotion promotion = new Promotion();
        promotion.setDiscount(20);
        promotion.setEndsAt(LocalDateTime.now().minusDays(1));
        
        product.setPromotion(promotion);
        
        assertFalse(product.getPromotion().isActive());
    }
}