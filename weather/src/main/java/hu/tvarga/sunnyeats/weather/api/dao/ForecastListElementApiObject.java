package hu.tvarga.sunnyeats.weather.api.dao;

import java.util.List;

public class ForecastListElementApiObject {

	public Long dt;

	public ForecastMainApiObject main;

	public List<ForecastWeatherApiObject> weather;

	public ForecastCloudsApiObject clouds;

	public ForecastWindApiObject wind;

}
