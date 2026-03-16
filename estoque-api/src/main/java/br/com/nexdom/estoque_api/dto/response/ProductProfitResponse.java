package br.com.nexdom.estoque_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductProfitResponse(
    Long id,
    String description,
    Long totalOutputMovement,
    BigDecimal profit
) {
}
