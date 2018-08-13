package hu.tvarga.sunnyeats.restaurants.ui;

import android.arch.lifecycle.LiveData;

import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.rxjava2.RxStore;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.restaurants.RestaurantsState;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class RestaurantsLiveData extends LiveData<AsyncState<RestaurantsState>> {

	private final Cursor<AsyncState<RestaurantsState>> asyncStateCursor;

	private Disposable disposable;

	@Inject
	public RestaurantsLiveData(Cursor<AsyncState<RestaurantsState>> asyncStateCursor) {
		this.asyncStateCursor = asyncStateCursor;
	}

	@Override
	protected void onActive() {
		disposable = RxStore.asObservable(asyncStateCursor).flatMapSingle(this::mapToSingle)
				.subscribe(this::setValue);
	}

	@Override
	protected void onInactive() {
		disposable.dispose();
	}

	private Single<AsyncState<RestaurantsState>> mapToSingle(
			AsyncState<RestaurantsState> forecastStateAsyncState) {
		if (!forecastStateAsyncState.value().isPresent()) {
			return Single.just(forecastStateAsyncState.withEmptyValue());
		}
		return Single.just(forecastStateAsyncState);
	}
}
