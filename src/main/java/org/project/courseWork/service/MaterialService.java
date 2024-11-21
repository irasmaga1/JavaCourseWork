package org.project.courseWork.service;

import lombok.AllArgsConstructor;
import org.project.courseWork.dto.MaterialCreationDto;
import org.project.courseWork.dto.MaterialDto;
import org.project.courseWork.entity.Material;
import org.project.courseWork.exception.MaterialNotFound;
import org.project.courseWork.mapper.MaterialMapper;
import org.project.courseWork.repository.MaterialRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.project.courseWork.exception.MaterialAlreadyExistsException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;

    @Cacheable(value = "materials", key = "#id")

    public MaterialDto getById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> {
                    return new MaterialNotFound("Material with ID " + id + " not found");
                });
        return materialMapper.toDto(material);
    }

    @Cacheable(value = "materials", key = "'all'")
    public List<MaterialDto> getAll(){
        List<Material> materials = materialRepository.findAll();
        return materials.stream()
                .map(materialMapper::toDto)
                .toList();
    }

    @Transactional
    @CachePut(value = "materials", key = "#result.id")
    public MaterialDto create(MaterialCreationDto materialCreationDto){
        if(materialRepository.existsByName(materialCreationDto.name())){
            throw new MaterialAlreadyExistsException("Material with name "+materialCreationDto.name()+" already exists");
        }
        return  materialMapper.toDto(materialRepository.save(materialMapper.toEntity(materialCreationDto)));
    }

    @Transactional
    @CachePut(value = "materials", key = "#id")
    public MaterialDto updateMaterial(Long id, MaterialDto updatedMaterial){
        Material material = materialRepository.findById(id)
                .orElseThrow(()-> new MaterialNotFound("Material with ID " + id + " not found"));
        material.setName(updatedMaterial.name());
        material.setCount(updatedMaterial.count());
        material.setPrice(updatedMaterial.price());
        material.setBrand(updatedMaterial.brand());
        Material updatedMaterialEntity = materialRepository.save(material);
        return materialMapper.toDto(updatedMaterialEntity);
    }

    @Transactional
    @CacheEvict(value = "materials", key = "#id")
    public void deleteMaterial(Long id){
        Material material = materialRepository.findById(id)
                .orElseThrow(()-> new MaterialNotFound("Material with ID " + id + " not found"));
        materialRepository.deleteById(id);
    }

    public Page<MaterialDto> getMaterials(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Material> materialsPage = materialRepository.findAll(pageable);
        return materialsPage.map(materialMapper::toDto);
    }

    public Page<MaterialDto> getMaterialsByBrand(String brand, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Material> materialsPage = materialRepository.findByBrand(brand, pageable);
        return materialsPage.map(materialMapper::toDto);
    }

    @CacheEvict(value = "materials", allEntries = true)
    public void clearCache() {
    }

    public List<MaterialDto> getBySuplierId(Long suplierId) {
        List<Material> materials = materialRepository.findBySuplierId(suplierId);
        return materials.stream()
                .map(materialMapper::toDto)
                .toList();
    }

    public List<MaterialDto> getByCategoryId(Long categoryId) {
        List<Material> materials = materialRepository.findByCategoryId(categoryId);
        return materials.stream()
                .map(materialMapper::toDto)
                .toList();
    }


}
