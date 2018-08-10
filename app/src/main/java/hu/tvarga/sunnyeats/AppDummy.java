package hu.tvarga.sunnyeats;

import hu.tvarga.sunnyeats.common.CommonDummy;
import hu.tvarga.sunnyeats.restaurants.RestaurantDummy;
import hu.tvarga.sunnyeats.weather.WeatherDummy;

public class AppDummy {

	private final WeatherDummy weatherDummy;

	private final RestaurantDummy restaurantDummy;

	private final CommonDummy commonDummy;

	public AppDummy(WeatherDummy weatherDummy, RestaurantDummy restaurantDummy,
			CommonDummy commonDummy) {
		this.weatherDummy = weatherDummy;
		this.restaurantDummy = restaurantDummy;
		this.commonDummy = commonDummy;
	}

	public int divide(int first, int second) {
		return first / second;
	}

	public int multiply(int first, int second) {
		return weatherDummy.multiply(first, second);
	}

	public int addition(int first, int second) {
		return restaurantDummy.addition(first, second);
	}

	public int subtract(int first, int second) {
		return commonDummy.subtract(first, second);
	}
}
