package hu.tvarga.sunnyeats.weather.service;

import com.yheriatovych.reductor.Actions;

import javax.inject.Inject;
import javax.inject.Provider;

import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.state.Dispatcher;
import hu.tvarga.sunnyeats.common.dto.Location;
import hu.tvarga.sunnyeats.common.location.LocationProvider;
import hu.tvarga.sunnyeats.weather.ForecastState;
import hu.tvarga.sunnyeats.weather.state.ForecastActions;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ForecastStateInteractor {

	private final Provider<AsyncState<ForecastState>> forecastStateAsyncStateProvider;

	private final Dispatcher dispatcher;

	private final ForecastActions actions = Actions.from(ForecastActions.class);

	private final LocationProvider locationProvider;

	private final Scheduler scheduler;

	@Inject
	public ForecastStateInteractor(
			Provider<AsyncState<ForecastState>> forecastStateAsyncStateProvider,
			Dispatcher dispatcher, Scheduler scheduler, LocationProvider locationProvider) {
		this.forecastStateAsyncStateProvider = forecastStateAsyncStateProvider;
		this.dispatcher = dispatcher;
		this.scheduler = scheduler;
		this.locationProvider = locationProvider;
	}

	public void fetchForecast() {
		if (forecastStateAsyncStateProvider.get().loading()) {
			return;
		}

		dispatcher.dispatch(actions.startForecastFetch());

		ForecastState forecastState = forecastStateAsyncStateProvider.get().value().get();

		Location location = locationProvider.get();
		forecastState.fetch(location.latitude(), location.longitude()).subscribeOn(Schedulers.io())
				.observeOn(scheduler).subscribe(this::handleFetchSuccess, this::handleFetchError);
	}

	private void handleFetchError(Throwable throwable) {
		Timber.e(throwable, "fetching forecast failed");
		dispatcher.dispatch(actions.failForecastFetch((Exception) throwable));
	}

	private void handleFetchSuccess(ForecastState forecastState) {
		Timber.d("forecast fetched", forecastState);
		dispatcher.dispatch(actions.succeedForecastFetch(forecastState));
	}
}
