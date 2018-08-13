package hu.tvarga.sunnyeats.weather.dto;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.math.BigDecimal;
import java.util.List;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class WeatherViewPagerUIObject implements Parcelable {

	public static WeatherViewPagerUIObject create(List<BigDecimal> values, List<Integer> valuesUnit,
			List<Integer> descriptions) {
		return new AutoValue_WeatherViewPagerUIObject(values, valuesUnit, descriptions);
	}

	public abstract List<BigDecimal> values();

	public abstract List<Integer> valuesUnit();

	public abstract List<Integer> descriptions();
}
