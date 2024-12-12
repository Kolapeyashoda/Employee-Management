package com.emp.Employee_Management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.Employee_Management.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);
}
