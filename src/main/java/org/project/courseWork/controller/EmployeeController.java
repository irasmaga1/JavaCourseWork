package org.project.courseWork.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.project.courseWork.dto.EmployeeCreationDto;
import org.project.courseWork.dto.EmployeeDto;
import org.project.courseWork.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;


import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor

public class EmployeeController {
   private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDto>> getAllEmployeesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<EmployeeDto> employees = employeeService.getAllEmployeesWithPagination(page, size, sortBy, sortDir);
        return ResponseEntity.ok(employees);
    }
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployees(@Valid @RequestBody EmployeeCreationDto employeeCreationDto){
        return new ResponseEntity(employeeService.createEmployee(employeeCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto updatedEmployee){
        EmployeeDto updated = employeeService.updateEmployee(id, updatedEmployee);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeDto>> getFilteredEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String contactNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<EmployeeDto> employees = employeeService.getFilteredEmployees(name, position, contactNumber, page, size, sortBy, sortDir);
        return ResponseEntity.ok(employees);
    }


}
