package br.com.nexdom.estoque_api.services;

import br.com.nexdom.estoque_api.dto.request.ProductRequest;
import br.com.nexdom.estoque_api.dto.request.ProductUpdateRequest;
import br.com.nexdom.estoque_api.dto.response.ProductByTypeResponse;
<<<<<<< HEAD
=======
import br.com.nexdom.estoque_api.dto.response.ProductProfitResponse;
>>>>>>> 39d2c35 (added tests for service (#5))
import br.com.nexdom.estoque_api.dto.response.ProductResponse;
import br.com.nexdom.estoque_api.entities.Product;
import br.com.nexdom.estoque_api.enums.ProductType;
import br.com.nexdom.estoque_api.enums.StockMovementStatus;
import br.com.nexdom.estoque_api.mappers.ProductMapper;
import br.com.nexdom.estoque_api.repositories.ProductRepository;
import br.com.nexdom.estoque_api.repositories.StockMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
<<<<<<< HEAD
=======
import org.junit.jupiter.api.DisplayName;
>>>>>>> 39d2c35 (added tests for service (#5))
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
<<<<<<< HEAD
=======
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
>>>>>>> 39d2c35 (added tests for service (#5))
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
=======
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Tests")
>>>>>>> 39d2c35 (added tests for service (#5))
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
<<<<<<< HEAD
        product.setDescription("Test Description");
        product.setProductType(ProductType.ELETRONICO);
        product.setSupplierPrice(BigDecimal.valueOf(100.00));
        product.setStockQuantity(10);

        productRequest = new ProductRequest(
                "Test Product",
                ProductType.ELETRONICO,
                BigDecimal.valueOf(100.00),
=======
        product.setDescription("Notebook Dell");
        product.setProductType(ProductType.ELETRONICO);
        product.setSupplierPrice(new BigDecimal("2500.00"));
        product.setStockQuantity(10);

        productRequest = new ProductRequest(
                "Notebook Dell",
                ProductType.ELETRONICO,
                new BigDecimal("2500.00"),
>>>>>>> 39d2c35 (added tests for service (#5))
                10
        );

        productUpdateRequest = new ProductUpdateRequest(
                1L,
<<<<<<< HEAD
                "Test Product",
                ProductType.ELETRONICO,
                BigDecimal.valueOf(100.00),
                10
=======
                "Notebook Dell Updated",
                ProductType.ELETRONICO,
                new BigDecimal("2600.00"),
                15
>>>>>>> 39d2c35 (added tests for service (#5))
        );

        productResponse = new ProductResponse(
                1L,
<<<<<<< HEAD
                "Test Product",
                ProductType.ELETRONICO,
                BigDecimal.valueOf(100.00),
=======
                "Notebook Dell",
                ProductType.ELETRONICO,
                new BigDecimal("2500.00"),
>>>>>>> 39d2c35 (added tests for service (#5))
                10
        );
    }

    @Test
<<<<<<< HEAD
    void create_ShouldReturnProductResponse_WhenValidRequest() {
=======
    void shouldCreateProductSuccessfully() {
>>>>>>> 39d2c35 (added tests for service (#5))
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
<<<<<<< HEAD
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
=======
    void shouldGetProductByIdSuccessfully() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productResponse);

        // When
        ProductResponse result = productService.getById(1L);

        // Then
        assertThat(result)
                .isEqualTo(productResponse)
                .returns(1L, ProductResponse::id);

        verify(productRepository).findById(1L);
>>>>>>> 39d2c35 (added tests for service (#5))
        verify(productMapper).toDTO(product);
    }

    @Test
<<<<<<< HEAD
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
=======
    void shouldThrowExceptionWhenProductNotFoundById() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.getById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto não encontrado");

        verify(productRepository).findById(999L);
        verify(productMapper, never()).toDTO(any());
    }

    @Test
    void shouldGetAllProductsSuccessfully() {
        // Given
        Product product2 = new Product();
        product2.setId(2L);
        product2.setDescription("Mouse");
        product2.setProductType(ProductType.INFORMATICA);
        product2.setSupplierPrice(new BigDecimal("100.00"));
        product2.setStockQuantity(50);

        List<Product> products = List.of(product, product2);
        List<ProductResponse> responses = List.of(
                productResponse,
                new ProductResponse(2L, "Mouse", ProductType.INFORMATICA, new BigDecimal("100.00"), 50)
        );

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDTOList(products)).thenReturn(responses);
>>>>>>> 39d2c35 (added tests for service (#5))

        // When
        List<ProductResponse> result = productService.getAll();

        // Then
        assertThat(result)
<<<<<<< HEAD
                .hasSize(1)
                .isEqualTo(List.of(productResponse));
=======
                .hasSize(2)
                .isEqualTo(responses);
>>>>>>> 39d2c35 (added tests for service (#5))

        verify(productRepository).findAll();
        verify(productMapper).toDTOList(products);
    }

    @Test
<<<<<<< HEAD
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
=======
    void shouldReturnEmptyListWhenNoProductsExist() {
        // Given
        when(productRepository.findAll()).thenReturn(List.of());
        when(productMapper.toDTOList(any())).thenReturn(List.of());

        // When
        List<ProductResponse> result = productService.getAll();

        // Then
        assertThat(result)
                .isEmpty();

        verify(productRepository).findAll();
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productResponse);
>>>>>>> 39d2c35 (added tests for service (#5))

        // When
        ProductResponse result = productService.update(productUpdateRequest);

        // Then
        assertThat(result)
<<<<<<< HEAD
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
=======
                .isNotNull();

        assertThat(product.getDescription()).isEqualTo(productUpdateRequest.description());
        assertThat(product.getProductType()).isEqualTo(productUpdateRequest.productType());
        assertThat(product.getSupplierPrice()).isEqualTo(productUpdateRequest.supplierPrice());
        assertThat(product.getStockQuantity()).isEqualTo(productUpdateRequest.stockQuantity());

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
        verify(productMapper).toDTO(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.update(productUpdateRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Produto não encontrado");

        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldDeleteProductSuccessfullyWhenNoMovementsExist() {
        // Given
        when(stockMovementRepository.existsByProductId(1L)).thenReturn(false);

        // When
        productService.delete(1L);

        // Then
        verify(stockMovementRepository).existsByProductId(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when deleting product with movements")
    void shouldThrowExceptionWhenDeletingProductWithMovements() {
        // Given
        when(stockMovementRepository.existsByProductId(1L)).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> productService.delete(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Não é possível excluir um produto que já possui movimentações no estoque.");

        verify(stockMovementRepository).existsByProductId(1L);
        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    void shouldGetProductsByTypeSuccessfully() {
        // Given
        List<ProductByTypeResponse> responses = List.of(
                new ProductByTypeResponse(1L, "Product 1", ProductType.ELETRONICO, 100L, StockMovementStatus.SAIDA, 50)
        );

        when(productRepository.getProductsByType(ProductType.ELETRONICO)).thenReturn(responses);

        // When
        List<ProductByTypeResponse> result = productService.getProductsByType(ProductType.ELETRONICO);

        // Then
        assertThat(result)
                .hasSize(1)
                .isEqualTo(responses)
                .first()
                .returns(ProductType.ELETRONICO, ProductByTypeResponse::productType);

        verify(productRepository).getProductsByType(ProductType.ELETRONICO);
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsOfTypeExist() {
        // Given
        when(productRepository.getProductsByType(ProductType.MOVEL)).thenReturn(List.of());

        // When
        List<ProductByTypeResponse> result = productService.getProductsByType(ProductType.MOVEL);

        // Then
        assertThat(result)
                .isEmpty();

        verify(productRepository).getProductsByType(ProductType.MOVEL);
    }

    @Test
    void shouldGetProfitPerProductWithDateRangeSuccessfully() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        List<ProductProfitResponse> responses = List.of(
                new ProductProfitResponse(1L, "Product 1", 100L, new BigDecimal("5000.00"))
        );

        when(productRepository.getProfitPerProduct(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(responses);

        // When
        List<ProductProfitResponse> result = productService.getProfitPerProduct(startDate, endDate);

        // Then
        assertThat(result)
                .hasSize(1)
                .isEqualTo(responses)
                .first()
                .returns(new BigDecimal("5000.00"), ProductProfitResponse::profit);

        verify(productRepository).getProfitPerProduct(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void shouldGetProfitPerProductWithoutDateRangeSuccessfully() {
        // Given
        List<ProductProfitResponse> responses = List.of(
                new ProductProfitResponse(1L, "Product 1", 100L, new BigDecimal("5000.00"))
        );

        when(productRepository.getProfitPerProduct(null, null)).thenReturn(responses);

        // When
        List<ProductProfitResponse> result = productService.getProfitPerProduct(null, null);

        // Then
        assertThat(result)
            .hasSize(1);

        verify(productRepository).getProfitPerProduct(null, null);
    }

    @Test
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 12, 31);
        LocalDate endDate = LocalDate.of(2024, 1, 1);

        // When & Then
        assertThatThrownBy(() -> productService.getProfitPerProduct(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A data de inicio não pdoe ser posterior à data de fim");

        verify(productRepository, never()).getProfitPerProduct(any(), any());
    }

    @Test
    void shouldGetProfitWithOnlyStartDate() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        List<ProductProfitResponse> responses = List.of(
                new ProductProfitResponse(1L, "Product 1", 100L, new BigDecimal("5000.00"))
        );

        when(productRepository.getProfitPerProduct(any(LocalDateTime.class), eq(null)))
                .thenReturn(responses);

        // When
        List<ProductProfitResponse> result = productService.getProfitPerProduct(startDate, null);

        // Then
        assertThat(result)
                .hasSize(1);

        verify(productRepository).getProfitPerProduct(any(LocalDateTime.class), eq(null));
    }

    @Test
    void shouldGetProfitWithOnlyEndDate() {
        // Given
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        List<ProductProfitResponse> responses = List.of(
                new ProductProfitResponse(1L, "Product 1", 100L, new BigDecimal("5000.00"))
        );

        when(productRepository.getProfitPerProduct(eq(null), any(LocalDateTime.class)))
                .thenReturn(responses);

        // When
        List<ProductProfitResponse> result = productService.getProfitPerProduct(null, endDate);

        // Then
        assertThat(result)
                .hasSize(1);

        verify(productRepository).getProfitPerProduct(eq(null), any(LocalDateTime.class));
    }
>>>>>>> 39d2c35 (added tests for service (#5))
}