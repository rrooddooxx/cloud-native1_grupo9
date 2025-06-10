package dev.bast.ecommerce.productos.service;

import dev.bast.ecommerce.common.exception.ResourceNotFoundException;
import dev.bast.ecommerce.productos.dto.ProductDto;
import dev.bast.ecommerce.productos.model.Product;
import dev.bast.ecommerce.productos.repository.ProductRepository;
import dev.bast.ecommerce.usuarios.model.User;
import dev.bast.ecommerce.usuarios.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAllOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToDto(product);
    }

    public List<ProductDto> getProductsByOwner(Long ownerId) {
        return productRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getActivePromotions() {
        return productRepository.findActivePromotions()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(String keyword) {
        return productRepository.findByTitleOrDescriptionContainingIgnoreCase(keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto createProduct(ProductDto productDto, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + ownerId));

        Product product = convertToEntity(productDto);
        product.setOwnerId(ownerId);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto, Long ownerId) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!existingProduct.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("You can only update your own products");
        }

        existingProduct.setTitle(productDto.getTitle());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setPromotion(productDto.getPromotion());

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDto(updatedProduct);
    }

    public void deleteProduct(Long id, Long ownerId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!product.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("You can only delete your own products");
        }

        productRepository.delete(product);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setOwnerId(product.getOwnerId());
        dto.setPromotion(product.getPromotion());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        // Set owner username
        userRepository.findById(product.getOwnerId())
                .ifPresent(user -> dto.setOwnerUsername(user.getUsername()));

        return dto;
    }

    private Product convertToEntity(ProductDto dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());
        product.setPromotion(dto.getPromotion());
        return product;
    }
}