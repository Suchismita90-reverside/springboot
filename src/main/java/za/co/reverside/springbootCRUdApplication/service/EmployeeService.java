package za.co.reverside.springbootCRUdApplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import za.co.reverside.springbootCRUdApplication.model.Employee;
import za.co.reverside.springbootCRUdApplication.repository.EmployeeRepository;
@Repository
public class EmployeeService {
	@Autowired
	EmployeeRepository emRepository;
	
	//create Employee
	public Employee createEmployee(Employee employee){
	return emRepository.save(employee);
	}
	//get Emplyoee by id
	public Optional<Employee> getEmplyoeeById(long id){
		System.out.println("I am in service class");
		Optional<Employee> employee = emRepository.findById(id);
		return employee;		
	}
	//get all emplyoees
	public List<Employee> getAllEmployee(){
		List<Employee> employees = emRepository.findAll();		
		return employees;
		
	}
	//delete employee by id
	public void deleteEmployeeById(long id){
		 emRepository.deleteById(id);		
		
	}
	//update emplyoee by id	
	public Employee updateEmployeeById(Employee employee) {
		Employee employee1 = emRepository.save(employee);
		return employee1;
		
		
	}

}
