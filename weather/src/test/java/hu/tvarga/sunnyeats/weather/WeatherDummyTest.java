package hu.tvarga.sunnyeats.weather;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeatherDummyTest {

	@Test
	public void multiply() {
		WeatherDummy weatherDummy = new WeatherDummy();

		assertEquals(2, weatherDummy.multiply(1, 2));
	}
}