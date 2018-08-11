package hu.tvarga.sunnyeats.weather.dto;

import com.google.auto.value.AutoValue;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class ForecastWeather {

	public static ForecastWeather create(String id, String main, String description, String icon) {
		return new AutoValue_ForecastWeather(id, main, description, icon);
	}

	public abstract String id();

	public abstract String main();

	public abstract String description();

	public abstract String icon();
}
