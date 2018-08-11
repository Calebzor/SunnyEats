package hu.tvarga.sunnyeats.weather;

import dagger.Module;
import hu.tvarga.sunnyeats.weather.state.ForecastStateModule;

@Module(includes = {WeatherModule.class, ForecastStateModule.class})
public interface WeatherAppModule {}
