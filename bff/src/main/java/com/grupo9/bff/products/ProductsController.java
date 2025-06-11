package com.grupo9.bff.products;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/products")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<Product> products = productsService.getAllProducts();
            List<ProductDTO> productDTOs = products.stream()
                    .map(ProductDTO::fromProduct)
                    .toList();
            
            log.info("Retrieved {} products", productDTOs.size());
            return ResponseEntity.ok(productDTOs);
        } catch (Exception e) {
            log.error("Error retrieving products", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        try {
            return productsService.getProductById(id)
                    .map(ProductDTO::fromProduct)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error retrieving product with id: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
