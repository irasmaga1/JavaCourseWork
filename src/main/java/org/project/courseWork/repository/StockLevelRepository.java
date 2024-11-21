package org.project.courseWork.repository;

import org.project.courseWork.entity.StockLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface StockLevelRepository extends JpaRepository<StockLevel, Long> {
    boolean existsByCurrentCount(int currentCount);

    @Query("SELECT sl FROM StockLevel sl " +
            "WHERE (:currentCount IS NULL OR sl.currentCount = :currentCount) " +
            "AND (:minimalQuantity IS NULL OR sl.minimalQuantity >= :minimalQuantity) " +
            "AND (:materialId IS NULL OR sl.matherial.id = :materialId)")
    Page<StockLevel> findFiltered(
            @Param("currentCount") Integer currentCount,
            @Param("minimalQuantity") Integer minimalQuantity,
            @Param("materialId") Long materialId,
            Pageable pageable);
}
