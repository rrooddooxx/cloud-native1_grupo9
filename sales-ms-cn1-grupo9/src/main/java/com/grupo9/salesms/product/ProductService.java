package com.grupo9.salesms.product;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public Optional<Product> getProductById(Long productId) {
    return productRepository.findById(productId).map(Product::fromEntity);
  }

  public Boolean isProductValid(Long productId) {
    Optional<Product> foundProduct = getProductById(productId);
    if (foundProduct.isEmpty()) {
      throw new RuntimeException("Product with id " + productId + " not found");
    }

    return foundProduct.get().getId().equals(productId);
  }
}
