package com.skravetz.cn1grupo9.service;

import com.skravetz.cn1grupo9.dto.ProductDTO;
import com.skravetz.cn1grupo9.entity.Product;
import com.skravetz.cn1grupo9.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                product.getOwnerId(),
                product.getDiscount(),
                product.getEndsAt()
        );
    }
}