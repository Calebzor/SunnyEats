package hu.tvarga.sunnyeats.weather.dto;

import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class ForecastWind {

	public static ForecastWind create(BigDecimal speed, BigDecimal degree) {
		return new AutoValue_ForecastWind(speed, degree);
	}

	public abstract BigDecimal speed();

	public abstract BigDecimal deg();
}
