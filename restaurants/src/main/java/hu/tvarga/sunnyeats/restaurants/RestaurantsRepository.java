package hu.tvarga.sunnyeats.restaurants;

import java.util.List;

import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.restaurants.dto.Restaurant;
import io.reactivex.Single;

public interface RestaurantsRepository {

	Single<List<Restaurant>> getRestaurants(City city);
}
