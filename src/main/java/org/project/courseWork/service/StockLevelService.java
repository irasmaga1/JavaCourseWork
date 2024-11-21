package org.project.courseWork.service;

import lombok.AllArgsConstructor;
import org.project.courseWork.dto.StockLevelCreationDto;
import org.project.courseWork.dto.StockLevelDto;
import org.project.courseWork.entity.StockLevel;
import org.project.courseWork.exception.StockLevelAlreadyExists;
import org.project.courseWork.exception.StockLevelNotFound;
import org.project.courseWork.mapper.StockLevelMapper;
import org.project.courseWork.repository.StockLevelRepository;
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
public class StockLevelService {
    private final StockLevelRepository stockLevelRepository;
    private final StockLevelMapper stockLevelMapper;

    @Cacheable(value = "stockLevels", key = "#id")
    public StockLevelDto getById(Long id){
        StockLevel stockLevel = stockLevelRepository.findById(id)
                .orElseThrow(()->{
                    return new StockLevelNotFound("Stock level with ID " + id + " not found");
                });
        return stockLevelMapper.toDto(stockLevel);
    }

    @Cacheable(value = "stockLevels", key = "'all'")
    public List<StockLevelDto> getAll(){
        List<StockLevel> stockLevels = stockLevelRepository.findAll();
        return stockLevels.stream()
                .map(stockLevelMapper::toDto)
                .toList();
    }


    @Transactional
    @CachePut(value = "stockLevels", key = "#result.id")
    public StockLevelDto create(StockLevelCreationDto stockLevelCreationDto){
        if (stockLevelRepository.existsByCurrentCount(stockLevelCreationDto.currentCount())){
            throw new StockLevelAlreadyExists("Stock level with current count "+stockLevelCreationDto.currentCount()+" already exists");
        }
        return stockLevelMapper.toDto(stockLevelRepository.save(stockLevelMapper.toEntity(stockLevelCreationDto)));
    }


    @Transactional
    @CachePut(value = "stockLevels", key = "#id")
    public StockLevelDto updateStockLevel(Long id, StockLevelDto updatedStockLevel){
     StockLevel stockLevel= stockLevelRepository.findById(id)
             .orElseThrow(() -> new StockLevelNotFound("Stock level with ID " + id + " not found"));
     stockLevel.setCurrentCount(updatedStockLevel.currentCount());
     stockLevel.setMinimalQuantity((updatedStockLevel.minimalQuantity()));

     StockLevel updatedStockLevelEntity = stockLevelRepository.save(stockLevel);
     return stockLevelMapper.toDto(updatedStockLevelEntity);
    }

    @Transactional
    @CacheEvict(value = "stockLevels", key = "#id")
    public void deleteStockLevel(Long id){
        StockLevel stockLevel = stockLevelRepository.findById(id)
                .orElseThrow(()-> new StockLevelNotFound("Stock level with ID " + id + " not found"));
        stockLevelRepository.deleteById(id);
    }
    @Cacheable(value = "stockLevels", key = "'filtered:' + #currentCount + ':' + #minimalQuantity + ':' + #materialId + ':' + #page + ':' + #size + ':' + #sortDir")
    public Page<StockLevelDto> getFilteredStockLevels(
            Integer currentCount, Integer minimalQuantity, Long materialId,
            int page, int size, String[] sortBy, String sortDir) {

        Sort sort = Sort.by(
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.Order.desc(sortBy[0])
                        : Sort.Order.asc(sortBy[0])
        );
        for (int i = 1; i < sortBy.length; i++) {
            sort = sort.and("desc".equalsIgnoreCase(sortDir)
                    ? Sort.by(sortBy[i]).descending()
                    : Sort.by(sortBy[i]).ascending());
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StockLevel> stockLevelPage = stockLevelRepository.findFiltered(currentCount, minimalQuantity, materialId, pageable);
        return stockLevelPage.map(stockLevelMapper::toDto);
    }

    @Cacheable(value = "stockLevels", key = "'all_paginated:' + #page + ':' + #size + ':' + #sortDir")
    public Page<StockLevelDto> getAllStockLevelsWithPagination(int page, int size, String[] sortBy, String sortDir) {
        Sort sort = Sort.by(
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.Order.desc(sortBy[0])
                        : Sort.Order.asc(sortBy[0])
        );
        for (int i = 1; i < sortBy.length; i++) {
            sort = sort.and("desc".equalsIgnoreCase(sortDir)
                    ? Sort.by(sortBy[i]).descending()
                    : Sort.by(sortBy[i]).ascending());
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StockLevel> stockLevelPage = stockLevelRepository.findAll(pageable);
        return stockLevelPage.map(stockLevelMapper::toDto);
    }
}
