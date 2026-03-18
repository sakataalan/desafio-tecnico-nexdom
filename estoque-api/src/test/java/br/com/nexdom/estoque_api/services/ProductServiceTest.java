package br.com.nexdom.estoque_api.services;

import br.com.nexdom.estoque_api.dto.request.ProductRequest;
import br.com.nexdom.estoque_api.dto.request.ProductUpdateRequest;
import br.com.nexdom.estoque_api.dto.response.ProductByTypeResponse;
import br.com.nexdom.estoque_api.dto.response.ProductProfitResponse;
import br.com.nexdom.estoque_api.dto.response.ProductResponse;
import br.com.nexdom.estoque_api.entities.Product;
import br.com.nexdom.estoque_api.enums.ProductType;
import br.com.nexdom.estoque_api.enums.StockMovementStatus;
import br.com.nexdom.estoque_api.mappers.ProductMapper;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Tests")
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
        product.setDescription("Notebook Dell");
        product.setProductType(ProductType.ELETRONICO);
        product.setSupplierPrice(new BigDecimal("2500.00"));
        product.setStockQuantity(10);

        productRequest = new ProductRequest(
                "Notebook Dell",
                ProductType.ELETRONICO,
                new BigDecimal("2500.00"),
                10
        );

        productUpdateRequest = new ProductUpdateRequest(
                1L,
                "Notebook Dell Updated",
                ProductType.ELETRONICO,
                new BigDecimal("2600.00"),
                15
        );

        productResponse = new ProductResponse(
                1L,
                "Notebook Dell",
                ProductType.ELETRONICO,
                new BigDecimal("2500.00"),
                10
        );
    }

    @Test
    void shouldCreateProductSuccessfully() {
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
        verify(productMapper).toDTO(product);
    }

    @Test
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

        // When
        List<ProductResponse> result = productService.getAll();

        // Then
        assertThat(result)
                .hasSize(2)
                .isEqualTo(responses);

        verify(productRepository).findAll();
        verify(productMapper).toDTOList(products);
    }

    @Test
<<<<<<< HEAD
    void update_ShouldReturnUpdatedProductResponse() {
        // Given
        Product existingProduct = new Product();
        existingProduct.setId(1L);
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

        assertThat(result)
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
}