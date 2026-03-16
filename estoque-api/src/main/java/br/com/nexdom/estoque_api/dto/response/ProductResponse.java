package br.com.nexdom.estoque_api.dto.response;

import br.com.nexdom.estoque_api.enums.ProductType;

import java.math.BigDecimal;

public record ProductResponse(
    Long id,
    String description,
    ProductType productType,
    BigDecimal supplierValue,
    Integer stockQuantity
) {
}
