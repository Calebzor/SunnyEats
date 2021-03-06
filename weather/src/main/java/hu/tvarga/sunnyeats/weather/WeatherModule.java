package hu.tvarga.sunnyeats.weather;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.tvarga.sunnyeats.weather.api.ApiWeatherService;
import hu.tvarga.sunnyeats.weather.api.retrofit.RetrofitWeatherApiModule;
import hu.tvarga.sunnyeats.weather.ui.WeatherFragment;
import hu.tvarga.sunnyeats.weather.ui.WeatherViewPagerFragment;

@Module(includes = {RetrofitWeatherApiModule.class})
public interface WeatherModule {

	@Binds
	WeatherRepository bindWeatherRepository(ApiWeatherService weatherService);

	@ContributesAndroidInjector
	WeatherFragment contributeWeatherFragmentInjector();

	@ContributesAndroidInjector
	WeatherViewPagerFragment contributeWeatherViewPagerFragmentInjector();
}
