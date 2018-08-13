package hu.tvarga.sunnyeats.restaurants;

import dagger.Module;
import hu.tvarga.sunnyeats.restaurants.state.RestaurantsStateModule;

@Module(includes = {RestaurantsModule.class, RestaurantsStateModule.class})
public interface RestaurantsAppModule {}
