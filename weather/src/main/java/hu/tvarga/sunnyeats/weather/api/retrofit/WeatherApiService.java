package hu.tvarga.sunnyeats.weather.api.retrofit;

import hu.tvarga.sunnyeats.weather.api.ApiConstants;
import hu.tvarga.sunnyeats.weather.api.dao.ForecastApiObject;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

	@GET(ApiConstants.URL_FORECAST)
	Single<ForecastApiObject> fetchForecast(
			@Query(ApiConstants.QUERY_PARAM_WEATHER_LOCATION_LATITUDE) String latitude,
			@Query(ApiConstants.QUERY_PARAM_WEATHER_LOCATION_LONGITUDE) String longitude,
			@Query(ApiConstants.QUERY_PARAM_FORMAT) String units,
			@Query(ApiConstants.QUERY_PARAM_API_KEY) String apiKey);
}
