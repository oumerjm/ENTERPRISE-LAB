package com.craft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		// Load the Spring container using the XML file from the classpath
		ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");

		// Ask the container for the "student" bean
		Student student = context.getBean("student", Student.class);
		

		student.getDetails();
	}

}
