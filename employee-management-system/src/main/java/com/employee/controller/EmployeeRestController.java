package com.employee.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;

@RestController
public class EmployeeRestController {

	@Autowired
	private EmployeeService employeeService;
	
    @Autowired
    private EmployeeRepository employeeRepository;

	@GetMapping(path="/api", produces= {"application/xml"})
	public List<Employee> getEmployee() {
		return employeeService.getAllEmployee();
	}

//	@GetMapping("/api/{id}")
//	public Employee getEmployeeById(@PathVariable long id) {
//		if (employeeService.getById(id) != null) {
//			return employeeService.getById(id);
//		} else {
//
//			return null;
//		}
//	}
	@GetMapping("/api/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		if (employeeService.getById(id) != null) {
			Employee employee = employeeService.getById(id);
			return ResponseEntity.ok().body(employee);
		} else {
			
			return ResponseEntity.ofNullable(null);
		}
	}

	@GetMapping("/api/name/{name}")
	public ResponseEntity<?> getEmployeeByName(@PathVariable String name) {
//        List<Employee> employees = employeeService.findByNameOrderByIdDesc(name);
        List<Employee> employees = employeeService.find(name);
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employee found with name: " + name);
        } else if (employees.size() == 1) {
            return ResponseEntity.ok().body(employees.get(0));
        } else {
            // Handle multiple employees with the same name (you can return a list or choose one)
            return ResponseEntity.ok().body(employees);
        }
    }
	
	@PostMapping(path = "/api/add", consumes = {"application/xml"}, produces= {"application/xml"})
	public List<Employee> createNewEmployee(@RequestBody Employee employee){
		employeeService.save(employee);
		return employeeService.getAllEmployee()
				.stream()
				.sorted(Comparator.comparing(Employee::getId))
				.collect(Collectors.toList());
	}
	
	@PutMapping("/api/update/{id}")
	public Employee updateEmployee(@PathVariable long id, @RequestBody Employee updateEmployee) {
		if(employeeService.getById(id) != null) {		
			Employee existEmployee = employeeService.getById(id);
			existEmployee.setName(updateEmployee.getName());
			existEmployee.setAddress(updateEmployee.getAddress());
			existEmployee.setDesignation(updateEmployee.getDesignation());
			existEmployee.setPhone(updateEmployee.getPhone());
			employeeService.save(existEmployee);
			return existEmployee;
		}else
			return null;
	}
	
	@DeleteMapping("/api/delete/{id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable long id){
		if (employeeService.getById(id) != null) {
			employeeService.deleteById(id);
			return ResponseEntity.ok().body("Deleted");
		} else {
			
			return ResponseEntity.ofNullable("Not found");
		}
		
	}
	
}
