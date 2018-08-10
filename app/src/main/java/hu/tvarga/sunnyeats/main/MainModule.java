package hu.tvarga.sunnyeats.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainModule {

	@ContributesAndroidInjector
	MainActivity contributeMainActivityInjector();
}
