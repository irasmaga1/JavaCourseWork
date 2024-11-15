package org.project.courseWork.service;

import lombok.AllArgsConstructor;
import org.project.courseWork.mapper.MaterialMapper;
import org.project.courseWork.repository.MatherialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MaterialService {
    private final MatherialRepository matherialRepository;
    private final MaterialMapper materialMapper;
    
}
