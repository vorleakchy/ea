package edu.mum.security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import edu.mum.security.rules.CustomPermissionEvaluator;

@Component
public class AuthenticateUser {
 
	String timeZone;
	
   public  void authenticate(AuthenticationManager authenticationManager) throws Exception {
  
	  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    while(true) {
      System.out.print("Please enter your username: ");
      String name = in.readLine();
      System.out.print("Please enter your password: ");
      String password = in.readLine();
      try {
        Authentication request = new UsernamePasswordAuthenticationToken(name, password);
        Authentication result = authenticationManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);
        System.out.println("AUTHORITIES GRANTED:");
        		List<GrantedAuthority> aList = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority ga : aList) System.out.println( "AUTHORITY " + ga.getAuthority());
        break;
      } catch(AuthenticationException e) {
  		   System.out.println( );
       System.out.println("Authentication failed: " + e.getMessage());
      }
    }
	   System.out.println( );
   System.out.println("Successfully authenticated. Security context contains: " +
              SecurityContextHolder.getContext().getAuthentication());
  
   System.out.println("1.  Central Standard Time");
   System.out.println("2.  Eastern Standard Time");
 
   System.out.print("Please Choose a Time Zone: ");
   String zone = in.readLine();
   
   this.timeZone = "Central Standard Time";
   if (zone.equals("2")) timeZone = "Eastern Standard Time";
	
 
   }
  
  public void logout() {
 
	  // Clears the context for the current user/thread
	  SecurityContextHolder.clearContext();
 }

public String getTimeZone() {
	return timeZone;
}

public void setTimeZone(String timeZone) {
	this.timeZone = timeZone;
}
}
