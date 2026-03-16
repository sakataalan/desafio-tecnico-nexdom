package br.com.nexdom.estoque_api.repositories;

import br.com.nexdom.estoque_api.dto.response.ProductByTypeResponse;
import br.com.nexdom.estoque_api.dto.response.ProductProfitResponse;
import br.com.nexdom.estoque_api.entities.Product;
import br.com.nexdom.estoque_api.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
        SELECT new br.com.nexdom.estoque_api.dto.response.ProductByTypeResponse(
            A.id,
            A.description,
            A.productType,
            SUM(B.movementQuantity),
            B.movementType,
            A.stockQuantity
        )
        FROM Product A
        JOIN StockMovement B
            ON B.product = A AND B.movementType = 'SAIDA'
        WHERE A.stockQuantity > 0
        AND A.productType = :productType
        GROUP BY A.id, A.description, A.productType, A.stockQuantity
    """)
    List<ProductByTypeResponse> getProductsByType(@Param("productType") ProductType productType);

    @Query(value = """
        SELECT new br.com.nexdom.estoque_api.dto.response.ProductProfitResponse(
            A.id,
            A.description,
            SUM(B.movementQuantity),
            SUM((B.salePrice - A.supplierPrice) * B.movementQuantity)
        )
        FROM Product A
        JOIN StockMovement B ON B.product = A
        AND B.movementType = 'SAIDA'
        WHERE (:startDate IS NULL OR B.movementDate >= :startDate)
        AND (:endDate IS NULL OR B.movementDate <= :endDate)
        GROUP BY A.id, A.description
        ORDER BY SUM((B.salePrice - A.supplierPrice) * B.movementQuantity) DESC
    """)
    List<ProductProfitResponse> getProfitPerProduct(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}


