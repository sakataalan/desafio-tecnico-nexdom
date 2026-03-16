package br.com.nexdom.estoque_api.controllers;

import br.com.nexdom.estoque_api.dto.request.ProductRequest;
import br.com.nexdom.estoque_api.dto.request.ProductUpdateRequest;
import br.com.nexdom.estoque_api.dto.response.ProductByTypeResponse;
import br.com.nexdom.estoque_api.dto.response.ProductProfitResponse;
import br.com.nexdom.estoque_api.dto.response.ProductResponse;
import br.com.nexdom.estoque_api.enums.ProductType;
import br.com.nexdom.estoque_api.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest productRequest) {
        return productService.create(productRequest);
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }

    @PutMapping
    public ProductResponse update(@RequestBody ProductUpdateRequest productUpdateRequest) {
        return productService.update(productUpdateRequest);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/types")
    public ProductType[] getProductTypes() {
        return ProductType.values();
    }

    @GetMapping("/by-type/{type}")
    public List<ProductByTypeResponse> getProductsByType(@PathVariable ProductType type) {
        return productService.getProductsByType(type);
    }

    @GetMapping("/profit")
    public List<ProductProfitResponse> getProfitPerProduct(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        return productService.getProfitPerProduct(startDate, endDate);
    }
}
