package dev.bast.ecommerce.productos.repository;

import dev.bast.ecommerce.productos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByOwnerId(Long ownerId);
    
    @Query("SELECT p FROM Product p WHERE p.promotion.endsAt > CURRENT_TIMESTAMP")
    List<Product> findActivePromotions();
    
    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByTitleOrDescriptionContainingIgnoreCase(@Param("keyword") String keyword);
}