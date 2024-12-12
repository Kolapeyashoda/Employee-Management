package com.emp.Employee_Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.Employee_Management.entity.Employee;
import com.emp.Employee_Management.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee,
                                                @RequestHeader("Authorization") String accessToken) {
        // Validate the access token (Custom logic for token validation)
        if (!isValidToken(accessToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Token Expired or Invalid");
        }

        // Ensure valid department and role are provided
        if (employee.getDepartment() == null || employee.getRole() == null) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department or Role is missing");
        }

        employeeService.createEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body("Employee Added");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeDetails(@PathVariable Long id, 
                                                     @RequestHeader("Authorization") String accessToken) {
        // Validate the access token
        if (!isValidToken(accessToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Token Expired or Invalid");
        }

        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee Not Found");
        }
    }

    private boolean isValidToken(String token) {
        // Implement token validation logic here (e.g., decode JWT and check expiration)
        return true; // Placeholder, implement actual token validation
    }
}
