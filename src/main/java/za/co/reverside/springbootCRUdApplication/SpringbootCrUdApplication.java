package za.co.reverside.springbootCRUdApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import za.co.reverside.springbootCRUdApplication.controller.EmployeeController;
@SpringBootApplication
@EnableJpaAuditing
public class SpringbootCrUdApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrUdApplication.class, args);
	}
}
