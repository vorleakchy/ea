package edu.mum.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.mum.domain.TemperatureInfo;
import edu.mum.weather.WeatherServiceClient;

public class HttpProxyInvokerClient {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext("edu.mum.main");

        WeatherServiceClient client = context.getBean(WeatherServiceClient.class);
       TemperatureInfo temperature = client.getTodayTemperature("Houston");
        System.out.println("Min temperature : " + temperature.getMin());
        System.out.println("Max temperature : " + temperature.getMax());
        System.out.println("Average temperature : " + temperature.getAverage());
    }
}
