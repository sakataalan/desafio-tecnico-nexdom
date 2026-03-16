package br.com.nexdom.estoque_api.mappers;

import br.com.nexdom.estoque_api.dto.request.ProductRequest;
import br.com.nexdom.estoque_api.dto.request.ProductUpdateRequest;
import br.com.nexdom.estoque_api.dto.response.ProductResponse;
import br.com.nexdom.estoque_api.entities.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest productRequest) {
        if (productRequest == null) {
            return null;
        }

        Product product = new Product();

        product.setDescription(productRequest.description());
        product.setProductType(productRequest.productType());
        product.setSupplierPrice(productRequest.supplierPrice());
        product.setStockQuantity(productRequest.stockQuantity());

        return product;
    }

    public Product toEntity(ProductUpdateRequest productUpdateRequest) {
        if (productUpdateRequest == null) {
            return null;
        }

        Product product = new Product();

        product.setId(productUpdateRequest.id());
        product.setDescription(productUpdateRequest.description());
        product.setProductType(productUpdateRequest.productType());
        product.setSupplierPrice(productUpdateRequest.supplierPrice());
        product.setStockQuantity(productUpdateRequest.stockQuantity());

        return product;
    }

    public ProductResponse toDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductResponse(
            product.getId(),
            product.getDescription(),
            product.getProductType(),
            product.getSupplierPrice(),
            product.getStockQuantity()
        );
    }

    public List<ProductResponse> toDTOList(List<Product> products) {
        if (products == null) {
            return null;
        }

        return products.stream()
                .map(this::toDTO)
                .toList();
    }


}
