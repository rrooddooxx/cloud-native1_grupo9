package com.grupo9.inventorymscn1grupo9.inventory;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final InventoryService inventoryService;

  @GetMapping()
  public ResponseEntity<List<InventoryEntity>> getAllInventory() {
    var response = inventoryService.getAllInventory();
    return ResponseEntity.ok(response);
  }
}
