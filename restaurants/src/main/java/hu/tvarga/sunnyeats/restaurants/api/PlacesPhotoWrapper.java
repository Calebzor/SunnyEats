package hu.tvarga.sunnyeats.restaurants.api;

import com.google.maps.GeoApiContext;
import com.google.maps.PhotoRequest;
import com.google.maps.PlacesApi;

import javax.inject.Inject;

public class PlacesPhotoWrapper {

	@Inject
	public PlacesPhotoWrapper() {
		// for dagger
	}

	public PhotoRequest photo(GeoApiContext context, String photoReference) {
		return PlacesApi.photo(context, photoReference);
	}
}
