package dev.bast.ecommerce.productos.controller;

import dev.bast.ecommerce.productos.dto.PurchaseDto;
import dev.bast.ecommerce.productos.service.PurchaseService;
import dev.bast.ecommerce.usuarios.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@Tag(name = "Compras", description = "API para la gestión de compras y ventas")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Operation(summary = "Comprar producto", description = "Realiza la compra de un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Compra realizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "No puedes comprar tu propio producto")
    })
    @PostMapping("/buy/{productId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PurchaseDto> purchaseProduct(
            @PathVariable Long productId,
            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        PurchaseDto purchase = purchaseService.purchaseProduct(productId, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(purchase);
    }

    @Operation(summary = "Mis compras", description = "Obtiene el historial de compras del usuario autenticado")
    @GetMapping("/my-purchases")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<PurchaseDto>> getMyPurchases(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<PurchaseDto> purchases = purchaseService.getMyPurchases(userDetails.getId());
        return ResponseEntity.ok(purchases);
    }

    @Operation(summary = "Mis ventas", description = "Obtiene el historial de ventas del usuario autenticado")
    @GetMapping("/my-sales")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<PurchaseDto>> getMySales(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<PurchaseDto> sales = purchaseService.getMySales(userDetails.getId());
        return ResponseEntity.ok(sales);
    }
}