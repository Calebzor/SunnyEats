package hu.tvarga.sunnyeats.restaurants.state;

import com.yheriatovych.reductor.Cursor;
import com.yheriatovych.reductor.Cursors;
import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.Store;

import dagger.Module;
import dagger.Provides;
import hu.tvarga.sunnyeats.app.state.AppState;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.restaurants.RestaurantsRepository;
import hu.tvarga.sunnyeats.restaurants.RestaurantsState;

@Module
public class RestaurantsStateModule {

	@Provides
	Reducer<AsyncState<RestaurantsState>> provideRestaurantsStateReducer(
			RestaurantsRepository restaurantsRepository) {
		return RestaurantsStateReducer.create(restaurantsRepository);
	}

	@Provides
	AsyncState<RestaurantsState> provideRestaurantsState(Store<AppState> appStateStore) {
		return appStateStore.getState().restaurants();
	}

	@Provides
	Cursor<AsyncState<RestaurantsState>> provideRestaurantsStateCursor(
			Store<AppState> appStateStore) {
		return Cursors.map(appStateStore, AppState::restaurants);
	}
}
