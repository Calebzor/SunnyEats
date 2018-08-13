package hu.tvarga.sunnyeats.restaurants.service;

import android.support.annotation.NonNull;

import com.yheriatovych.reductor.Actions;

import javax.inject.Inject;
import javax.inject.Provider;

import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.state.Dispatcher;
import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.restaurants.RestaurantsState;
import hu.tvarga.sunnyeats.restaurants.state.RestaurantsActions;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RestaurantsStateInteractor {

	private final Provider<AsyncState<RestaurantsState>> restaurantsStateAsyncStateProvider;

	private final Dispatcher dispatcher;

	private final RestaurantsActions actions = Actions.from(RestaurantsActions.class);

	private final Scheduler scheduler;

	@Inject
	public RestaurantsStateInteractor(
			Provider<AsyncState<RestaurantsState>> restaurantsStateAsyncStateProvider,
			Dispatcher dispatcher, Scheduler scheduler) {
		this.restaurantsStateAsyncStateProvider = restaurantsStateAsyncStateProvider;
		this.dispatcher = dispatcher;
		this.scheduler = scheduler;
	}

	public void fetchRestaurants(@NonNull City city) {
		if (restaurantsStateAsyncStateProvider.get().loading()) {
			return;
		}
		dispatcher.dispatch(actions.startRestaurantsFetch());

		Thread thread = new Thread(() -> {
			RestaurantsState restaurantsState =
					restaurantsStateAsyncStateProvider.get().value().get();
			restaurantsState.fetch(city).subscribeOn(Schedulers.io()).observeOn(scheduler)
					.subscribe(RestaurantsStateInteractor.this::handleFetchSuccess,
							RestaurantsStateInteractor.this::handleFetchError);
		});
		thread.start();

	}

	private void handleFetchError(Throwable throwable) {
		Timber.e(throwable, "fetching restaurants failed");
		dispatcher.dispatch(actions.failRestaurantsFetch((Exception) throwable));
	}

	private void handleFetchSuccess(RestaurantsState restaurantsState) {
		Timber.d("restaurants fetched", restaurantsState);
		dispatcher.dispatch(actions.succeedRestaurantsFetch(restaurantsState));
	}
}
