package br.com.nexdom.estoque_api.services;

import br.com.nexdom.estoque_api.dto.request.ProductRequest;
import br.com.nexdom.estoque_api.dto.request.ProductUpdateRequest;
import br.com.nexdom.estoque_api.dto.response.ProductByTypeResponse;
import br.com.nexdom.estoque_api.dto.response.ProductProfitResponse;
import br.com.nexdom.estoque_api.dto.response.ProductResponse;
import br.com.nexdom.estoque_api.entities.Product;
import br.com.nexdom.estoque_api.enums.ProductType;
import br.com.nexdom.estoque_api.mappers.ProductMapper;
import br.com.nexdom.estoque_api.repositories.ProductRepository;
import br.com.nexdom.estoque_api.repositories.StockMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;
    private final ProductMapper productMapper;

    public ProductResponse create(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        productRepository.save(product);

        return productMapper.toDTO(product);
    }

    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        return productMapper.toDTO(product);
    }

    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();

        return productMapper.toDTOList(products);
    }

    public ProductResponse update(ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productUpdateRequest.id())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        Product newProduct = productRepository.save(productMapper.toEntity(productUpdateRequest));

        return productMapper.toDTO(newProduct);
    }

    public void delete(Long id) {
        boolean hasMovements = stockMovementRepository.existsByProductId(id);

        if (hasMovements) {
            throw new IllegalStateException("Não é possível excluir um produto que já possui movimentações no estoque.");
        }

        productRepository.deleteById(id);
    }

    public List<ProductByTypeResponse> getProductsByType(ProductType type) {
        return productRepository.getProductsByType(type);
    }

    public List<ProductProfitResponse> getProfitPerProduct(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("A data de inicio não pdoe ser posterior à data de fim");
        }

        LocalDateTime startDateTime = Optional.ofNullable(startDate)
                .map(date -> date.atStartOfDay())
                .orElse(null);

        LocalDateTime endDateTime = Optional.ofNullable(endDate)
                .map(date -> date.atTime(23, 59, 59))
                .orElse(null);

        return productRepository.getProfitPerProduct(startDateTime, endDateTime);
    }
}
