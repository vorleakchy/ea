package com.packt.webstore;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/*
 * SpringBootServletInitializer code is not executed when 
 * running your Spring Boot app using main method - only where run as a WAR
 */

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FejpasecbApplication.class);
	}

}
