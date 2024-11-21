package org.project.courseWork.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.project.courseWork.dto.StockLevelCreationDto;
import org.project.courseWork.dto.StockLevelDto;
import org.project.courseWork.service.StockLevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;


import java.util.List;

@RestController
@RequestMapping("/api/stockLevels")
@AllArgsConstructor
public class StockLevelController {
    private final StockLevelService stockLevelService;

    @GetMapping("/{id}")
    public ResponseEntity<StockLevelDto> getStockLevelById(@PathVariable Long id){
        return ResponseEntity.ok(stockLevelService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<StockLevelDto>> getAllStockLevels(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String[] sortBy,
        @RequestParam(defaultValue = "asc") String sortDir) {

    Page<StockLevelDto> stockLevels = stockLevelService.getAllStockLevelsWithPagination(page, size, sortBy, sortDir);
    return ResponseEntity.ok(stockLevels);
}


    @PostMapping
    public ResponseEntity<StockLevelDto> createStockLevel(@Valid @RequestBody StockLevelCreationDto stockLevelCreationDto){
        return new ResponseEntity(stockLevelService.create(stockLevelCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockLevelDto> updateStockLevel(@PathVariable Long id, @RequestBody StockLevelDto updatedStockLevel){
        StockLevelDto updated = stockLevelService.updateStockLevel(id,updatedStockLevel);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStockLevel(@PathVariable Long id){
        stockLevelService.deleteStockLevel(id);
        return ResponseEntity.ok("Stock level deleted successfully.");
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StockLevelDto>> getFilteredStockLevels(
            @RequestParam(required = false) Integer currentCount,
            @RequestParam(required = false) Integer minimalQuantity,
            @RequestParam(required = false) Long materialId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String[] sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<StockLevelDto> stockLevels = stockLevelService.getFilteredStockLevels(
                currentCount, minimalQuantity, materialId, page, size, sortBy, sortDir);
        return ResponseEntity.ok(stockLevels);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<StockLevelDto>> getAllStockLevelsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String[] sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<StockLevelDto> stockLevels = stockLevelService.getAllStockLevelsWithPagination(page, size, sortBy, sortDir);
        return ResponseEntity.ok(stockLevels);
    }

}
