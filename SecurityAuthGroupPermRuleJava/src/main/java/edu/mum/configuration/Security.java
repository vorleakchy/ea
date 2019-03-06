package edu.mum.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
@Configuration
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class Security extends SecurityConfigurerAdapter
{
	String groupAuthoritiesQuery = "select g.id, g.group_name, a.authority" +
                                " from groups g, groups_credentials gc, groups_authority ga, authority a " +
                                " where gc.userCredentials_username = ? and g.id = ga.Groups_id and g.id = gc.Groups_id " +
                                " and ga.authority_id = a.id";
     @Autowired
    private DataSource dataSource;
 	@Autowired
 	PermissionEvaluator customPermissionEvaluator;

     AuthenticationConfiguration authenticationConfiguration;
     
     // "imported" by @EnableGlobalAuthentication allows access to 
     // AuthenticationManager [authenticationConfiguration.getAuthenticationManager()]
     @Autowired
     public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
          this.authenticationConfiguration = authenticationConfiguration;
     }
     
     @Autowired
     public void configureGlobal( AuthenticationManagerBuilder authentication ) throws Exception
    {
    	 authentication
          .jdbcAuthentication()
          .dataSource( dataSource )
          .passwordEncoder( passwordEncoder() )
          .groupAuthoritiesByUsername(groupAuthoritiesQuery)
          .usersByUsernameQuery( "select username,password,enabled from credentials where username=?" )
          .getUserDetailsService().setEnableAuthorities(false);
    }
 

    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
   @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	  protected MethodSecurityExpressionHandler expressionHandler() {
		    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		    expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
		    return expressionHandler;
		  }

}