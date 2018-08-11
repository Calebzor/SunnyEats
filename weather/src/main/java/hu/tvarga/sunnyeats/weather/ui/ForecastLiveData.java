package hu.tvarga.sunnyeats.weather.ui;

import android.arch.lifecycle.LiveData;

import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.rxjava2.RxStore;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.weather.ForecastState;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class ForecastLiveData extends LiveData<AsyncState<ForecastState>> {

	private final Cursor<AsyncState<ForecastState>> asyncStateCursor;

	private Disposable disposable;

	@Inject
	public ForecastLiveData(Cursor<AsyncState<ForecastState>> asyncStateCursor) {
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

	private Single<AsyncState<ForecastState>> mapToSingle(
			AsyncState<ForecastState> forecastStateAsyncState) {
		if (!forecastStateAsyncState.value().isPresent()) {
			return Single.just(forecastStateAsyncState.withEmptyValue());
		}
		return Single.just(forecastStateAsyncState);
	}
}
