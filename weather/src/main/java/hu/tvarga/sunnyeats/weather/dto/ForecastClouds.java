package hu.tvarga.sunnyeats.weather.dto;

import com.google.auto.value.AutoValue;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class ForecastClouds {

	public static ForecastClouds create(Integer all) {
		return new AutoValue_ForecastClouds(all);

	}

	public abstract Integer all();
}
