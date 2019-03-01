package edu.mum.main;


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

 // gather  statistics....
    // Cache statistics [Puts,Hits & Misses] are part of Session statistics...
    Statistics stats = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
		   stats.clear();
 		   
			  long elapsed;
			  long start;
		
	 	   // Query Caching...
		   // The Query actually Caches Member in the data cache so there will be NO Put during findOne below   -------
		  
		   // When commented  out will see different[More dramatic] statistics results in the series of findOne calls below

	   	System.out.println("      ************* Initial Query **********");
		start = System.currentTimeMillis();
	  	entityManager.createNamedQuery("Member.findById")
				    .setParameter("memberNumber", 1)
 				    .getSingleResult();

			  elapsed = System.currentTimeMillis() - start;
		  System.out.println("FIRST Query: " + elapsed + " ms ");	  

		  printStatistics();
			System.out.println("***********************");

 
		   // Try it again to see Query HIT
		  System.out.println("      ************* Second Query **********");

		  start = System.currentTimeMillis();
		  entityManager.createNamedQuery("Member.findById")
			    .setParameter("memberNumber", 1)
			    .getSingleResult();
		  elapsed = System.currentTimeMillis() - start;
	      System.out.println("Second Query: " + elapsed + " ms ");	  
			    
			    		  printStatistics();
		System.out.println("***********************");


		// These are the FIND by Primary Key --- Will NOT impact Query Cache...   
			  System.out.println("      ************* Find By Primary Key **********");
			  start = System.currentTimeMillis();
				 Member  member = memberService.findOne(1L);   //this is a miss & a PUT [ if no cached query from above]
 			  elapsed = System.currentTimeMillis() - start;
			  System.out.println("FIRST FindOne Time: " + elapsed + " ms ");	  
			   
			  start = System.currentTimeMillis();
			   member = memberService.findOne(1L);   //this is a HIT
 			 elapsed = System.currentTimeMillis() - start;
			  System.out.println("SECOND FindOne Time: " + elapsed + " ms ");	  

			   
			  start = System.currentTimeMillis();
			   member = memberService.findOne(1L);   //this is a HIT
 			 elapsed = System.currentTimeMillis() - start;
			  System.out.println("THIRD FindOne Time: " + elapsed + " ms ");	  
			  
			  start = System.currentTimeMillis();
			   member = memberService.findOne(1L);   //this is a HIT
 			 elapsed = System.currentTimeMillis() - start;
			  System.out.println("FOURTH FindOne Time: " + elapsed + " ms ");	  
			  
 
			  // Try a different Member if you like...
/*		   member = memberService.findOne(2L);   //this is a miss & a PUT
		   member = memberService.findOne(2L);   
		   member = memberService.findOne(2L);
		   member = memberService.findOne(2L);
*/		
		   
		System.out.println("      ********   After Finds ******");
		   printStatistics();
    }
    
    
    private void printStatistics() {
 
    	Statistics stats = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
 			
		   SecondLevelCacheStatistics statistics = stats.getSecondLevelCacheStatistics( "edu.mum.domain.Member" );

		   System.out.println("         ****** Member **********");

			   System.out.println("Cache Hits = " + statistics.getHitCount());
			   System.out.println("Cache Miss = " + statistics.getMissCount());
			   System.out.println("Cache Put = " + statistics.getPutCount());
			   System.out.println();

			   System.out.println("Query Cache Hit = " + stats.getQueryCacheHitCount());
			   System.out.println("Query Cache Miss = " + stats.getQueryCacheMissCount());
			   System.out.println("Query Cache Put = " + stats.getQueryCachePutCount());
			   
			   
    }
   }
  
   



