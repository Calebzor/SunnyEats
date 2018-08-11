package hu.tvarga.sunnyeats.weather;

import dagger.Module;
import hu.tvarga.sunnyeats.restaurants.RestaurantsModule;

@Module(includes = {RestaurantsModule.class})
public interface RestaurantsAppModule {}
