package com.packt.webstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class FejpasecbApplication {

	public static void main(String[] args) {
		SpringApplication.run(FejpasecbApplication.class, args);
	}
	
	   @Bean
	    InternalResourceViewResolver internalResourceViewResolver () {
	        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setPrefix("/WEB-INF/views/");
	        viewResolver.setSuffix(".jsp");
			return viewResolver;

	    }

}
