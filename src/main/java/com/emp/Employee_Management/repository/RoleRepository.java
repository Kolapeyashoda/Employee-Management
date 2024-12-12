package com.emp.Employee_Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.Employee_Management.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}