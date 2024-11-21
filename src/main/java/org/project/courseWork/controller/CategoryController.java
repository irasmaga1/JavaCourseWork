package org.project.courseWork.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.project.courseWork.dto.CategoryCreationDto;
import org.project.courseWork.dto.CategoryDto;
import org.project.courseWork.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getPaginatedCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name) {

        Page<CategoryDto> categories = categoryService.getPaginatedCategories(page, size, sortBy, sortDir, name);
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategories(@Valid @RequestBody CategoryCreationDto categoryCreationDto){
        return new ResponseEntity(categoryService.createCategory(categoryCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto updatedCategory){
        CategoryDto updated = categoryService.updateCategory(id, updatedCategory);
        return  ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @GetMapping("/filtered")
    public ResponseEntity<Page<CategoryDto>> getFilteredCategories(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<CategoryDto> categories = categoryService.getFilteredCategories(name, description, page, size, sortBy, sortDir);
        return ResponseEntity.ok(categories);
    }

}
