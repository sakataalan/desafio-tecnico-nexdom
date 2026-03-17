package br.com.nexdom.estoque_api.services;

import br.com.nexdom.estoque_api.dto.request.StockMovementRequest;
import br.com.nexdom.estoque_api.dto.response.StockMovementResponse;
import br.com.nexdom.estoque_api.entities.Product;
import br.com.nexdom.estoque_api.entities.StockMovement;
import br.com.nexdom.estoque_api.enums.ProductType;
import br.com.nexdom.estoque_api.enums.StockMovementStatus;
import br.com.nexdom.estoque_api.exceptions.InsufficientStockException;
import br.com.nexdom.estoque_api.mappers.StockMovementMapper;
import br.com.nexdom.estoque_api.repositories.ProductRepository;
import br.com.nexdom.estoque_api.repositories.StockMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StockMovementService Tests")
class StockMovementServiceTest {

    @Mock
    private StockMovementRepository stockMovementRepository;

    @Mock
    private StockMovementMapper stockMovementMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private StockMovementService stockMovementService;

    private Product product;
    private StockMovement stockMovement;
    private StockMovementRequest entradaRequest;
    private StockMovementRequest saidaRequest;
    private StockMovementResponse stockMovementResponse;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setDescription("Notebook Dell");
        product.setProductType(ProductType.ELETRONICO);
        product.setSupplierPrice(new BigDecimal("2500.00"));
        product.setStockQuantity(10);

        stockMovement = new StockMovement();
        stockMovement.setId(1L);
        stockMovement.setProduct(product);
        stockMovement.setMovementType(StockMovementStatus.ENTRADA);
        stockMovement.setMovementQuantity(5);
        stockMovement.setMovementDate(LocalDateTime.now());

        entradaRequest = new StockMovementRequest(
                1L,
                StockMovementStatus.ENTRADA,
                null,
                LocalDateTime.now(),
                5
        );

        saidaRequest = new StockMovementRequest(
                1L,
                StockMovementStatus.SAIDA,
                new BigDecimal("3000.00"),
                LocalDateTime.now(),
                3
        );

        stockMovementResponse = new StockMovementResponse(
                1L,
                null,
                StockMovementStatus.ENTRADA,
                null,
                5,
                LocalDateTime.now()
        );
    }

    @Test
    void shouldRegisterEntradaMovementSuccessfully() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(stockMovementMapper.toEntity(entradaRequest)).thenReturn(stockMovement);
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(stockMovement);
        when(stockMovementMapper.toDTO(stockMovement)).thenReturn(stockMovementResponse);

        int initialStock = product.getStockQuantity();
        int expectedStock = initialStock + entradaRequest.movementQuantity();

        // When
        StockMovementResponse result = stockMovementService.movement(entradaRequest);

        // Then
        assertThat(result)
                .isEqualTo(stockMovementResponse);

        assertThat(product.getStockQuantity()).isEqualTo(expectedStock);
        assertThat(stockMovement.getSalePrice()).isNull();
        assertThat(stockMovement.getProduct()).isEqualTo(product);

        verify(productRepository).findById(1L);
        verify(stockMovementRepository).save(any(StockMovement.class));
        verify(stockMovementMapper).toDTO(stockMovement);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundForEntrada() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> stockMovementService.movement(entradaRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto não encontrado");

        verify(productRepository).findById(1L);
        verify(stockMovementRepository, never()).save(any());
    }

    @Test
    void shouldRegisterSaidaMovementSuccessfully() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        StockMovement saidaMovement = new StockMovement();
        saidaMovement.setMovementType(StockMovementStatus.SAIDA);
        saidaMovement.setSalePrice(new BigDecimal("3000.00"));
        saidaMovement.setMovementQuantity(3);

        when(stockMovementMapper.toEntity(saidaRequest)).thenReturn(saidaMovement);
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(saidaMovement);
        when(stockMovementMapper.toDTO(any(StockMovement.class))).thenReturn(stockMovementResponse);

        int initialStock = product.getStockQuantity();
        int expectedStock = initialStock - saidaMovement.getMovementQuantity();

        // When
        StockMovementResponse result = stockMovementService.movement(saidaRequest);

        // Then
        assertThat(result)
                .isEqualTo(stockMovementResponse);

        assertThat(product.getStockQuantity()).isEqualTo(expectedStock);

        verify(productRepository).findById(1L);
        verify(stockMovementRepository).save(any(StockMovement.class));
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficientForSaida() {
        // Given
        product.setStockQuantity(2); // Stock menor que a quantidade solicitada

        StockMovementRequest insufficientRequest = new StockMovementRequest(
                1L,
                StockMovementStatus.SAIDA,
                new BigDecimal("3000.00"),
                LocalDateTime.now(),
                5 // Tentando retirar mais do que tem
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        StockMovement saidaMovement = new StockMovement();
        saidaMovement.setMovementType(StockMovementStatus.SAIDA);
        saidaMovement.setMovementQuantity(5);

        when(stockMovementMapper.toEntity(insufficientRequest)).thenReturn(saidaMovement);

        // When & Then
        assertThatThrownBy(() -> stockMovementService.movement(insufficientRequest))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessage("Estoque insuficiente para o produto '" + product.getDescription() + "'. Estoque atual: " + product.getStockQuantity());

        verify(productRepository).findById(1L);
        verify(stockMovementRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundForSaida() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> stockMovementService.movement(saidaRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto não encontrado");

        verify(productRepository).findById(1L);
        verify(stockMovementRepository, never()).save(any());
    }

    @Test
    void shouldAllowSaidaWhenStockEqualsRequestedQuantity() {
        // Given
        product.setStockQuantity(5);

        StockMovementRequest exactStockRequest = new StockMovementRequest(
                1L,
                StockMovementStatus.SAIDA,
                new BigDecimal("3000.00"),
                LocalDateTime.now(),
                5
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        StockMovement saidaMovement = new StockMovement();
        saidaMovement.setMovementType(StockMovementStatus.SAIDA);
        saidaMovement.setMovementQuantity(5);

        when(stockMovementMapper.toEntity(exactStockRequest)).thenReturn(saidaMovement);
        when(stockMovementRepository.save(saidaMovement)).thenReturn(saidaMovement);
        when(stockMovementMapper.toDTO(saidaMovement)).thenReturn(stockMovementResponse);

        // When
        StockMovementResponse result = stockMovementService.movement(exactStockRequest);

        // Then
        assertThat(result)
                .isEqualTo(stockMovementResponse);

        assertThat(product.getStockQuantity()).isZero();

        verify(stockMovementRepository).save(any(StockMovement.class));
    }

    @Test
    void shouldGetStockMovementByIdSuccessfully() {
        // Given
        when(stockMovementRepository.findById(1L)).thenReturn(Optional.of(stockMovement));
        when(stockMovementMapper.toDTO(stockMovement)).thenReturn(stockMovementResponse);

        // When
        StockMovementResponse result = stockMovementService.getById(1L);

        // Then
        assertThat(result)
                .isEqualTo(stockMovementResponse)
                .returns(1L, StockMovementResponse::id);

        verify(stockMovementRepository).findById(1L);
        verify(stockMovementMapper).toDTO(stockMovement);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when stock movement not found by id")
    void shouldThrowExceptionWhenStockMovementNotFoundById() {
        // Given
        when(stockMovementRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> stockMovementService.getById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Registro de movimento de estoque não encontrado");

        verify(stockMovementRepository).findById(999L);
        verify(stockMovementMapper, never()).toDTO(any());
    }

    @Test
    void shouldGetAllStockMovementsSuccessfully() {
        // Given
        StockMovement movement2 = new StockMovement();
        movement2.setId(2L);
        movement2.setProduct(product);
        movement2.setMovementType(StockMovementStatus.SAIDA);
        movement2.setMovementQuantity(3);

        List<StockMovement> movements = List.of(stockMovement, movement2);
        List<StockMovementResponse> responses = List.of(stockMovementResponse, stockMovementResponse);

        when(stockMovementRepository.findAll()).thenReturn(movements);
        when(stockMovementMapper.toDTOList(movements)).thenReturn(responses);

        // When
        List<StockMovementResponse> result = stockMovementService.getAll();

        // Then
        assertThat(result)
                .isEqualTo(responses)
                .hasSize(2);

        verify(stockMovementRepository).findAll();
        verify(stockMovementMapper).toDTOList(movements);
    }

    @Test
    void shouldReturnEmptyListWhenNoStockMovementsExist() {
        // Given
        when(stockMovementRepository.findAll()).thenReturn(List.of());
        when(stockMovementMapper.toDTOList(any())).thenReturn(List.of());

        // When
        List<StockMovementResponse> result = stockMovementService.getAll();

        // Then
        assertThat(result)
                .isEmpty();

        verify(stockMovementRepository).findAll();
    }

    @Test
    void shouldDeleteStockMovementSuccessfully() {
        // Given
        doNothing().when(stockMovementRepository).deleteById(1L);

        // When
        stockMovementService.delete(1L);

        // Then
        verify(stockMovementRepository).deleteById(1L);
    }

    @Test
    void shouldHandleDeleteOfNonExistentStockMovement() {
        // Given
        doNothing().when(stockMovementRepository).deleteById(anyLong());

        // When
        stockMovementService.delete(999L);

        // Then
        verify(stockMovementRepository).deleteById(999L);
    }
}