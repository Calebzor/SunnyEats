package hu.tvarga.sunnyeats.weather.dto;

import com.google.auto.value.AutoValue;

import hu.tvarga.sunnyeats.common.dto.City;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class Forecast {

	public static Forecast create(City city) {
		return new AutoValue_Forecast(city);
	}

	public abstract City city();
}
