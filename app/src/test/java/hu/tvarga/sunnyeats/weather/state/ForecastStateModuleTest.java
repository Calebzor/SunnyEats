package hu.tvarga.sunnyeats.weather.state;

import com.yheriatovych.reductor.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import hu.tvarga.sunnyeats.app.state.AppState;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.weather.ForecastState;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ForecastStateModuleTest {

	@Mock
	private Store<AppState> appStateStore;

	@Mock
	private AppState appState;

	@Mock
	private AsyncState<ForecastState> forecastStateAsyncState;

	private ForecastStateModule forecastStateModule;

	@Before
	public void setUp() {
		forecastStateModule = new ForecastStateModule();
	}

	@Test
	public void provideForecastState() {
		when(appStateStore.getState()).thenReturn(appState);
		when(appState.forecast()).thenReturn(forecastStateAsyncState);

		assertEquals(forecastStateAsyncState,
				forecastStateModule.provideForecastState(appStateStore));
	}
}