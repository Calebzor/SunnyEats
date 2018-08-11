package hu.tvarga.sunnyeats.weather.api;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.weather.api.dao.ForecastApiObject;
import hu.tvarga.sunnyeats.weather.dto.Forecast;

public class WeatherApiMapper {

	private final CityApiMapper cityApiMapper;

	@Inject
	public WeatherApiMapper(CityApiMapper cityApiMapper) {
		this.cityApiMapper = cityApiMapper;
	}

	public Forecast mapToForecast(ForecastApiObject forecastApiObject) {
		City city = cityApiMapper.mapToCity(forecastApiObject.city);
		if (city == null) {
			return null;
		}

		return Forecast.create(city);
	}
}
