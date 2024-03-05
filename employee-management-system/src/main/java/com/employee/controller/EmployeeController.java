package com.employee.controller;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("allemplist", employeeService.getAllEmployee()
        		.stream()
        		.sorted(Comparator.comparing(Employee::getId))
        		.collect(Collectors.toList()));
//    	model.addAttribute("allemplist",employeeService.getAllEmployee());
        try {
			XMLEncoder x = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Student.xml")));
			x.writeObject(employeeService.getAllEmployee());
			x.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        return "index";
    }
    
    @GetMapping("/add")
    public String addNewEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "addEmployee"; 
    }
    
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/";
    }
    
    @RequestMapping("/update/{id}")
    public String updateEmployee(@PathVariable long id, @ModelAttribute("employee") Employee updateEmpoloyee) {
    	Employee existEmployee = employeeService.getById(id);
    	existEmployee.setName(updateEmpoloyee.getName());
    	existEmployee.setDesignation(updateEmpoloyee.getDesignation());
    	existEmployee.setAddress(updateEmpoloyee.getAddress());
    	existEmployee.setPhone(updateEmpoloyee.getPhone());
    	employeeService.save(existEmployee);
    	return "redirect:/";
    }
    
    @GetMapping("/updateform/{id}")
    public String updateForm(@PathVariable(value="id") long id, Model model) {
        Employee employee = employeeService.getById(id);
        model.addAttribute("employee", employee);
//        return "updateEmployee";
        return "update";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable(value="id") long id) {
        employeeService.deleteById(id);
        return "redirect:/";
    }
    
    @RequestMapping("/delete")
    public String deleteRecord() {
    	employeeService.deleteAll();
    	return "redirect:/";
    }
}