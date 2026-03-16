package br.com.nexdom.estoque_api.dto.request;

import br.com.nexdom.estoque_api.enums.ProductType;

import java.math.BigDecimal;

public record ProductRequest(
        String description,
        ProductType productType,
        BigDecimal supplierPrice,
        Integer stockQuantity
) {
}
