package hu.tvarga.sunnyeats.common.app.ui;

import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

import javax.inject.Inject;

public class SystemConfigurationWrapper {

	@Inject
	public SystemConfigurationWrapper() {
		// for dagger
	}

	public Locale getCurrentLocale() {
		Resources system = Resources.getSystem();
		return getLocale(system);
	}

	@SuppressWarnings("squid:CallToDeprecatedMethod")
	private Locale getLocale(Resources resources) {
		Locale locale = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			resources.getConfiguration().getLocales().get(0);
		}
		else {
			locale = resources.getConfiguration().locale;
		}
		// final fall back to legacy way
		if (locale == null) {
			locale = resources.getConfiguration().locale;
		}
		return locale;
	}
}
