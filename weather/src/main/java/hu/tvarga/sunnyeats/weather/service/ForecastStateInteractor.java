package hu.tvarga.sunnyeats.weather.service;

import android.location.Location;
import android.support.annotation.NonNull;

import com.yheriatovych.reductor.Actions;

import javax.inject.Inject;
import javax.inject.Provider;

import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.state.Dispatcher;
import hu.tvarga.sunnyeats.weather.ForecastState;
import hu.tvarga.sunnyeats.weather.state.ForecastActions;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ForecastStateInteractor {

	private final Provider<AsyncState<ForecastState>> forecastStateAsyncStateProvider;

	private final Dispatcher dispatcher;

	private final ForecastActions actions = Actions.from(ForecastActions.class);

	private final Scheduler scheduler;

	@Inject
	public ForecastStateInteractor(
			Provider<AsyncState<ForecastState>> forecastStateAsyncStateProvider,
			Dispatcher dispatcher, Scheduler scheduler) {
		this.forecastStateAsyncStateProvider = forecastStateAsyncStateProvider;
		this.dispatcher = dispatcher;
		this.scheduler = scheduler;
	}

	public void fetchForecast(@NonNull Location location) {
		if (forecastStateAsyncStateProvider.get().loading()) {
			return;
		}

		dispatcher.dispatch(actions.startForecastFetch());

		ForecastState forecastState = forecastStateAsyncStateProvider.get().value().get();

		forecastState.fetch(String.valueOf(location.getLatitude()),
				String.valueOf(location.getLongitude())).subscribeOn(Schedulers.io()).observeOn(
				scheduler).subscribe(this::handleFetchSuccess, this::handleFetchError);
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
