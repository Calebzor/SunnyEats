package hu.tvarga.sunnyeats.weather;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.tvarga.sunnyeats.common.location.LocationModule;
import hu.tvarga.sunnyeats.weather.api.ApiWeatherService;
import hu.tvarga.sunnyeats.weather.api.retrofit.RetrofitWeatherApiModule;
import hu.tvarga.sunnyeats.weather.ui.WeatherFragment;

@Module(includes = {RetrofitWeatherApiModule.class, LocationModule.class})
public interface WeatherModule {

	@Binds
	WeatherRepository bindWeatherRepository(ApiWeatherService weatherService);

	@ContributesAndroidInjector
	WeatherFragment contributeWeatherFragmentInjector();
}
