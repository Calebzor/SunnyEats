package hu.tvarga.sunnyeats.location;

import android.arch.lifecycle.LiveData;
import android.location.Location;

import javax.inject.Inject;

public class LocationLiveData extends LiveData<Location> {

	@Inject
	public LocationLiveData() {
		// for dagger
	}

	@Override
	public void setValue(Location value) {
		super.setValue(value);
	}
}
