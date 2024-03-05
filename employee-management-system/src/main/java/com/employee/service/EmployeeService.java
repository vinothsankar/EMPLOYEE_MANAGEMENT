package com.employee.service;

import java.util.List;

import com.employee.model.Employee;

public interface EmployeeService {
	
	List<Employee> getAllEmployee();

	List<Employee> findByNameOrderByIdDesc(String name);

	void save(Employee employee);

	Employee getById(Long id);

	void deleteById(Long id);

	List<Employee> find(String name);
	
	void deleteAll();
}
