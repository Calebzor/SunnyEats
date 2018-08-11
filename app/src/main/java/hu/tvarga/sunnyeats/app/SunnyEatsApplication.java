package hu.tvarga.sunnyeats.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import hu.tvarga.sunnyeats.BuildConfig;
import hu.tvarga.sunnyeats.app.di.ApplicationComponent;
import hu.tvarga.sunnyeats.app.di.BaseApplicationModule;
import hu.tvarga.sunnyeats.app.di.DaggerApplicationComponent;
import timber.log.Timber;

public class SunnyEatsApplication extends Application
		implements HasActivityInjector, HasServiceInjector {

	@Inject
	DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

	@Inject
	DispatchingAndroidInjector<Service> dispatchingServiceInjector;

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
		createComponent().inject(this);
	}

	@Override
	public AndroidInjector<Activity> activityInjector() {
		return dispatchingActivityInjector;
	}

	@NonNull
	protected ApplicationComponent createComponent() {
		return DaggerApplicationComponent.builder().baseApplicationModule(
				new BaseApplicationModule(this)).build();
	}

	@Override
	public AndroidInjector<Service> serviceInjector() {
		return dispatchingServiceInjector;
	}

}