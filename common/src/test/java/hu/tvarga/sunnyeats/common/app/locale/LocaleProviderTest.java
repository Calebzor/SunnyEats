package hu.tvarga.sunnyeats.common.app.locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import hu.tvarga.sunnyeats.common.app.ui.SystemConfigurationWrapper;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocaleProviderTest {

	@Mock
	private SystemConfigurationWrapper systemConfigurationWrapper;

	private LocaleProvider localeProvider;

	@Before
	public void setUp() {
		localeProvider = new LocaleProvider(systemConfigurationWrapper);
	}

	@Test
	public void getCurrentLocale_getsLocaleFromSystemConfiguration() {
		localeProvider.getCurrentLocale();

		verify(systemConfigurationWrapper).getCurrentLocale();
	}
}