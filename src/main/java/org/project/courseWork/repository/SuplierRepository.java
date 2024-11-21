package org.project.courseWork.repository;

import org.project.courseWork.entity.Suplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuplierRepository extends JpaRepository<Suplier, Long> {
    boolean existsByNameAllIgnoreCase(String name);

    @Query("SELECT s FROM Suplier s WHERE " +
            "(:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:phoneNumber IS NULL OR LOWER(s.phoneNumber) LIKE LOWER(CONCAT('%', :phoneNumber, '%'))) " +
            "AND (:email IS NULL OR LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%'))) ")
    Page<Suplier> findFiltered(
            @Param("name") String name,
            @Param("phoneNumber") String phoneNumber,
            @Param("email") String email,
            Pageable pageable);

    boolean existsByPhoneNumber(String phoneNumber);
}
