package org.project.courseWork.controller;

import lombok.AllArgsConstructor;
import org.project.courseWork.dto.MaterialCreationDto;
import org.project.courseWork.dto.MaterialDto;
import org.project.courseWork.dto.SuplierDto;
import org.project.courseWork.service.MaterialService;
import org.project.courseWork.service.SuplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;


import java.util.List;

@RestController
@RequestMapping("/api/materials")
@AllArgsConstructor
public class MaterialController {
    private final MaterialService materialService;
    private final SuplierService suplierService;

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDto> getMaterialById(@PathVariable Long id){
        return ResponseEntity.ok(materialService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<MaterialDto>> getPaginatedMaterials(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(materialService.getMaterials(page, size, sortBy, sortDir));
    }

    @GetMapping("/by-brand")
    public ResponseEntity<Page<MaterialDto>> getPaginatedMaterialsByBrand(
            @RequestParam String brand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(materialService.getMaterialsByBrand(brand, page, size));
    }

    @PostMapping
    public ResponseEntity<MaterialDto> createMaterial(@Valid @RequestBody MaterialCreationDto materialCreationDto){
        return new ResponseEntity(materialService.create(materialCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDto> updateMaterial(@PathVariable Long id ,@RequestBody MaterialDto updatedMaterial){
        MaterialDto updated = materialService.updateMaterial(id, updatedMaterial);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long id){
        materialService.deleteMaterial(id);
        return ResponseEntity.ok("Material deleted successfully.");
    }

    @DeleteMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        materialService.clearCache();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<List<MaterialDto>> getMaterialsByCategoryId(@PathVariable("id") Long categoryId) {
        List<MaterialDto> materials = materialService.getByCategoryId(categoryId);
        return ResponseEntity.ok(materials);
    }
}
