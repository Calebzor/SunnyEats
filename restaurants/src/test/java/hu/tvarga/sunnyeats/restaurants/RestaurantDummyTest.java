package hu.tvarga.sunnyeats.restaurants;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RestaurantDummyTest {

	@Test
	public void addition() {
		RestaurantDummy restaurantDummy = new RestaurantDummy();

		assertEquals(2, restaurantDummy.addition(1, 1));
	}
}