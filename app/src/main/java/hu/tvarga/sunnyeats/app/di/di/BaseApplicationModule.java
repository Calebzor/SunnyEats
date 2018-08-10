package hu.tvarga.sunnyeats.app.di.di;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import hu.tvarga.sunnyeats.app.di.di.annotations.qualifiers.ApplicationContext;
import hu.tvarga.sunnyeats.app.di.di.annotations.qualifiers.ForInteractorObserveOn;
import hu.tvarga.sunnyeats.app.di.di.annotations.scope.ApplicationScoped;
import hu.tvarga.sunnyeats.common.app.ui.AndroidStrings;
import hu.tvarga.sunnyeats.common.app.ui.Strings;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public class BaseApplicationModule {

	private final Application application;

	public BaseApplicationModule(Application application) {
		this.application = application;
	}

	@Provides
	@ForInteractorObserveOn
	Scheduler provideForInteractorObserverOn() {
		return AndroidSchedulers.mainThread();
	}

	@Provides
	@ApplicationScoped
	Strings provideStrings() {
		return new AndroidStrings(application.getResources());
	}

	@Provides
	@ApplicationContext
	Context provideApplicationContext() {
		return application;
	}
}
