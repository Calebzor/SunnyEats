package hu.tvarga.sunnyeats.restaurants.state;

import com.yheriatovych.reductor.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import hu.tvarga.sunnyeats.app.state.AppState;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.restaurants.RestaurantsState;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantsStateModuleTest {

	@Mock
	private Store<AppState> appStateStore;

	@Mock
	private AppState appState;

	@Mock
	private AsyncState<RestaurantsState> restaurantsStateAsyncState;

	private RestaurantsStateModule restaurantsStateModule;

	@Before
	public void setUp() {
		restaurantsStateModule = new RestaurantsStateModule();
	}

	@Test
	public void provideRestaurantsState() {
		when(appStateStore.getState()).thenReturn(appState);
		when(appState.restaurants()).thenReturn(restaurantsStateAsyncState);

		assertEquals(restaurantsStateAsyncState,
				restaurantsStateModule.provideRestaurantsState(appStateStore));
	}
}
