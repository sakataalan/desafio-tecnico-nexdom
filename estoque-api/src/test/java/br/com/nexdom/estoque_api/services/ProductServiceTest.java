package br.com.nexdom.estoque_api.services;

import br.com.nexdom.estoque_api.dto.request.ProductRequest;
import br.com.nexdom.estoque_api.dto.request.ProductUpdateRequest;
import br.com.nexdom.estoque_api.dto.response.ProductByTypeResponse;
import br.com.nexdom.estoque_api.dto.response.ProductResponse;
import br.com.nexdom.estoque_api.entities.Product;
import br.com.nexdom.estoque_api.enums.ProductType;
import br.com.nexdom.estoque_api.enums.StockMovementStatus;
import br.com.nexdom.estoque_api.mappers.ProductMapper;
import br.com.nexdom.estoque_api.repositories.ProductRepository;
import br.com.nexdom.estoque_api.repositories.StockMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockMovementRepository stockMovementRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequest productRequest;
    private ProductUpdateRequest productUpdateRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setDescription("Test Description");
        product.setProductType(ProductType.ELETRONICO);
        product.setSupplierPrice(BigDecimal.valueOf(100.00));
        product.setStockQuantity(10);

        productRequest = new ProductRequest(
                "Test Product",
                ProductType.ELETRONICO,
                BigDecimal.valueOf(100.00),
                10
        );

        productUpdateRequest = new ProductUpdateRequest(
                1L,
                "Test Product",
                ProductType.ELETRONICO,
                BigDecimal.valueOf(100.00),
                10
        );

        productResponse = new ProductResponse(
                1L,
                "Test Product",
                ProductType.ELETRONICO,
                BigDecimal.valueOf(100.00),
                10
        );
    }

    @Test
    void create_ShouldReturnProductResponse_WhenValidRequest() {
        // Given
        when(productMapper.toEntity(productRequest)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productResponse);

        // When
        ProductResponse result = productService.create(productRequest);

        // Then
        assertThat(result)
                .isEqualTo(productResponse);

        verify(productMapper).toEntity(productRequest);
        verify(productRepository).save(product);
        verify(productMapper).toDTO(product);
    }

    @Test
    void getById_ShouldReturnProductResponse_WhenProductExists() {
        // Given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productResponse);

        // When
        ProductResponse result = productService.getById(productId);

        // Then
        assertThat(result)
                .isEqualTo(productResponse);

        verify(productRepository).findById(productId);
        verify(productMapper).toDTO(product);
    }

    @Test
    void getById_ShouldThrowEntityNotFoundException_WhenProductDoesNotExist() {
        // Given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> productService.getById(productId)
        );

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(productRepository).findById(productId);
        verifyNoInteractions(productMapper);
    }

    @Test
    void getAll_ShouldReturnListOfProductResponses() {
        // Given
        List<Product> products = List.of(product);
        List<ProductResponse> productResponses = List.of(productResponse);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDTOList(products)).thenReturn(productResponses);

        // When
        List<ProductResponse> result = productService.getAll();

        // Then
        assertThat(result)
                .hasSize(1)
                .isEqualTo(List.of(productResponse));

        verify(productRepository).findAll();
        verify(productMapper).toDTOList(products);
    }

    @Test
    void update_ShouldReturnUpdatedProductResponse() {
        // Given
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setDescription("Old Product");

        ProductResponse updatedResponse = new ProductResponse(
                1L,
                "Updated Product",
                ProductType.ELETRONICO,
                BigDecimal.valueOf(150),
                20
        );

        when(productRepository.findById(productUpdateRequest.id())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(productMapper.toDTO(any(Product.class))).thenReturn(updatedResponse);

        // When
        ProductResponse result = productService.update(productUpdateRequest);

        // Then
        assertThat(result)
                .isEqualTo(updatedResponse);

        verify(productRepository).findById(productUpdateRequest.id());
        verify(productRepository).save(existingProduct);
        verify(productMapper).toDTO(existingProduct);
    }

    @Test
    void delete_ShouldDeleteProduct_WhenNoStockMovementsExist() {
        // Given
        Long productId = 1L;
        when(stockMovementRepository.existsByProductId(productId)).thenReturn(false);

        // When
        productService.delete(productId);

        // Then
        verify(stockMovementRepository).existsByProductId(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void delete_ShouldThrowIllegalStateException_WhenStockMovementsExist() {
        // Given
        Long productId = 1L;
        when(stockMovementRepository.existsByProductId(productId)).thenReturn(true);

        // When & Then
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> productService.delete(productId)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Não é possível excluir um produto que já possui movimentações no estoque.");

        verify(stockMovementRepository).existsByProductId(productId);
        verify(productRepository, never()).deleteById(productId);
    }

    @Test
    void getProductsByType_ShouldReturnProductsByType() {
        // Given
        ProductType type = ProductType.ELETRONICO;
        ProductByTypeResponse productByTypeResponse = new ProductByTypeResponse(
                1L,
                "Test Product",
                type,
                5,
                StockMovementStatus.SAIDA,
                4

        );

        List<ProductByTypeResponse> expectedResponse = List.of(productByTypeResponse);
        when(productRepository.getProductsByType(type)).thenReturn(expectedResponse);

        // When
        List<ProductByTypeResponse> result = productService.getProductsByType(type);

        // Then
        assertThat(result)
                .isEqualTo(List.of(productByTypeResponse));
        

        verify(productRepository).getProductsByType(type);
    }
//
//    @Test
//    @DisplayName("Should get profit per product with date range successfully")
//    void getProfitPerProduct_ShouldReturnProfitData_WithValidDateRange() {
//        // Given
//        LocalDate startDate = LocalDate.of(2023, 1, 1);
//        LocalDate endDate = LocalDate.of(2023, 12, 31);
//        LocalDateTime startDateTime = startDate.atStartOfDay();
//        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
//
//        ProductProfitResponse profitResponse = new ProductProfitResponse();
//        profitResponse.setProductId(1L);
//        profitResponse.setProductName("Test Product");
//        profitResponse.setTotalProfit(BigDecimal.valueOf(500.00));
//
//        List<ProductProfitResponse> expectedResponse = Arrays.asList(profitResponse);
//        when(productRepository.getProfitPerProduct(startDateTime, endDateTime)).thenReturn(expectedResponse);
//
//        // When
//        List<ProductProfitResponse> result = productService.getProfitPerProduct(startDate, endDate);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(profitResponse.getProductId(), result.get(0).getProductId());
//        assertEquals(profitResponse.getProductName(), result.get(0).getProductName());
//        assertEquals(profitResponse.getTotalProfit(), result.get(0).getTotalProfit());
//
//        verify(productRepository).getProfitPerProduct(startDateTime, endDateTime);
//    }
//
//    @Test
//    @DisplayName("Should get profit per product with null dates")
//    void getProfitPerProduct_ShouldReturnProfitData_WithNullDates() {
//        // Given
//        ProductProfitResponse profitResponse = new ProductProfitResponse();
//        profitResponse.setProductId(1L);
//        profitResponse.setProductName("Test Product");
//        profitResponse.setTotalProfit(BigDecimal.valueOf(500.00));
//
//        List<ProductProfitResponse> expectedResponse = Arrays.asList(profitResponse);
//        when(productRepository.getProfitPerProduct(null, null)).thenReturn(expectedResponse);
//
//        // When
//        List<ProductProfitResponse> result = productService.getProfitPerProduct(null, null);
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(productRepository).getProfitPerProduct(null, null);
//    }
//
//    @Test
//    @DisplayName("Should throw IllegalArgumentException when start date is after end date")
//    void getProfitPerProduct_ShouldThrowIllegalArgumentException_WhenStartDateAfterEndDate() {
//        // Given
//        LocalDate startDate = LocalDate.of(2023, 12, 31);
//        LocalDate endDate = LocalDate.of(2023, 1, 1);
//
//        // When & Then
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> productService.getProfitPerProduct(startDate, endDate)
//        );
//
//        assertEquals("A data de inicio não pode ser posterior à data de fim", exception.getMessage());
//        verifyNoInteractions(productRepository);
//    }
//
//    @Test
//    @DisplayName("Should handle single null start date")
//    void getProfitPerProduct_ShouldWork_WithNullStartDate() {
//        // Given
//        LocalDate endDate = LocalDate.of(2023, 12, 31);
//        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
//
//        ProductProfitResponse profitResponse = new ProductProfitResponse();
//        List<ProductProfitResponse> expectedResponse = Arrays.asList(profitResponse);
//        when(productRepository.getProfitPerProduct(null, endDateTime)).thenReturn(expectedResponse);
//
//        // When
//        List<ProductProfitResponse> result = productService.getProfitPerProduct(null, endDate);
//
//        // Then
//        assertNotNull(result);
//        verify(productRepository).getProfitPerProduct(null, endDateTime);
//    }
//
//    @Test
//    @DisplayName("Should handle single null end date")
//    void getProfitPerProduct_ShouldWork_WithNullEndDate() {
//        // Given
//        LocalDate startDate = LocalDate.of(2023, 1, 1);
//        LocalDateTime startDateTime = startDate.atStartOfDay();
//
//        ProductProfitResponse profitResponse = new ProductProfitResponse();
//        List<ProductProfitResponse> expectedResponse = Arrays.asList(profitResponse);
//        when(productRepository.getProfitPerProduct(startDateTime, null)).thenReturn(expectedResponse);
//
//        // When
//        List<ProductProfitResponse> result = productService.getProfitPerProduct(startDate, null);
//
//        // Then
//        assertNotNull(result);
//        verify(productRepository).getProfitPerProduct(startDateTime, null);
//    }
}