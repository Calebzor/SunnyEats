package hu.tvarga.sunnyeats.weather.api;

import org.pcollections.TreePVector;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.weather.api.dao.ForecastApiObject;
import hu.tvarga.sunnyeats.weather.api.dao.ForecastListElementApiObject;
import hu.tvarga.sunnyeats.weather.api.dao.ForecastWeatherApiObject;
import hu.tvarga.sunnyeats.weather.dto.Forecast;
import hu.tvarga.sunnyeats.weather.dto.ForecastClouds;
import hu.tvarga.sunnyeats.weather.dto.ForecastListElement;
import hu.tvarga.sunnyeats.weather.dto.ForecastMain;
import hu.tvarga.sunnyeats.weather.dto.ForecastWeather;
import hu.tvarga.sunnyeats.weather.dto.ForecastWind;

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
		List<ForecastListElement> forecastListElementsList = new ArrayList<>();
		for (ForecastListElementApiObject forecastListElementApiObject : forecastApiObject.list) {
			Instant instant = Instant.ofEpochSecond(forecastListElementApiObject.dt);
			ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

			ForecastMain forecastMain = ForecastMain.create(forecastListElementApiObject.main.temp,
					forecastListElementApiObject.main.temp_min,
					forecastListElementApiObject.main.temp_max,
					forecastListElementApiObject.main.pressure,
					forecastListElementApiObject.main.sea_level,
					forecastListElementApiObject.main.grnd_level,
					forecastListElementApiObject.main.humidity);

			List<ForecastWeather> forecastWeatherList = new ArrayList<>();
			for (ForecastWeatherApiObject forecastWeatherApiObject : forecastListElementApiObject.weather) {
				forecastWeatherList.add(ForecastWeather
						.create(forecastWeatherApiObject.id, forecastWeatherApiObject.main,
								forecastWeatherApiObject.description,
								forecastWeatherApiObject.icon));
			}
			TreePVector<ForecastWeather> weathers = TreePVector.from(forecastWeatherList);

			ForecastClouds forecastClouds = ForecastClouds.create(
					forecastListElementApiObject.clouds.all);
			ForecastWind forecastWind = ForecastWind.create(forecastListElementApiObject.wind.speed,
					forecastListElementApiObject.wind.deg);

			forecastListElementsList.add(ForecastListElement
					.create(zonedDateTime, forecastMain, weathers, forecastClouds, forecastWind));
		}
		TreePVector<ForecastListElement> list = TreePVector.from(forecastListElementsList);

		return Forecast.create(city, list);
	}
}
