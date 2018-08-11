package hu.tvarga.sunnyeats.common.location;

import dagger.Binds;
import dagger.Module;

@Module
public interface LocationModule {

	@Binds
	LocationProvider bindLocationProvider(LocationProviderImpl locationProvider);
}
