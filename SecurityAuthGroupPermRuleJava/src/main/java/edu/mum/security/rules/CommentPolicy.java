	package edu.mum.security.rules;

import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import edu.mum.domain.Comment;
import edu.mum.domain.Member;
import edu.mum.service.CommentService;

/*
 * Implements wrapper interface for Comment evaluator
 * 
 */
public class CommentPolicy implements Policy {

	@Autowired 
	CommentService commentService;
	
	private Comment comment;
	
	private String timeZone;
	
	public CommentPolicy() {}

 	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Member getMember() {
		return comment.getMember();
	}
 	
	// Evaluate Rules
	public Boolean checkRules(Authentication authentication, String action, Object asset, Map environment) {
		// Call default interface method 
		if (!hasActionAuthority(action)) return false;
		
		this.comment = (Comment)asset;
		this.timeZone = (String) environment.get("timeZone");
		
		Boolean result = false;
		Boolean owner = false;
		
		// Check for ownership of resource [domainObject]
 		String userName = this.getMember().getUserCredentials().getUsername();
    	if (authentication.getName().equals(userName))
    		owner = true;
    	
    	// perform actions based on permission
    	
    	switch (action) {
    		
	    	case "update":
	    		
 	    		// We can execute "Generic" rules here 
	    		if (!owner) {
	     	    	System.out.println("VIOLATED Update Owner RULE!!");
	     	    	result =  false;
	    		}
 
	    		// Only allow updates if in Central Time Zone		
	    		else if (!TimeZone.getDefault().getDisplayName().equals(timeZone)) {
	     	    	
	    			System.out.println("VIOLATED Comment Update Time Zone RULE!!");
	     	    	result =  false;
	     	    }
	    		else {
	    		
	    			System.out.println("Rules EVALUATION related to Comment update request SUCCESSFULL");
	    			result =  true;
	    		}
	    		
	    		break;
 	    		
	    	case "delete":
	    		System.out.println("EVALUATING Rules related to Comment delete request...");
 	    		result = true;
 	    		break;
 	    	
	    	case "save":
	    		System.out.println("EVALUATING Rules related to Comment save request...");
	    		result = true;

	    		
    	}
    	
 		return result;
	}
	


	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}


	
}
