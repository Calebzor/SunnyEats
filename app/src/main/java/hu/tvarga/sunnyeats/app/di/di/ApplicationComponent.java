package hu.tvarga.sunnyeats.app.di.di;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import hu.tvarga.sunnyeats.app.SunnyEatsApplication;
import hu.tvarga.sunnyeats.app.di.di.annotations.scope.ApplicationScoped;
import hu.tvarga.sunnyeats.common.app.api.ApiModule;
import hu.tvarga.sunnyeats.common.app.api.retrofit.RetrofitModule;
import hu.tvarga.sunnyeats.main.MainModule;

@ApplicationScoped
@Component(modules = {BaseApplicationModule.class, AndroidInjectionModule.class,

		MainModule.class,

		ApiModule.class, RetrofitModule.class,

})
public interface ApplicationComponent extends AndroidInjector<SunnyEatsApplication> {}