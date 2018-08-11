package hu.tvarga.sunnyeats.common.app.locale;

import java.util.Locale;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.app.ui.SystemConfigurationWrapper;

public class LocaleProvider {

	private final SystemConfigurationWrapper systemConfigurationWrapper;

	@Inject
	public LocaleProvider(SystemConfigurationWrapper systemConfigurationWrapper) {
		this.systemConfigurationWrapper = systemConfigurationWrapper;
	}

	public Locale getCurrentLocale() {
		return systemConfigurationWrapper.getCurrentLocale();
	}

	public boolean isLocalEnUS() {
		return Locale.US.equals(getCurrentLocale());
	}
}
