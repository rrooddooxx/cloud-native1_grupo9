package dev.bast.ecommerce.productos.repository;

import dev.bast.ecommerce.productos.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    
    List<Purchase> findByBuyerIdOrderByPurchasedAtDesc(Long buyerId);
    
    List<Purchase> findBySellerIdOrderByPurchasedAtDesc(Long sellerId);
}