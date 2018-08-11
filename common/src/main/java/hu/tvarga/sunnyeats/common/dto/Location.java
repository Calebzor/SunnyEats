package hu.tvarga.sunnyeats.common.dto;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class Location implements Parcelable {

	public static Location create(String longitude, String latitude) {
		return new AutoValue_Location(longitude, latitude);
	}

	public abstract String longitude();

	public abstract String latitude();
}
