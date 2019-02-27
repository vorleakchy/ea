package edu.mum.main;


import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.mum.dao.impl.UserDaoImpl;
import edu.mum.domain.User;
import edu.mum.service.UserService;
import edu.mum.service.impl.UserServiceImpl;

public class Main {
  public static void main(String[] args) {
	  
	  ApplicationContext context = new ClassPathXmlApplicationContext("context/applicationContext.xml");
	  
	  UserService userService = (UserService) context.getBean("userServiceImpl");
	  
	  User user = new User();
	  user.setFirstName("John");
	  user.setLastName("Doe");
	  user.setEmail("john.doe@mum.edu");
	  userService.save(user);
	  
	  User userOutput = userService.findByEmail(user.getEmail());
	  System.out.println("********** User **********");
	  System.out.println(String.format("User Name: %s", userOutput.getFullName()));
	  
	  user.setEmail("updated1@mum.edu");
	  User updatedUser = userService.update(user);
	  user.setEmail("updated2@mum.edu");
	  userService.update(user);
	  //userService.update(updatedUser);
  }  
  
 }