package org.project.courseWork.service;

import lombok.AllArgsConstructor;
import org.project.courseWork.dto.EmployeeCreationDto;
import org.project.courseWork.dto.EmployeeDto;
import org.project.courseWork.entity.Employee;
import org.project.courseWork.exception.EmployeeAlreadyExistsException;
import org.project.courseWork.exception.EmployeeNotFound;
import org.project.courseWork.mapper.EmployeeMapper;
import org.project.courseWork.repository.EmployeeRepository;
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
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    @Cacheable(value = "employees", key = "#id")
    public EmployeeDto getById(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->{
                    return new EmployeeNotFound("Employee with ID " + id + " not found");
                });
        return employeeMapper.toDto(employee);
    }

//    @Cacheable(value = "employees", key = "'all'")
//    public List<EmployeeDto> getAll() {
//        List<Employee> employees = employeeRepository.findAll();
//        return employees.stream()
//                .map(employeeMapper::toDto)
//                .toList();
//    }

    @Transactional
    @CachePut(value = "employees", key = "#result.id")
    public EmployeeDto createEmployee(EmployeeCreationDto employeeCreationDto){
        if (employeeRepository.existsByContactNumber(employeeCreationDto.contactNumber())){
            throw new EmployeeAlreadyExistsException("Employee with phone number "+employeeCreationDto.contactNumber()+" already exists");
        }
        return employeeMapper.toDto(employeeRepository.save(employeeMapper.toEntity(employeeCreationDto)));
    }

    @Transactional
    @CachePut(value = "employees", key = "#id")
    public EmployeeDto updateEmployee(Long id, EmployeeDto updatedEmployee){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->
                    new EmployeeNotFound("Employee with ID " + id + " not found"));
        employee.setName(updatedEmployee.name());
        employee.setPosition(updatedEmployee.position());
        employee.setContactNumber(updatedEmployee.contactNumber());
        employee.setEmail(updatedEmployee.email());
        Employee updatedEmployeeEntity = employeeRepository.save(employee);
        return employeeMapper.toDto(updatedEmployeeEntity);
    }

    @Transactional
    @CacheEvict(value = "employees", key = "#id")
    public void deleteEmployee(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->
                        new EmployeeNotFound("Employee with ID " + id + " not found"));
        employeeRepository.deleteById(id);
    }

    @Cacheable(value = "employees", key = "'filtered:' + #name + ':' + #position + ':' + #contactNumber + ':' + #page + ':' + #size + ':' + #sortBy + ':' + #sortDir")
    public Page<EmployeeDto> getFilteredEmployees(
            String name, String position, String contactNumber,
            int page, int size, String sortBy, String sortDir) {

        Sort sort = "desc".equalsIgnoreCase(sortDir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage = employeeRepository.findFiltered(name, position, contactNumber, pageable);
        return employeePage.map(employeeMapper::toDto);
    }

    @Cacheable(value = "employees", key = "'paginated:' + #page + ':' + #size + ':' + #sortBy + ':' + #sortDir")
    public Page<EmployeeDto> getAllEmployeesWithPagination(int page, int size, String sortBy, String sortDir) {
        Sort sort = "desc".equalsIgnoreCase(sortDir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        return employeePage.map(employeeMapper::toDto);
    }

}
