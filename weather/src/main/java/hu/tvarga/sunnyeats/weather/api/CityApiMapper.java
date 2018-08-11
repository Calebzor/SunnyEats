package hu.tvarga.sunnyeats.weather.api;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.common.dto.Location;
import hu.tvarga.sunnyeats.weather.api.dao.CityApiObject;

public class CityApiMapper {

	private final LocationApiMapper locationApiMapper;

	@Inject
	public CityApiMapper(LocationApiMapper locationApiMapper) {
		this.locationApiMapper = locationApiMapper;
	}

	public City mapToCity(CityApiObject cityApiObject) {
		Location location = locationApiMapper.mapToLocation(cityApiObject.coord);
		if (location == null || cityApiObject.name == null) {
			return null;
		}

		return City.create(cityApiObject.name, cityApiObject.country, location);
	}
}
