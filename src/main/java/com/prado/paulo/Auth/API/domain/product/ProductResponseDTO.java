package com.prado.paulo.Auth.API.domain.product;

public record ProductResponseDTO(String id, String nome, Integer preco) {
    public ProductResponseDTO(Product product) {
        this(product.getId(), product.getName(), product.getPrice());
    }
    
}
