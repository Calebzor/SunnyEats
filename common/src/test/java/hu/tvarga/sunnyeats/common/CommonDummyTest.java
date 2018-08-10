package hu.tvarga.sunnyeats.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommonDummyTest {

	@Test
	public void subtract() {
		CommonDummy commonDummy = new CommonDummy();

		assertEquals(1, commonDummy.subtract(2, 1));
	}
}