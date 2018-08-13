package hu.tvarga.sunnyeats.app.state;

import com.google.auto.value.AutoValue;
import com.yheriatovych.reductor.annotations.CombinedState;

import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.restaurants.RestaurantsState;
import hu.tvarga.sunnyeats.weather.ForecastState;

@CombinedState
@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class AppState {

	static AppState create(AsyncState<ForecastState> forecastStateAsyncState,
			AsyncState<RestaurantsState> restaurantsStateAsyncState) {
		return new AutoValue_AppState(forecastStateAsyncState, restaurantsStateAsyncState);
	}

	public abstract AsyncState<ForecastState> forecast();

	public abstract AsyncState<RestaurantsState> restaurants();
}

