package edu.mum.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.mum.domain.TemperatureInfo;
import edu.mum.weather.AuthenticationServiceClient;
import edu.mum.weather.WeatherServiceClient;

public class RmiClient {

    public static void main(String[] args) {
    ApplicationContext context =
            new AnnotationConfigApplicationContext("edu.mum.main");
        WeatherServiceClient client = context.getBean(WeatherServiceClient.class);

        AuthenticationServiceClient authClient = context.getBean(AuthenticationServiceClient.class);
 
		Authentication clientAuthentication = new UsernamePasswordAuthenticationToken("Bill", "Bill");

		clientAuthentication = authClient.authenticate(clientAuthentication);

		if (clientAuthentication == null)
			System.out.println(" Authentication failed ");
		else 
			System.out.println(" Authentication successful ");
			

         TemperatureInfo temperature = client.getTodayTemperature("Houston");
        System.out.println("Min temperature : " + temperature.getMin());
        System.out.println("Max temperature : " + temperature.getMax());
        System.out.println("Average temperature : " + temperature.getAverage());
        

    }
}
