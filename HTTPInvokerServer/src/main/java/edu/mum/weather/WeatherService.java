package edu.mum.weather;

import java.util.List;

import edu.mum.domain.TemperatureInfo;

import java.util.Date; 

public interface WeatherService {

    public List<TemperatureInfo> getTemperatures(String city, List<Date> dates);
}
