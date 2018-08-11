package hu.tvarga.sunnyeats.weather.state;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.state.AsyncStateReducer;
import hu.tvarga.sunnyeats.weather.ForecastState;
import hu.tvarga.sunnyeats.weather.WeatherRepository;
import timber.log.Timber;

@AutoReducer
public abstract class ForecastStateReducer extends AsyncStateReducer<ForecastState>
		implements Reducer<AsyncState<ForecastState>> {

	private AsyncState<ForecastState> initialState;

	public static ForecastStateReducer create(WeatherRepository weatherRepository) {
		ForecastStateReducer forecastStateReducer = new ForecastStateReducerImpl();
		forecastStateReducer.initialState = createInitialState(weatherRepository);
		return forecastStateReducer;
	}

	private static AsyncState<ForecastState> createInitialState(
			WeatherRepository weatherRepository) {
		ForecastState forecastState = ForecastState.createUnknown(weatherRepository);
		return AsyncState.createValue(forecastState);
	}

	@AutoReducer.InitialState
	AsyncState<ForecastState> initialState() {
		return initialState;
	}

	@AutoReducer.Action(value = ForecastActions.START_FORECAST_FETCH, from = ForecastActions.class)
	@Override
	protected AsyncState<ForecastState> startFetch(AsyncState<ForecastState> state) {
		Timber.d("starting to fetch forecast");
		return super.startFetch(state);
	}

	@AutoReducer.Action(value = ForecastActions.SUCCEED_FORECAST_FETCH,
			from = ForecastActions.class)
	@Override

	protected AsyncState<ForecastState> succeedFetch(AsyncState<ForecastState> state,
			ForecastState forecastState) {
		Timber.d("successfully fetched forecast");
		return super.succeedFetch(state, forecastState);
	}

	@AutoReducer.Action(value = ForecastActions.FAIL_FORECAST_FETCH, from = ForecastActions.class)
	@Override
	protected AsyncState<ForecastState> failFetch(AsyncState<ForecastState> state,
			Exception error) {

		Timber.d("fetching forecast failed");
		return super.failFetch(state, error);
	}
}
