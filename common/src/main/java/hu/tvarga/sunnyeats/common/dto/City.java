package hu.tvarga.sunnyeats.common.dto;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class City implements Parcelable {

	public static final String CITY_EXTRA_KEY = "CITY_EXTRA_KEY";

	public static City create(String name, String country, Location location) {
		return new AutoValue_City(name, country, location);
	}

	public abstract String name();

	public abstract String country();

	public abstract Location location();
}
