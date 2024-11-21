package org.project.courseWork.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.project.courseWork.dto.MaterialDto;
import org.project.courseWork.dto.SuplierCreationDto;
import org.project.courseWork.dto.SuplierDto;
import org.project.courseWork.service.MaterialService;
import org.project.courseWork.service.SuplierService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supliers")
@AllArgsConstructor
public class SuplierController {
    private final SuplierService suplierService;
    private final MaterialService materialService;

    @GetMapping("/{id}")
    public ResponseEntity<SuplierDto> getSuplierById( @PathVariable Long id){
        return ResponseEntity.ok(suplierService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SuplierDto>> getAllSupliers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String[] sortBy,
        @RequestParam(defaultValue = "asc") String sortDir) {

    Page<SuplierDto> allSupliers = suplierService.getAllSupliersWithPagination(page, size, sortBy, sortDir);
    return ResponseEntity.ok(allSupliers);
}

    @PostMapping
    public ResponseEntity<SuplierDto> createSuplier(@Valid @RequestBody SuplierCreationDto suplierCreationDto){
        return new ResponseEntity(suplierService.create(suplierCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuplierDto> updateSuplier(@PathVariable Long id, @RequestBody SuplierDto updatedSuplier) {
        SuplierDto updated = suplierService.updateSuplier(id, updatedSuplier);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSuplier(@PathVariable Long id){
        suplierService.deleteSuplier(id);
        return ResponseEntity.ok("Suplier deleted successfully.");
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<SuplierDto>> filterSupliers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String[] sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<SuplierDto> filteredSupliers = suplierService.getFilteredSupliers(name, phoneNumber, email, page, size, sortBy, sortDir);
        return ResponseEntity.ok(filteredSupliers);
    }

    @GetMapping("/{id}/materials")
    //@GetMapping("/supliers/{id}")
    public ResponseEntity<List<MaterialDto>> getMaterialsBySuplierId(@PathVariable("id") Long suplierId) {
        List<MaterialDto> materials = materialService.getBySuplierId(suplierId);
        return ResponseEntity.ok(materials);
    }


}
