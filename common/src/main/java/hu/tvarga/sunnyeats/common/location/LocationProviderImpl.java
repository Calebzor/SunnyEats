package hu.tvarga.sunnyeats.common.location;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.dto.Location;

class LocationProviderImpl implements LocationProvider {

	@Inject
	public LocationProviderImpl() {
		// for dagger
	}

	@Override
	public Location get() {
		// Szalkszentmarton
		return Location.create("19", "47");
		// invalid aka no city
		//		return Location.create("47", "19");
	}
}
