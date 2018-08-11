package hu.tvarga.sunnyeats.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.tvarga.sunnyeats.weather.RestaurantsAppModule;
import hu.tvarga.sunnyeats.weather.WeatherAppModule;

@Module(includes = {WeatherAppModule.class, RestaurantsAppModule.class})
public interface MainModule {

	@ContributesAndroidInjector
	MainActivity contributeMainActivityInjector();
}
