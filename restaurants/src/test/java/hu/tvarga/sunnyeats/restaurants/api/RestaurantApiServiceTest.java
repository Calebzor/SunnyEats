package hu.tvarga.sunnyeats.restaurants.api;

import com.google.maps.GeoApiContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantApiServiceTest {

	@Mock
	private PlacesPhotoWrapper placesPhotoWrapper;

	@Mock
	private GeoApiContext geoApiContext;

	private RestaurantApiService restaurantApiService;

	@Before
	public void setUp() {
		restaurantApiService = new RestaurantApiService(placesPhotoWrapper);
	}

	@Test
	public void getImageResult() throws Exception {
		String photoReference = "photoReference";

		restaurantApiService.getImageResult(geoApiContext, photoReference);

		verify(placesPhotoWrapper).photo(geoApiContext, photoReference);
	}
}