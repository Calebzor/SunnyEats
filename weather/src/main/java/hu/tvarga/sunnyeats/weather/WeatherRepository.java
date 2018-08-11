package hu.tvarga.sunnyeats.weather;

import hu.tvarga.sunnyeats.weather.dto.Forecast;
import io.reactivex.Single;

public interface WeatherRepository {

	Single<Forecast> getForecast(String longitude, String latitude);
}
