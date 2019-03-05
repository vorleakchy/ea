package edu.mum.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import edu.mum.domain.User;

@Aspect
@Component
public class AuctionAspect {
//	 @Pointcut("execution(* edu.mum.service..*(..))")
//	 @Pointcut("execution(* edu.mum.service..*(Long))")
//	 @Pointcut("execution(* edu.mum.service..*())")
//	 @Pointcut("within(edu.mum.service..*) && args()")
	 @Pointcut("within(edu.mum.service..*)")
	 public void applicationMethod() {}
	 
//	 @Before("applicationMethod()") // Explicit Pointcut
//	 @Before("execution(* edu.mum.service..*(..))") // Implicit Pointcut
//	 public void printMethodName(JoinPoint joinPoint) {
//		 System.out.println( "=== Method Name : " + joinPoint.getSignature().toString());
//	 }
	 
	 @Pointcut(" args(user)  ")
	 public void userArgs(User user) {}
	 
	 @Before("applicationMethod() && userArgs(user)")
	 public void logResourceName(JoinPoint joinPoint, User user) {
		 System.out.println("=== User Full Name: " + user.getFirstName() + " " + user.getLastName());
	 }
}
