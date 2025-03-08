package com.prado.paulo.Auth.API.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prado.paulo.Auth.API.domain.product.Product;
import com.prado.paulo.Auth.API.domain.product.ProductRequestDTO;
import com.prado.paulo.Auth.API.domain.product.ProductResponseDTO;
import com.prado.paulo.Auth.API.repositories.ProductRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductRepository repository;
    
    @PostMapping
    public ResponseEntity<String> uploadProduct(@RequestBody ProductRequestDTO body) {
        Product product = new Product(body);

        this.repository.save(product);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getListOfProduct() {
        List<ProductResponseDTO> productList = this.repository.findAll().stream().map(ProductResponseDTO::new).toList();
        return ResponseEntity.ok().body(productList);
    }
}
