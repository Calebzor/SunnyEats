package hu.tvarga.sunnyeats.app.state;

import com.google.auto.value.AutoValue;
import com.yheriatovych.reductor.annotations.CombinedState;

import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.weather.ForecastState;

@CombinedState
@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class AppState {

	static AppState create(AsyncState<ForecastState> forecastStateAsyncState) {
		return new AutoValue_AppState(forecastStateAsyncState);
	}

	public abstract AsyncState<ForecastState> forecast();
}

