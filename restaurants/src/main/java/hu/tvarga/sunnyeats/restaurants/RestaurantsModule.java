package hu.tvarga.sunnyeats.restaurants;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.tvarga.sunnyeats.restaurants.ui.RestaurantsFragment;

@Module
public interface RestaurantsModule {

	@ContributesAndroidInjector
	RestaurantsFragment contributeRestaurantsFragmentInjector();
}
