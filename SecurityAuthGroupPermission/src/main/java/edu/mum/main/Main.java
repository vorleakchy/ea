package edu.mum.main;


import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.mum.domain.Address;
import edu.mum.domain.Authority;
import edu.mum.domain.Group;
import edu.mum.domain.Member;
import edu.mum.domain.UserCredentials;
import edu.mum.security.AuthenticateUser;
import edu.mum.service.MemberService;
import edu.mum.service.GroupService;

 
public class Main {
  public static void main(String[] args) {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("context/applicationContext.xml");

    AuthenticationManager authenticationManager = (AuthenticationManager) ctx.getBean("authenticationManager");

    MemberService memberService = (MemberService) ctx.getBean("memberServiceImpl");
    GroupService groupService = (GroupService) ctx.getBean("groupServiceImpl");
 
    // Create USER group
    Group group = new Group();
    group.setGroup_name("USER");

    // Create ADMIN group 
    Group groupAdmin = new Group();
    groupAdmin.setGroup_name("ADMIN");

 
    // Add Permissions
    // ADMIN Group has CRUD & LIST
    // USER Group has R[ead] & L[ist] 
    Authority authority = new Authority();
    authority.setAuthority("CREATE");
    groupAdmin.getAuthority().add(authority); 

    authority = new Authority();
    authority.setAuthority("UPDATE");
    groupAdmin.getAuthority().add(authority); 

    authority = new Authority();
    authority.setAuthority("DELETE");
    groupAdmin.getAuthority().add(authority); 

    authority = new Authority();
    authority.setAuthority("READ");
    groupAdmin.getAuthority().add(authority); 
    group.getAuthority().add(authority); 

    authority = new Authority();
    authority.setAuthority("LIST");
    groupAdmin.getAuthority().add(authority); 
    group.getAuthority().add(authority); 


    UserCredentials userCredentials = new UserCredentials();
    userCredentials.setUsername("Sean");
    userCredentials.setPassword("Sean");
    userCredentials.setEnabled(true);
 
    group.getUserCredentials().add(userCredentials);
 
  
    Member member = new Member();
    member.setFirstName("Sean");
    member.setLastName("Smith");
    member.setMemberNumber(1);

    member.setUserCredentials(userCredentials);
    
    
    Address address =  new Address();
    address.setCity("Batavia");
    address.setState("Iowa");
  
    Address  address2 =  new Address();
    address2.setCity("Red Rock");
    address2.setState("Iowa");
          
    member.addAddress(address);
    member.addAddress(address2);

    memberService.saveFull(member);

    
    // THIS IS BILL

    userCredentials = new UserCredentials();
    userCredentials.setUsername("Bill");
    userCredentials.setPassword("Bill");
    userCredentials.setEnabled(true);
 
    groupAdmin.getUserCredentials().add(userCredentials);
    
     member = new Member();
    member.setFirstName("Bill");
    member.setLastName("Due");
    member.setMemberNumber(3);

    member.setUserCredentials(userCredentials);
    
    Address address3 =  new Address();
    address3.setCity("Washington");
    address3.setState("Iowa");
 
    Address address4 =  new Address();
    address4.setCity("Mexico");
    address4.setState("Iowa");

     member.addAddress(address3);
    member.addAddress(address4);
     
     memberService.saveFull(member);
  
     // THIS is PETE
 
     userCredentials = new UserCredentials();
    userCredentials.setUsername("Pete");
    userCredentials.setPassword("Pete");
    userCredentials.setEnabled(true);
 
    group.getUserCredentials().add(userCredentials);
    
    
     member = new Member();
    member.setFirstName("Pete");
    member.setLastName("Moss");
    member.setMemberNumber(4);

    member.setUserCredentials(userCredentials);
    
    memberService.saveFull(member);
     
    groupService.save(groupAdmin);
    groupService.update(group);
     
     try {
     List<Member> members = memberService.findAll();
     }
     catch (AuthenticationCredentialsNotFoundException e) {
    	 System.out.println( );
    	 System.out.println( " ******** ANONYMOUS USER Attempted to access a secure resource *********"  );
       	 System.out.println( );
     }
 
 while (true)  {    
     AuthenticateUser authenticateUser = new AuthenticateUser();
     try {
  		authenticateUser.authenticate(authenticationManager);
  	} catch (Exception e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
     
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
authentication.getAuthorities();
     
  // ANYBODY but Bill will get access Denied - AS Bill is An ADMIN & 
  // Update requires Admin
      try {
    	 List<Member> members = memberService.findAll();
		   System.out.println( );
		   System.out.println("LIST access of Members was SUCCESSFUL!!!" );
   	   
    	 for (Member membere : members) {
    		   
		   System.out.println( );
    		   System.out.println("Member Name : " + membere.getFirstName() + "  " +  membere.getLastName() );
    		
    		   for (Address addresse : membere.getAddresses()) {
    		       System.out.println("Address : " + addresse.getCity() + 
    						"   " + addresse.getState());
    		   }
    	   }
    	 
    	 
    	 member = members.get(0);
    	 member.setAge(75);
    	 memberService.update(member);
		   System.out.println( );
		   System.out.println("Member UPDATE was SUCCESSFUL!!!" );
   	 
     }
     catch ( AccessDeniedException e) {
 		   System.out.println( );
 		   System.out.println("****** ACCESS DENIED ! You Need UPDATE to modify Member  **********");
		   System.out.println( );

     }
 
   }
  } 
  }