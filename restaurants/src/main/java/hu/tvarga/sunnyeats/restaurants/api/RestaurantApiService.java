package hu.tvarga.sunnyeats.restaurants.api;

import android.support.annotation.Nullable;

import com.google.maps.GeoApiContext;
import com.google.maps.ImageResult;
import com.google.maps.PhotoRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.common.dto.Location;
import hu.tvarga.sunnyeats.restaurants.RestaurantsRepository;
import hu.tvarga.sunnyeats.restaurants.dto.Restaurant;
import io.reactivex.Single;
import timber.log.Timber;

public class RestaurantApiService implements RestaurantsRepository {

	private final PlacesPhotoWrapper placesPhotoWrapper;

	@Inject
	public RestaurantApiService(PlacesPhotoWrapper placesPhotoWrapper) {
		this.placesPhotoWrapper = placesPhotoWrapper;
	}

	@SuppressWarnings("squid:S2142")
	// very very rudimentary solution
	@Override
	public Single<List<Restaurant>> getRestaurants(City city) {
		GeoApiContext context = new GeoApiContext.Builder().apiKey(
				"AIzaSyBzUUVJdMfinTY05zcbuQQOrFg5O0h4uR4").build();

		List<Restaurant> restaurants = new LinkedList<>();
		try {
			PlacesSearchResponse results = getPlacesSearchResponse(city, context);

			if (results == null || results.results == null) {
				return Single.just(restaurants);
			}
			for (PlacesSearchResult result : results.results) {
				String photoReference = getPhotoReference(result);
				byte[] imageData = getImageData(context, photoReference);
				restaurants.add(Restaurant
						.create(result.placeId, result.name, city, result.rating, photoReference,
								imageData, Location.create(result.geometry.location.lng,
										result.geometry.location.lat)));
			}
		}
		catch (ApiException | InterruptedException | IOException e) {
			Timber.e(e);
			return Single.error(e);
		}

		Collections.sort(restaurants, (o1, o2) -> o2.rating().compareTo(o1.rating()));
		return Single.just(restaurants);
	}

	private byte[] getImageData(GeoApiContext context, String photoReference)
			throws ApiException, InterruptedException, IOException {
		byte[] imageData = null;
		if (photoReference != null) {
			ImageResult imageResult;
			imageResult = getImageResult(context, photoReference);

			if (imageResult != null) {
				String contentType = imageResult.contentType;
				if (contentType.contains("jpeg")) {
					imageData = imageResult.imageData;
				}
			}
		}
		return imageData;
	}

	ImageResult getImageResult(GeoApiContext context, String photoReference)
			throws ApiException, InterruptedException, IOException {
		int imageDimension = 160;
		PhotoRequest photoRequest = placesPhotoWrapper.photo(context, photoReference);
		if (photoRequest != null) {
			return photoRequest.maxHeight(imageDimension).maxWidth(imageDimension).await();
		}
		return null;

	}

	private PlacesSearchResponse getPlacesSearchResponse(City city, GeoApiContext context)
			throws ApiException, InterruptedException, IOException {
		LatLng location = new LatLng(city.location().latitude(), city.location().longitude());
		return PlacesApi.textSearchQuery(context, "restaurant").radius(500).location(location)
				.await();
	}

	@Nullable
	private String getPhotoReference(PlacesSearchResult result) {
		String photoReference = null;
		if (result.photos != null) {
			Photo[] photos = result.photos;
			photoReference = photos[0].photoReference;
		}
		return photoReference;
	}
}
