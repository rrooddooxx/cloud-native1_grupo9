package dev.bast.ecommerce.productos.controller;

import dev.bast.ecommerce.productos.dto.ProductDto;
import dev.bast.ecommerce.productos.service.ProductService;
import dev.bast.ecommerce.usuarios.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "API para la gestión de productos del e-commerce")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Obtener todos los productos", description = "Obtiene la lista de todos los productos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado", 
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Obtener mis productos", description = "Obtiene todos los productos del usuario autenticado")
    @GetMapping("/my-products")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ProductDto>> getMyProducts(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<ProductDto> products = productService.getProductsByOwner(userDetails.getId());
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Obtener productos con promoción", description = "Obtiene todos los productos que tienen promociones activas")
    @GetMapping("/promotions")
    public ResponseEntity<List<ProductDto>> getActivePromotions() {
        List<ProductDto> products = productService.getActivePromotions();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Buscar productos", description = "Busca productos por título o descripción")
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(
            @Parameter(description = "Palabra clave para buscar", required = true)
            @RequestParam String keyword) {
        List<ProductDto> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente", 
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(
            @Parameter(description = "Datos del producto a crear", required = true)
            @Valid @RequestBody ProductDto productDto,
            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ProductDto createdProduct = productService.createProduct(productDto, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente", 
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "403", description = "No tienes permisos para actualizar este producto")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del producto", required = true)
            @Valid @RequestBody ProductDto productDto,
            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ProductDto updatedProduct = productService.updateProduct(id, productDto, userDetails.getId());
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "403", description = "No tienes permisos para eliminar este producto")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        productService.deleteProduct(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}