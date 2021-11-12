package com.nikhil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	
}
