package hu.tvarga.sunnyeats.weather.api.retrofit;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = WeatherRetrofitModule.class)
public class RetrofitWeatherApiModule {

	@Provides
	WeatherApiService provideWeatherApiService(Retrofit retrofit) {
		return retrofit.create(WeatherApiService.class);
	}
}
