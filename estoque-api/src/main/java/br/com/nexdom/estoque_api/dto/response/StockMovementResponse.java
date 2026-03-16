package br.com.nexdom.estoque_api.dto.response;

import br.com.nexdom.estoque_api.enums.StockMovementStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StockMovementResponse(
    Long id,
    ProductResponse product,
    StockMovementStatus stockMovementStatus,
    BigDecimal salePrice,
    Integer movementQuantity,
    LocalDateTime movementDate
) {

}
