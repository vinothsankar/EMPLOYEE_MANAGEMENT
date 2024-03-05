package com.employee.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	@Override
	public void save(Employee employee) {
		// TODO Auto-generated method stub
		if(Objects.nonNull(employee)){
			employeeRepository.save(employee);
		}		
	}

	@Override
	public Employee getById(Long id) {
		 Optional<Employee> optionalEmployee = employeeRepository.findById(id);
    if (optionalEmployee.isPresent()) {
        return optionalEmployee.get(); // Safe because we checked for presence
    } else {
        throw new RuntimeException("Employee not found with the id:"+id);
    }
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		if(Objects.nonNull(id)){
		employeeRepository.deleteById(id);
		}
	}
	
	@Override
	public List<Employee> findByNameOrderByIdDesc(String name) {
	        return employeeRepository.findByNameOrderByIdDesc(name);
	    }

	@Override
	public List<Employee> find(String name) {
		// TODO Auto-generated method stub
		return employeeRepository.find(name);
	}

	@Override
	public void deleteAll() {
		employeeRepository.deleteAll();		
	}


}
