package org.project.courseWork.repository;

import org.project.courseWork.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByContactNumber(String contactNumber);

    @Query("SELECT e FROM Employee e " +
            "WHERE (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:position IS NULL OR LOWER(e.position) LIKE LOWER(CONCAT('%', :position, '%'))) " +
            "AND (:contactNumber IS NULL OR e.contactNumber = :contactNumber)")
    Page<Employee> findFiltered(
            @Param("name") String name,
            @Param("position") String position,
            @Param("contactNumber") String contactNumber,
            Pageable pageable);
}
