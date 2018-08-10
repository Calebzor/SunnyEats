package hu.tvarga.sunnyeats;

import org.junit.Test;

import hu.tvarga.sunnyeats.common.CommonDummy;
import hu.tvarga.sunnyeats.restaurants.RestaurantDummy;
import hu.tvarga.sunnyeats.weather.WeatherDummy;

import static org.junit.Assert.assertEquals;

public class AppDummyTest {

	@Test
	public void divide() {
		AppDummy appDummy = new AppDummy(new WeatherDummy(), new RestaurantDummy(),
				new CommonDummy());

		assertEquals(2, appDummy.divide(4, 2));
	}
}