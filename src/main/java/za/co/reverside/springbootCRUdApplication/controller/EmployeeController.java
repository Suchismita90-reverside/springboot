package za.co.reverside.springbootCRUdApplication.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import za.co.reverside.springbootCRUdApplication.exception.ResourceNotFoundException;
import za.co.reverside.springbootCRUdApplication.model.Employee;
import za.co.reverside.springbootCRUdApplication.model.FileName;
import za.co.reverside.springbootCRUdApplication.repository.EmployeeRepository;
import za.co.reverside.springbootCRUdApplication.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	static int rowNumber = 0;
	static int countPipe = 1;
	static int totalDataRows = 0;
	
	
	//insert employees by file name
	@RequestMapping(value = "/fileemployees",method = RequestMethod.POST)
	public List<Employee> insertEmployeeByFile(@RequestBody FileName fileName) throws IOException{
		
		String requestfileName = fileName.getFileName();
		
		File file = new File("E://SUCHI/employee.txt");		
		String localFileName = file.getName();
		
		System.out.println(localFileName);
		List<Employee> employees = null;
		
		if(localFileName.equalsIgnoreCase(requestfileName)){
			employees = getEmployeDetails(file);
			for(Employee employee : employees){
				employeeService.createEmployee(employee);
			}
			
		}else{
			System.out.println("File name doesn't match with request body");
		}
		return employees;
	}
	
	private List<Employee> getEmployeDetails(File file) {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String str;
			boolean firstRow=true;
			while((str = reader.readLine()) !=null && StringUtils.isNotEmpty(str)){
				rowNumber++;
				if(firstRow==true){
					firstRow=false;
					continue;
				}
				if(countPipe == (StringUtils.countMatches(str, "|"))){
					System.out.println("This is last line :"+str);
					String[] inputArray = str.split("\\|", -1);
					if(StringUtils.isEmpty(inputArray[1])){
						throw new Exception("Total number of rows in footer is null or empty");
					}
					totalDataRows = Integer.parseInt(inputArray[1]);
					continue;
						
				}
				String[] inputArray = str.split("\\|", -1);	
				if(inputArray.length < 4){
					throw new Exception("The required number of columns in row number :" +rowNumber + " is less than 4 " );
				}
				Employee employee = new Employee();
				if(StringUtils.isEmpty(inputArray[0])){
					throw new Exception("The value of 'Title' in 1st column of row number :" +rowNumber + " is null or empty.");
				}else{
					employee.setTitle(inputArray[0].trim());
				}
				if(StringUtils.isEmpty(inputArray[1])){
					throw new Exception("The value of 'Name' in 2nd column of row number :" +rowNumber + " is null or empty.");
				}else{
					employee.setName((inputArray[1].trim()));
				}
				if(StringUtils.isEmpty(inputArray[2])){
					throw new Exception("The value of 'Age' in 3rd column of row number :" +rowNumber + " is null or empty.");
				}else{
					employee.setAge(Integer.parseInt(inputArray[2].trim()));
				}
				if(StringUtils.isEmpty(inputArray[3])){
					throw new Exception("The value of 'Address' in 4th column of row number :" +rowNumber + " is null or empty.");
				}else{
					employee.setAddress(inputArray[3].trim());
				}
				employees.add(employee);					
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return employees;
	}
		
	

	//create a new employee	
	@RequestMapping(value = "/employees",method = RequestMethod.POST,produces = "application/json")
	public List<Employee> createEmployee(@Valid @RequestBody List<Employee> employees) throws Exception {
		
		List<Employee> employees3 = new ArrayList<Employee>();
		for(Employee employee : employees){
			int age= employee.getAge();
			if(age<18){
				throw new Exception("Employee's age less than 18");
			}
		Employee employees2 = employeeService.createEmployee(employee);
		employees3.add(employees2);		
		}
	    return employees3 ;
		
	}
	
	//Get Employee by id
	@RequestMapping(value = "/employee/{id}",method = RequestMethod.GET,produces = "application/json")
	public Optional<Employee> getEmplyoeeById(@PathVariable("id") long id){
		System.out.println("I am in Controller class");
		Optional<Employee> employee = employeeService.getEmplyoeeById(id);
		return employee;		
	}
	
	//Get all employees
	@RequestMapping(value = "/allEmployees",method = RequestMethod.GET,produces = "application/json")
	List<Employee> getEmplyoees(){
		List<Employee> employees = employeeService.getAllEmployee();
		return employees;		
	}
	
	//Delete employee by id
	@RequestMapping(value = "/deleteEmployee/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEmplyoeeById(@PathVariable("id") long id){
		Employee employee = employeeService.getEmplyoeeById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
		employeeService.deleteEmployeeById(id);
		
		return ResponseEntity.ok().build();
					
		}
	
	//Update employee by id
	@RequestMapping(value = "/employees",method = RequestMethod.PUT)
	public List<Employee> updateEmployeeById( @RequestBody List<Employee> employees){		
		List<Employee> employees2 = new ArrayList<Employee>();
		for(Employee employee2 : employees){			
			Optional<Employee> employee1 = employeeService.getEmplyoeeById(employee2.getId());
			employee1.get().setName(employee2.getName());
			employee1.get().setAddress(employee2.getAddress());
			employee1.get().setAge(employee2.getAge());
			employeeService.updateEmployeeById(employee2);
			employees2.add(employee2);
			
		}
		
		return employees2;
		
	}
}
	
		
		
		
	
	
	
	
	
	

	
	

