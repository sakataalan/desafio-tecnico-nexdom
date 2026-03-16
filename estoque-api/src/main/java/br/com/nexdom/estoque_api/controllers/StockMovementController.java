package br.com.nexdom.estoque_api.controllers;

import br.com.nexdom.estoque_api.dto.request.ProductRequest;
import br.com.nexdom.estoque_api.dto.request.StockMovementRequest;
import br.com.nexdom.estoque_api.dto.response.ProductResponse;
import br.com.nexdom.estoque_api.dto.response.StockMovementResponse;
import br.com.nexdom.estoque_api.services.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock-movement")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping
    public StockMovementResponse movement(@RequestBody StockMovementRequest stockMovementRequest) {
        return stockMovementService.movement(stockMovementRequest);
    }

    @GetMapping("/{id}")
    public StockMovementResponse getById(@PathVariable Long id) {
        return stockMovementService.getById(id);
    }

    @GetMapping
    public List<StockMovementResponse> getAll() {
        return stockMovementService.getAll();
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        stockMovementService.delete(id);
    }
}
