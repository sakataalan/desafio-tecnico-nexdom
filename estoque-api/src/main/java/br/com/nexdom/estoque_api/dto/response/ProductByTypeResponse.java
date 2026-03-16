package br.com.nexdom.estoque_api.dto.response;

import br.com.nexdom.estoque_api.enums.ProductType;
import br.com.nexdom.estoque_api.enums.StockMovementStatus;

public record ProductByTypeResponse(
    Long id,
    String description,
    ProductType productType,
    Long totalMovementQuantity,
    StockMovementStatus movementType,
    Integer stockQuantity
) {
}
