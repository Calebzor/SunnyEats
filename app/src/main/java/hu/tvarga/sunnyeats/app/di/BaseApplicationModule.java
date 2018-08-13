package hu.tvarga.sunnyeats.app.di;

import android.app.Application;
import android.content.Context;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.Store;

import dagger.Module;
import dagger.Provides;
import hu.tvarga.sunnyeats.app.state.AppState;
import hu.tvarga.sunnyeats.app.state.AppStateReducer;
import hu.tvarga.sunnyeats.common.app.di.annotations.qualifiers.ApplicationContext;
import hu.tvarga.sunnyeats.common.app.di.annotations.scope.ApplicationScoped;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.state.Dispatcher;
import hu.tvarga.sunnyeats.common.app.ui.AndroidStrings;
import hu.tvarga.sunnyeats.common.app.ui.Strings;
import hu.tvarga.sunnyeats.restaurants.RestaurantsState;
import hu.tvarga.sunnyeats.weather.ForecastState;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public class BaseApplicationModule {

	private final Application application;

	public BaseApplicationModule(Application application) {
		this.application = application;
	}

	@ApplicationScoped
	@Provides
	Store<AppState> provideAppStateStore(Reducer<AsyncState<ForecastState>> forecastStateReducer,
			Reducer<AsyncState<RestaurantsState>> restaurantsStateReducer) {

		AppStateReducer appStateReducer = AppStateReducer.builder().forecastReducer(
				forecastStateReducer).restaurantsReducer(restaurantsStateReducer).build();

		return Store.create(appStateReducer);
	}

	@Provides
	Dispatcher provideDispatcher(Store<AppState> appStateStore) {
		return appStateStore::dispatch;
	}

	@Provides
	Scheduler provideForInteractorObserverOn() {
		return AndroidSchedulers.mainThread();
	}

	@Provides
	@ApplicationScoped
	Strings provideStrings() {
		return new AndroidStrings(application.getResources());
	}

	@Provides
	@ApplicationContext
	Context provideApplicationContext() {
		return application;
	}
}
