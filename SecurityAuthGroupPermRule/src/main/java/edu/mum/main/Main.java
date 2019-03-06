package edu.mum.main;


import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.mum.domain.Address;
import edu.mum.domain.Authority;
import edu.mum.domain.Comment;
import edu.mum.domain.Group;
import edu.mum.domain.Member;
import edu.mum.domain.UserCredentials;
import edu.mum.security.AuthenticateUser;
import edu.mum.security.rules.CommentPolicy;
import edu.mum.service.CommentService;
import edu.mum.service.CredentialsService;
import edu.mum.service.GroupService;
import edu.mum.service.MemberService;

 
public class Main {
	// "Configuration policy/rules list
	public static Map<String, Object> policyList = new HashMap<String, Object>();

  public static void main(String[] args) {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("context/applicationContext.xml");

    AuthenticationManager authenticationManager = (AuthenticationManager) ctx.getBean("authenticationManager");

    MemberService memberService = (MemberService) ctx.getBean("memberServiceImpl");
    GroupService groupService = (GroupService) ctx.getBean("groupServiceImpl");
    CommentService commentService = (CommentService) ctx.getBean("commentServiceImpl");
    CredentialsService userCredentialsService = (CredentialsService) ctx.getBean("credentialsServiceImpl");
    AuthenticateUser authenticateUser = (AuthenticateUser) ctx.getBean("authenticateUser");

    // "Configured list of policies/rules
    policyList.put("Comment",new CommentPolicy());
    
    Group group = new Group();
    group.setGroup_name("USER");

    // Create ADMIN  Group
    Group groupAdmin = new Group();
    groupAdmin.setGroup_name("ADMIN");

    // Add Permissions
    // ADMIN Group has CRUD & LIST
    // USER Group has R[ead] & L[ist] Update
    Authority authority = new Authority();
    authority.setAuthority("create");
    groupAdmin.getAuthority().add(authority); 

    authority = new Authority();
    authority.setAuthority("update");
    groupAdmin.getAuthority().add(authority); 
    group.getAuthority().add(authority); 

    authority = new Authority();
    authority.setAuthority("delete");
    groupAdmin.getAuthority().add(authority); 

    authority = new Authority();
    authority.setAuthority("read");
    groupAdmin.getAuthority().add(authority); 
    group.getAuthority().add(authority); 

    authority = new Authority();
    authority.setAuthority("list");
    groupAdmin.getAuthority().add(authority); 
    group.getAuthority().add(authority); 

    // If we wanted "special" delete privileges...
/*    authority = new Authority();
    authority.setAuthority("admin_delete");
    groupAdmin.getAuthority().add(authority); 
*/
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
  
    userCredentials = new UserCredentials();
    userCredentials.setUsername("Pete");
    userCredentials.setPassword("Pete");
    userCredentials.setEnabled(true);
 
    group.getUserCredentials().add(userCredentials);
    
    // This is Pete
    
    member = new Member();
    member.setFirstName("Pete");
    member.setLastName("Moss");
    member.setMemberNumber(4);

    member.setUserCredentials(userCredentials);
    
    memberService.saveFull(member);
     
    groupService.save(groupAdmin);
    groupService.update(group);
     
	System.out.println("Login in as Pete - to Create Comments"); //Could be Sean Or Bill BUT differing results
// AuthenticateUser authenticateUser = new AuthenticateUser();
 try {
		authenticateUser.authenticate(authenticationManager);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 
 String userName =  SecurityContextHolder.getContext().getAuthentication().getName();
 userCredentials = userCredentialsService.findByUserName(userName);
 member = userCredentials.getMember();
 

 Comment commentD = new Comment();
 commentD.setMember(member);
 commentD.setContent("This is REALLY Good");
 commentService.save(commentD);
 
 Comment comment = new Comment();
 comment.setMember(member);
 comment.setContent("This is REALLY Good Too");
 commentService.save(comment);
 
  comment.setContent("This is REALLY Good Too AND EVEN BETTER!");
  
  try {
	  commentService.update(comment);
  }
  catch ( AccessDeniedException e) {
 	 System.out.println("****** ACCESS DENIED ! You Need to be OWNER and in Right Time Zone to Update  **********");

  }
 
  try {
	  commentService.delete(commentD);
	 	 System.out.println("****** Delete SUCCESS! **********");
	 	 commentD = null;
  }
  catch ( AccessDeniedException e) {
 	 System.out.println("****** ACCESS DENIED ! You Need to be ADMIN to Delete  **********");

  }


  // Logout Pete
  authenticateUser.logout();
  
 while (true)  {

		     // Now Login in as SEAN OR BILL - access denied
		      try {
		  		authenticateUser.authenticate(authenticationManager);
		  	} catch (Exception e) {
		  		// TODO Auto-generated catch block
		  		e.printStackTrace();
		  	}
	
		      
			 try {
			 comment.setContent("Second update");
			 commentService.update(comment);
		 	 System.out.println("****** ACCESS ALLOWED ! You ARE OWNER - you CAN  Update  **********");
	  }
		  catch ( AccessDeniedException e) {
		 	 System.out.println("****** ACCESS DENIED ! You Need to be OWNER and in Right Time Zone to Update  **********");
		
		  }

			 try {
				 commentService.findAll();
				 System.out.println("****** ACCESS ALLOWED ! You have LIST permission - So you CAN  READ [can call findAll] **********");
			 }
			 catch ( AccessDeniedException e) {
				 System.out.println("****** ACCESS DENIED ! You Need to have LIST permission to Read  **********");		
			 }
			 
			  try {
				 if (commentD != null) {
					 commentService.delete(commentD);
				 	 System.out.println("****** Delete SUCCESS! **********");
				 	 commentD = null;
				 }
			  }
			  catch ( AccessDeniedException e) {
			 	 System.out.println("****** ACCESS DENIED ! You Need to be ADMIN to Delete  **********");

			  }


 }      
} 
  }