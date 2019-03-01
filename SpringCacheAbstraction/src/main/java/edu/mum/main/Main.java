package edu.mum.main;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.internal.EntityManagerFactoryImpl;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Component;

import edu.mum.domain.Address;
import edu.mum.domain.Member;
import edu.mum.service.AddressService;
import edu.mum.service.MemberService;


@Component
public class Main {
	
	  @Autowired
	  TestData testData;
  
	  @Autowired
	  AddressService addressService;

	  @Autowired
	  MemberService memberService;

	  @PersistenceContext
	  EntityManager entityManager;
	  
 	  

  public static void main(String[] args) {

	  
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "context/applicationContext.xml");
    applicationContext.getBean(Main.class).mainInternal(applicationContext);
  }
    private void mainInternal(ApplicationContext applicationContext) {
  	
 
  	 testData.setupData();


    // "regular" cache statistics aren't relevant to Spring cache BUT we can see the performance improvement...
    // We can do either findOne OR findAllJoinFetch with Spring - both Primary key  & query
 	
  	 // here are the queries
  	 		  long start = System.currentTimeMillis();
 			  List<Member> m = (List<Member>)memberService.findAllJoinFetch();   //this is a miss 
			  long elapsed = System.currentTimeMillis() - start;
			  System.out.println("FIRST FindAll Time: " + elapsed + " ms ");	  
			   
			  start = System.currentTimeMillis();
 			 m = (List<Member>)memberService.findAllJoinFetch();   //this is a HIT
			 elapsed = System.currentTimeMillis() - start;
			  System.out.println("SECOND FindAll Time: " + elapsed + " ms ");	  

			   
			  start = System.currentTimeMillis();
 			m = (List<Member>)memberService.findAllJoinFetch();   //this is a HIT
			 elapsed = System.currentTimeMillis() - start;
			  System.out.println("THIRD FindAll Time: " + elapsed + " ms ");	  
			  
			  start = System.currentTimeMillis();
 			m = (List<Member>)memberService.findAllJoinFetch();   //this is a HIT
			 elapsed = System.currentTimeMillis() - start;
			  System.out.println("FOURTH FindAll Time: " + elapsed + " ms ");	  
			  

			  
			  // Try a different Member if you like... 
			  	 // here are the find by Primary Key

  			  start = System.currentTimeMillis();
		   Member member = memberService.findOne(2L);   //this is a miss & a PUT
			 elapsed = System.currentTimeMillis() - start;
			  System.out.println("FIRST FindOne Time: " + elapsed + " ms ");	  

				  start = System.currentTimeMillis();
	   member = memberService.findOne(2L);   
			 elapsed = System.currentTimeMillis() - start;
			  System.out.println("SECOND FindOne Time: " + elapsed + " ms ");	  

			  start = System.currentTimeMillis();
		   member = memberService.findOne(2L);
			 elapsed = System.currentTimeMillis() - start;
			  System.out.println("THIRD FindOne Time: " + elapsed + " ms ");	  
 
		
 		    }
		    

  		   
     }
    
    

  
   



