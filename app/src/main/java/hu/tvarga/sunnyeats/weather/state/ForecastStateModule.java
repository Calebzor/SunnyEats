package hu.tvarga.sunnyeats.weather.state;

import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.Store;

import dagger.Module;
import dagger.Provides;
import hu.tvarga.sunnyeats.app.state.AppState;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.weather.ForecastState;
import hu.tvarga.sunnyeats.weather.WeatherRepository;

@Module
public class ForecastStateModule {

	@Provides
	Reducer<AsyncState<ForecastState>> provideForecastStateReducer(
			WeatherRepository weatherRepository) {
		return ForecastStateReducer.create(weatherRepository);
	}

	@Provides
	AsyncState<ForecastState> provideForecastState(Store<AppState> appStateStore) {
		return appStateStore.getState().forecast();
	}

	@Provides
	Cursor<AsyncState<ForecastState>> provideForecastStateCursor(Store<AppState> appStateStore) {
		return Cursors.map(appStateStore, AppState::forecast);
	}
}