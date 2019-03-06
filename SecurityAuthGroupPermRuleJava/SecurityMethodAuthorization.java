package edu.mum.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled=true)

public class SecurityMethodAuthorization extends GlobalMethodSecurityConfiguration {

	@Autowired
	PermissionEvaluator customPermissionEvaluator;
/*	  @Bean
	  public MethodSecurityService methodSecurityService() {
	    return new MethodSecurityServiceImpl()
	  }
*/
//	  @Override
	  protected MethodSecurityExpressionHandler expressionHandler() {
	    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
	    expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
	    return expressionHandler;
	  }
	  
/*	  @Autowired
	  public void registerGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth
	        .inMemoryAuthentication()
	          .withUser("user").password("password").roles("USER").and()
	          .withUser("admin").password("password").roles("USER", "ADMIN");
	    }
*/
	  }
