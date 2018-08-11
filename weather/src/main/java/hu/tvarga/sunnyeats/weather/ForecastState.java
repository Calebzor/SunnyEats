package hu.tvarga.sunnyeats.weather;

import com.annimon.stream.Optional;
import com.google.auto.value.AutoValue;

import hu.tvarga.sunnyeats.weather.dto.Forecast;
import io.reactivex.Single;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class ForecastState {

	private WeatherRepository weatherRepository;

	public static ForecastState createUnknown(WeatherRepository weatherRepository) {
		ForecastState forecastState = new AutoValue_ForecastState(Optional.empty());

		forecastState.weatherRepository = weatherRepository;

		return forecastState;
	}

	public abstract Optional<Forecast> forecast();

	public ForecastState create(Forecast forecast, WeatherRepository weatherRepository) {
		ForecastState forecastState = new AutoValue_ForecastState(Optional.of(forecast));

		forecastState.weatherRepository = weatherRepository;

		return forecastState;
	}

	public Single<ForecastState> fetch(String longitude, String latitude) {
		return weatherRepository.getForecast(longitude, latitude).map(
				forecast -> create(forecast, weatherRepository));
	}
}
