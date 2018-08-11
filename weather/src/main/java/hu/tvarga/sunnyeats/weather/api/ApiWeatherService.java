package hu.tvarga.sunnyeats.weather.api;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.weather.WeatherRepository;
import hu.tvarga.sunnyeats.weather.api.retrofit.WeatherApiService;
import hu.tvarga.sunnyeats.weather.dto.Forecast;
import io.reactivex.Single;

public class ApiWeatherService implements WeatherRepository {

	private final WeatherApiService weatherApiService;

	private final WeatherApiMapper weatherApiMapper;

	@Inject
	public ApiWeatherService(WeatherApiService weatherApiService,
			WeatherApiMapper weatherApiMapper) {
		this.weatherApiService = weatherApiService;
		this.weatherApiMapper = weatherApiMapper;
	}

	@Override
	public Single<Forecast> getForecast(String longitude, String latitude) {
		return weatherApiService.fetchForecast(longitude, latitude, ApiConstants.WEATHER_API_KEY)
				.map(weatherApiMapper::mapToForecast);
	}
}
