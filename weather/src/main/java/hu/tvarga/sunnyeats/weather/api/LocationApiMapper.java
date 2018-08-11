package hu.tvarga.sunnyeats.weather.api;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.dto.Location;
import hu.tvarga.sunnyeats.weather.api.dao.CoordApiObject;

public class LocationApiMapper {

	@Inject
	public LocationApiMapper() {
		// for dagger
	}

	public Location mapToLocation(CoordApiObject coordApiObject) {
		if (coordApiObject.lat == null || coordApiObject.lon == null) {
			return null;
		}
		return Location.create(coordApiObject.lat, coordApiObject.lon);
	}
}
