package hu.tvarga.sunnyeats.weather.dto;

import com.google.auto.value.AutoValue;

import org.pcollections.TreePVector;

import hu.tvarga.sunnyeats.common.dto.City;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class Forecast {

	public static Forecast create(City city, TreePVector<ForecastListElement> list) {
		return new AutoValue_Forecast(city, list);
	}

	public abstract City city();

	public abstract TreePVector<ForecastListElement> list();
}
