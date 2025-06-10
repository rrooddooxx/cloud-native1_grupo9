package dev.bast.ecommerce.productos.service;

import dev.bast.ecommerce.common.exception.ResourceNotFoundException;
import dev.bast.ecommerce.productos.dto.PurchaseDto;
import dev.bast.ecommerce.productos.model.Product;
import dev.bast.ecommerce.productos.model.Purchase;
import dev.bast.ecommerce.productos.repository.ProductRepository;
import dev.bast.ecommerce.productos.repository.PurchaseRepository;
import dev.bast.ecommerce.usuarios.model.User;
import dev.bast.ecommerce.usuarios.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, 
                          ProductRepository productRepository,
                          UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public PurchaseDto purchaseProduct(Long productId, Long buyerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (product.getOwnerId().equals(buyerId)) {
            throw new RuntimeException("You cannot buy your own product");
        }

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with id: " + buyerId));

        Purchase purchase = new Purchase();
        purchase.setBuyerId(buyerId);
        purchase.setSellerId(product.getOwnerId());
        purchase.setProductId(productId);
        purchase.setProductTitle(product.getTitle());
        purchase.setPrice(product.getPrice());

        Purchase savedPurchase = purchaseRepository.save(purchase);
        return convertToDto(savedPurchase);
    }

    public List<PurchaseDto> getMyPurchases(Long buyerId) {
        return purchaseRepository.findByBuyerIdOrderByPurchasedAtDesc(buyerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<PurchaseDto> getMySales(Long sellerId) {
        return purchaseRepository.findBySellerIdOrderByPurchasedAtDesc(sellerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PurchaseDto convertToDto(Purchase purchase) {
        PurchaseDto dto = new PurchaseDto();
        dto.setId(purchase.getId());
        dto.setBuyerId(purchase.getBuyerId());
        dto.setSellerId(purchase.getSellerId());
        dto.setProductId(purchase.getProductId());
        dto.setProductTitle(purchase.getProductTitle());
        dto.setPrice(purchase.getPrice());
        dto.setPurchasedAt(purchase.getPurchasedAt());

        // Set buyer username
        userRepository.findById(purchase.getBuyerId())
                .ifPresent(user -> dto.setBuyerUsername(user.getUsername()));

        // Set seller username
        userRepository.findById(purchase.getSellerId())
                .ifPresent(user -> dto.setSellerUsername(user.getUsername()));

        return dto;
    }
}