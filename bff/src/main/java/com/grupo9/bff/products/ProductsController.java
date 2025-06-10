package com.grupo9.bff.products;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ProductsController {

    @GetMapping("/private/products")
    public String getProducts() {
        return "Productos!";
    }
}
