package hu.tvarga.sunnyeats.restaurants;

import com.annimon.stream.Optional;
import com.google.auto.value.AutoValue;

import org.pcollections.TreePVector;

import java.util.List;

import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.restaurants.dto.Restaurant;
import io.reactivex.Single;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class RestaurantsState {

	private RestaurantsRepository restaurantsRepository;

	public static RestaurantsState createUnknown(RestaurantsRepository restaurantsRepository) {
		RestaurantsState restaurantsState = new AutoValue_RestaurantsState(Optional.empty());

		restaurantsState.restaurantsRepository = restaurantsRepository;

		return restaurantsState;
	}

	public abstract Optional<List<Restaurant>> restaurants();

	public RestaurantsState create(List<Restaurant> restaurants,
			RestaurantsRepository restaurantsRepository) {
		RestaurantsState restaurantsState = new AutoValue_RestaurantsState(
				Optional.of(restaurants));

		restaurantsState.restaurantsRepository = restaurantsRepository;

		return restaurantsState;
	}

	public Single<RestaurantsState> fetch(City city) {
		return restaurantsRepository.getRestaurants(city).map(
				restaurants -> create(TreePVector.from(restaurants), restaurantsRepository));
	}
}
