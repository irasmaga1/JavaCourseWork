package org.project.courseWork.service;

import lombok.AllArgsConstructor;
import org.project.courseWork.dto.CategoryCreationDto;
import org.project.courseWork.dto.CategoryDto;
import org.project.courseWork.entity.Category;
import org.project.courseWork.exception.CategoryAlreadyExistsException;
import org.project.courseWork.exception.CategoryNotFound;
import org.project.courseWork.mapper.CategoryMapper;
import org.project.courseWork.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Cacheable(value = "categories", key = "#id")
    public CategoryDto getById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->{
                    return new CategoryNotFound("Category with ID " + id + " not found");
                });
        return categoryMapper.toDto(category);
    }

    @Transactional
    @CachePut(value = "categories", key = "#result.id")
    public CategoryDto createCategory(CategoryCreationDto categoryCreationDto){
        if (categoryRepository.existsByName(categoryCreationDto.name())){
            throw new CategoryAlreadyExistsException("Category "+categoryCreationDto.name()+" already exists");
        }
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryCreationDto)));
    }

    @Transactional
    @CachePut(value = "categories", key = "#id")
    public CategoryDto updateCategory(Long id, CategoryDto updatedCategory){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFound("Category with ID "+id+"not found."));
        category.setName(updatedCategory.name());
        category.setDescription(updatedCategory.description());
        Category updatedCategoryEntity = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategoryEntity);
        }

    @Transactional
    @CacheEvict(value = "categories", key = "#id")
    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFound("Category with ID "+ id +"not found"));
        categoryRepository.deleteById(id);
    }

    @Cacheable(value = "categories", key = "'paginated:' + #page + ':' + #size + ':' + #sortBy + ':' + #sortDir + ':' + (#name ?: 'all')")
    public Page<CategoryDto> getPaginatedCategories(int page, int size, String sortBy, String sortDir, String name) {
        Sort sort = "desc".equalsIgnoreCase(sortDir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Category> categoryPage;
        if (name != null && !name.isEmpty()) {
            categoryPage = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            categoryPage = categoryRepository.findAll(pageable);
        }
        return categoryPage.map(categoryMapper::toDto);
    }
    @Cacheable(
            value = "categories", key = "'filtered:' + #name + ':' + #description + ':' + #page + ':' + #size + ':' + #sortBy + ':' + #sortDir")
    public Page<CategoryDto> getFilteredCategories(String name, String description, int page, int size, String sortBy, String sortDir) {
        Sort sort = "desc".equalsIgnoreCase(sortDir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categories = categoryRepository.findFiltered(name, description, pageable);
        return categories.map(categoryMapper::toDto);
    }

}
