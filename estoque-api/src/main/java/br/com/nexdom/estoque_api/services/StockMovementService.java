package br.com.nexdom.estoque_api.services;

import br.com.nexdom.estoque_api.dto.request.StockMovementRequest;
import br.com.nexdom.estoque_api.dto.response.StockMovementResponse;
import br.com.nexdom.estoque_api.entities.Product;
import br.com.nexdom.estoque_api.entities.StockMovement;
import br.com.nexdom.estoque_api.enums.StockMovementStatus;
import br.com.nexdom.estoque_api.exceptions.InsufficientStockException;
import br.com.nexdom.estoque_api.mappers.StockMovementMapper;
import br.com.nexdom.estoque_api.repositories.ProductRepository;
import br.com.nexdom.estoque_api.repositories.StockMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final StockMovementMapper stockMovementMapper;
    private final ProductRepository productRepository;

    @Transactional
    public StockMovementResponse movement(StockMovementRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        StockMovement stockMovement = stockMovementMapper.toEntity(request);

        if (request.movementType() == StockMovementStatus.ENTRADA) {
            product.setStockQuantity(product.getStockQuantity() + request.movementQuantity());
            stockMovement.setSalePrice(null);
        }

        if (request.movementType() == StockMovementStatus.SAIDA) {
            if (product.getStockQuantity() < request.movementQuantity()) {
                throw new InsufficientStockException(
                    "Estoque insuficiente para o produto '" + product.getDescription() +
                    "'. Estoque atual: " + product.getStockQuantity()
                );
            }

            product.setStockQuantity(product.getStockQuantity() - request.movementQuantity());
        }


        stockMovement.setProduct(product);
        stockMovementRepository.save(stockMovement);

        return stockMovementMapper.toDTO(stockMovement);
    }

    public StockMovementResponse getById(Long id) {
        StockMovement stockMovement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de movimento de estoque não encontrado"));

        return stockMovementMapper.toDTO(stockMovement);
    }

    public List<StockMovementResponse> getAll() {
        List<StockMovement> stockMovement = stockMovementRepository.findAll();

        return stockMovementMapper.toDTOList(stockMovement);
    }

    public void delete(Long id) {
        stockMovementRepository.deleteById(id);
    }
}
