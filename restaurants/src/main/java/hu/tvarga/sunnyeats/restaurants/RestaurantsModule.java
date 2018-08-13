package hu.tvarga.sunnyeats.restaurants;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.tvarga.sunnyeats.restaurants.api.RestaurantApiService;
import hu.tvarga.sunnyeats.restaurants.ui.RestaurantsFragment;

@Module
public interface RestaurantsModule {

	@Binds
	RestaurantsRepository bindRestaurantsRepository(RestaurantApiService restaurantApiService);

	@ContributesAndroidInjector
	RestaurantsFragment contributeRestaurantsFragmentInjector();
}
