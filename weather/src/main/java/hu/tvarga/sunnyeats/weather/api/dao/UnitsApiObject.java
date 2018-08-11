package hu.tvarga.sunnyeats.weather.api.dao;

public enum UnitsApiObject {
	IMPERIAL,
	METRIC;

	public String toApiString() {
		return this.name().toLowerCase();
	}
}
