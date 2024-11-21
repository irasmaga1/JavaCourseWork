package org.project.courseWork.repository;

import org.project.courseWork.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    public boolean existsByName(String name);

    Page<Material> findByBrand(String brand, Pageable pageable);

    @Query("SELECT m FROM Material m WHERE " +
            "(:name IS NULL OR m.name LIKE %:name%) AND " +
            "(:brand IS NULL OR m.brand LIKE %:brand%) AND " +
            "(:category IS NULL OR m.category.name LIKE %:category%)")
    Page<Material> findByNameBrandCategory(String name, String brand, String category, Pageable pageable);

    List<Material> findBySuplierId(Long suplierId);
    List<Material> findByCategoryId(Long categoryId);

}
