package hu.tvarga.sunnyeats.restaurants.state;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.state.AsyncStateReducer;
import hu.tvarga.sunnyeats.restaurants.RestaurantsRepository;
import hu.tvarga.sunnyeats.restaurants.RestaurantsState;
import timber.log.Timber;

@AutoReducer
public abstract class RestaurantsStateReducer extends AsyncStateReducer<RestaurantsState>
		implements Reducer<AsyncState<RestaurantsState>> {

	private AsyncState<RestaurantsState> initialState;

	public static RestaurantsStateReducer create(RestaurantsRepository restaurantsRepository) {
		RestaurantsStateReducer restaurantsStateReducer = new RestaurantsStateReducerImpl();
		restaurantsStateReducer.initialState = createInitialState(restaurantsRepository);
		return restaurantsStateReducer;
	}

	private static AsyncState<RestaurantsState> createInitialState(
			RestaurantsRepository restaurantsRepository) {
		RestaurantsState restaurantsState = RestaurantsState.createUnknown(restaurantsRepository);
		return AsyncState.createValue(restaurantsState);
	}

	@AutoReducer.InitialState
	AsyncState<RestaurantsState> initialState() {
		return initialState;
	}

	@AutoReducer.Action(value = RestaurantsActions.START_RESTAURANTS_FETCH,
			from = RestaurantsActions.class)
	@Override
	protected AsyncState<RestaurantsState> startFetch(AsyncState<RestaurantsState> state) {
		Timber.d("starting to fetch forecast");
		return super.startFetch(state);
	}

	@AutoReducer.Action(value = RestaurantsActions.SUCCEED_RESTAURANTS_FETCH,
			from = RestaurantsActions.class)
	@Override

	protected AsyncState<RestaurantsState> succeedFetch(AsyncState<RestaurantsState> state,
			RestaurantsState forecastState) {
		Timber.d("successfully fetched forecast");
		return super.succeedFetch(state, forecastState);
	}

	@AutoReducer.Action(value = RestaurantsActions.FAIL_RESTAURANTS_FETCH,
			from = RestaurantsActions.class)
	@Override
	protected AsyncState<RestaurantsState> failFetch(AsyncState<RestaurantsState> state,
			Exception error) {

		Timber.d("fetching forecast failed");
		return super.failFetch(state, error);
	}
}
