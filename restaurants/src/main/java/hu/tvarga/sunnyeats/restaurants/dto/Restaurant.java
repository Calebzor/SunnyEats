package hu.tvarga.sunnyeats.restaurants.dto;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.common.dto.Location;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class Restaurant {

	public static Restaurant create(String id, String name, City city, Float rating,
			@Nullable String photoReference, @Nullable byte[] imageData,
			@Nullable Location location) {
		return new AutoValue_Restaurant(id, name, city, rating, photoReference, imageData,
				location);
	}

	public abstract String id();

	public abstract String name();

	public abstract City city();

	public abstract Float rating();

	@Nullable
	public abstract String photoReference();

	@SuppressWarnings("mutable")
	@Nullable
	public abstract byte[] imageData();

	@Nullable
	public abstract Location location();
}
