package br.com.nexdom.estoque_api.dto.request;

import br.com.nexdom.estoque_api.enums.StockMovementStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StockMovementRequest(
        Long productId,
        StockMovementStatus movementType,
        BigDecimal salePrice,
        LocalDateTime movementDate,
        Integer movementQuantity
) {
}
