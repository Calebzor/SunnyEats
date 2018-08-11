package hu.tvarga.sunnyeats.modules;

import dagger.Module;
import hu.tvarga.sunnyeats.weather.WeatherModule;
import hu.tvarga.sunnyeats.weather.state.ForecastStateModule;

@Module(includes = {WeatherModule.class, ForecastStateModule.class})
public interface WeatherAppModule {}
