package com.employee.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.employee.model.Employee;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	List<Employee> findByNameOrderByIdDesc(String name);// DSL it will automatically order the list by id desc
	
//	@Query("from Employee v where v.name = :name")// we can use query also insted of DSL
//	List<Employee> find(@Param("name") String name);
	
	@Query(value = "SELECT * FROM VKCORP v where v.name = :name ", nativeQuery = true)
	List<Employee> find(@Param("name") String name);
	
	@Modifying
	@Transactional
	@Query(value = "TRUNCATE TABLE VKCORP", nativeQuery = true)
	void deleteAll();
	
	
}
