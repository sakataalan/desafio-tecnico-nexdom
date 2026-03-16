package br.com.nexdom.estoque_api.mappers;

import br.com.nexdom.estoque_api.dto.request.StockMovementRequest;
import br.com.nexdom.estoque_api.dto.response.ProductResponse;
import br.com.nexdom.estoque_api.dto.response.StockMovementResponse;
import br.com.nexdom.estoque_api.entities.StockMovement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockMovementMapper {

    public StockMovement toEntity(StockMovementRequest stockMovementRequest) {
        if (stockMovementRequest == null) {
            return null;
        }

        StockMovement stockMovement = new StockMovement();
        stockMovement.setMovementType(stockMovementRequest.movementType());
        stockMovement.setSalePrice(stockMovementRequest.salePrice());
        stockMovement.setMovementDate(stockMovementRequest.movementDate());
        stockMovement.setMovementQuantity(stockMovementRequest.movementQuantity());

        return stockMovement;
    }

    public StockMovementResponse toDTO(StockMovement stockMovement) {
        if (stockMovement == null) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse(
                stockMovement.getProduct().getId(),
                stockMovement.getProduct().getDescription(),
                stockMovement.getProduct().getProductType(),
                stockMovement.getProduct().getSupplierPrice(),
                stockMovement.getProduct().getStockQuantity()
        );

        return new StockMovementResponse(
                stockMovement.getId(),
                productResponse,
                stockMovement.getMovementType(),
                stockMovement.getSalePrice(),
                stockMovement.getMovementQuantity(),
                stockMovement.getMovementDate()
        );
    }

    public List<StockMovementResponse> toDTOList(List<StockMovement> stockMovements) {
        if (stockMovements == null) {
            return null;
        }

        return stockMovements.stream()
                .map(this::toDTO)
                .toList();
    }
}
