package hu.tvarga.sunnyeats.weather.api.retrofit;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import hu.tvarga.sunnyeats.common.app.di.annotations.qualifiers.ApplicationContext;
import hu.tvarga.sunnyeats.common.app.di.annotations.scope.ApplicationScoped;
import hu.tvarga.sunnyeats.common.app.util.CustomHttpLoggingInterceptor;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class WeatherRetrofitModule {

	private OkHttpClient client;

	@Provides
	@ApplicationScoped
	Retrofit provideWeatherRetrofit(@ApplicationContext Context context, Gson gson) {
		if (client == null) {
			File cacheFile = new File(context.getCacheDir(), "weatherOkHttpCache");

			Cache cache = new Cache(cacheFile, 1024 * 1024);

			int timeout = 10;

			OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache)
					.addNetworkInterceptor(new CustomHttpLoggingInterceptor());
			client = builder.connectTimeout(timeout, TimeUnit.SECONDS).readTimeout(timeout,
					TimeUnit.SECONDS).build();
		}

		return new Retrofit.Builder().baseUrl("http://api.openweathermap.org/").addConverterFactory(
				GsonConverterFactory.create(gson)).addCallAdapterFactory(
				RxJava2CallAdapterFactory.create()).client(client).build();
	}
}