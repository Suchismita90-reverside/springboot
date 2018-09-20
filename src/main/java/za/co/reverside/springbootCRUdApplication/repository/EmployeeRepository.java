package za.co.reverside.springbootCRUdApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.reverside.springbootCRUdApplication.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	

}
