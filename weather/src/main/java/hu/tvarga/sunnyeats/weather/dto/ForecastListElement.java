package hu.tvarga.sunnyeats.weather.dto;

import com.google.auto.value.AutoValue;

import org.pcollections.TreePVector;
import org.threeten.bp.ZonedDateTime;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class ForecastListElement {

	public static ForecastListElement create(ZonedDateTime timeOfData, ForecastMain forecastMain,
			TreePVector<ForecastWeather> forecastWeathers, ForecastClouds forecastClouds,
			ForecastWind forecastWind) {
		return new AutoValue_ForecastListElement(timeOfData, forecastMain, forecastWeathers,
				forecastClouds, forecastWind);
	}

	public abstract ZonedDateTime timeOfData();

	public abstract ForecastMain forecastMain();

	public abstract TreePVector<ForecastWeather> forecastWeathers();

	public abstract ForecastClouds forecastClouds();

	public abstract ForecastWind forecastWind();
}
