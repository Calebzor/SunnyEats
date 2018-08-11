package hu.tvarga.sunnyeats.app.ui;

import hu.tvarga.sunnyeats.common.app.ui.Screen;
import hu.tvarga.sunnyeats.common.ui.BaseFragment;
import hu.tvarga.sunnyeats.restaurants.ui.RestaurantsFragment;
import hu.tvarga.sunnyeats.weather.ui.WeatherFragment;

public final class ScreenToFragmentMapper {

	private ScreenToFragmentMapper() {
		// hiding constructor of helper class
	}

	public static BaseFragment getFragmentForScreen(Screen screen) {
		switch (screen) {
			case WEATHER:
				return WeatherFragment.create();
			case RESTAURANTS:
			default:
				return RestaurantsFragment.create();
		}
	}
}
