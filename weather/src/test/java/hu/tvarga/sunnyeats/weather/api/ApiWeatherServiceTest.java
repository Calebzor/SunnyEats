package hu.tvarga.sunnyeats.weather.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import hu.tvarga.sunnyeats.weather.api.dao.ForecastApiObject;
import hu.tvarga.sunnyeats.weather.api.dao.UnitsApiObject;
import hu.tvarga.sunnyeats.weather.api.retrofit.WeatherApiService;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApiWeatherServiceTest {

	@Mock
	private WeatherApiService weatherApiService;

	private ApiWeatherService apiWeatherService;

	@Before
	public void setUp() {
		apiWeatherService = new ApiWeatherService(weatherApiService,
				new WeatherApiMapper(new CityApiMapper(new LocationApiMapper())));
	}

	@Test
	public void getForecast_callsApiService() {
		ForecastApiObject apiObject = new ForecastApiObject();
		Single<ForecastApiObject> single = Single.just(apiObject);
		when(weatherApiService.fetchForecast(anyString(), anyString(), anyString(), anyString()))
				.thenReturn(single);

		apiWeatherService.getForecast("1", "2");

		verify(weatherApiService).fetchForecast("1", "2", UnitsApiObject.METRIC.toApiString(),
				ApiConstants.WEATHER_API_KEY);
	}
}