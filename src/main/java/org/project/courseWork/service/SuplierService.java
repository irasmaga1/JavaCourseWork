package org.project.courseWork.service;

import lombok.AllArgsConstructor;
import org.project.courseWork.dto.SuplierCreationDto;
import org.project.courseWork.dto.SuplierDto;
import org.project.courseWork.entity.Suplier;
import org.project.courseWork.exception.StockLevelAlreadyExists;
import org.project.courseWork.exception.SuplierNotFound;
import org.project.courseWork.mapper.SuplierMapper;
import org.project.courseWork.repository.SuplierRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class SuplierService {
    public final SuplierRepository suplierRepository;
    public final SuplierMapper suplierMapper;

    @Cacheable(value = "supliers", key = "#id")
    public SuplierDto getById(Long id) {
        Suplier suplier = suplierRepository.findById(id)
                .orElseThrow(() -> {
                    return new SuplierNotFound("Suplier with ID " + id + " not found");
                });
        return suplierMapper.toDto(suplier);
    }

    @Cacheable(value = "supliers", key = "'all")
    public List<SuplierDto> getAll() {
        List<Suplier> supliers = suplierRepository.findAll();
        return supliers.stream()
                .map(suplierMapper::toDto)
                .toList();
    }

    @Transactional
    @CachePut(value = "supliers", key = "#result.id")
    public SuplierDto create(SuplierCreationDto suplierCreationDto) {
        if (suplierRepository.existsByPhoneNumber(suplierCreationDto.phoneNumber())) {
            throw new StockLevelAlreadyExists("Suplier with phone number " + suplierCreationDto.phoneNumber() + " already exists");
        }
        return suplierMapper.toDto(suplierRepository.save(suplierMapper.toEntity(suplierCreationDto)));
    }

    @Transactional
    @CachePut(value = "supliers", key = "#id")
    public SuplierDto updateSuplier(Long id, SuplierDto updatedSuplier) {
        Suplier suplier = suplierRepository.findById(id)
                .orElseThrow(() -> new SuplierNotFound("Suplier with ID " + id + " not found"));
        suplier.setName(updatedSuplier.name());
        suplier.setPhoneNumber(updatedSuplier.phoneNumber());
        suplier.setEmail(updatedSuplier.email());
        Suplier updatedSuplierEntity = suplierRepository.save(suplier);
        return suplierMapper.toDto(updatedSuplierEntity);
    }

    @Transactional
    @CacheEvict(value = "supliers", key = "#id")
   public void deleteSuplier(Long id){
        Suplier suplier = suplierRepository.findById(id)
                .orElseThrow(() -> new SuplierNotFound("Suplier with ID " + id + " not found"));
        suplierRepository.deleteById(id);
    }

    @Cacheable(value = "supliers", key = "'filtered:' + #name + ':' + #phoneNumber + ':' + #email + ':' + #page + ':' + #size + ':' + #sortDir")
    public Page<SuplierDto> getFilteredSupliers(
            String name, String phoneNumber, String email,
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
        Page<Suplier> suplierPage = suplierRepository.findFiltered(name, phoneNumber, email, pageable);

        return suplierPage.map(suplierMapper::toDto);
    }

    @Cacheable(value = "supliers", key = "'all_paginated:' + #page + ':' + #size + ':' + #sortDir")
    public Page<SuplierDto> getAllSupliersWithPagination(int page, int size, String[] sortBy, String sortDir) {
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
        Page<Suplier> suplierPage = suplierRepository.findAll(pageable);

        return suplierPage.map(suplierMapper::toDto);
    }

    @CacheEvict(value = "supliers", allEntries = true)
    public void clearCache() {
    }
}
