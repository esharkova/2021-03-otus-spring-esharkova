package ru.diasoft.StudentsTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.diasoft.StudentsTest.Service.TestingService;

import java.io.IOException;

@SpringBootApplication
public class StudentsTestApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(StudentsTestApplication.class, args);
	}

}
