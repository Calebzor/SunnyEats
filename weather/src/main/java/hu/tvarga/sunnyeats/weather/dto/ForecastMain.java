package hu.tvarga.sunnyeats.weather.dto;

import com.google.auto.value.AutoValue;

import java.math.BigDecimal;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class ForecastMain {

	public static ForecastMain create(BigDecimal temp, BigDecimal tempMin, BigDecimal tempMax,
			BigDecimal pressure, BigDecimal seaLevel, BigDecimal groundLevel, Integer humidity) {
		return new AutoValue_ForecastMain(temp, tempMin, tempMax, pressure, seaLevel, groundLevel,
				humidity);
	}

	public abstract BigDecimal temp();

	public abstract BigDecimal tempMin();

	public abstract BigDecimal tempMax();

	public abstract BigDecimal pressure();

	public abstract BigDecimal seaLevel();

	public abstract BigDecimal groundLevel();

	public abstract Integer humidity();
}
