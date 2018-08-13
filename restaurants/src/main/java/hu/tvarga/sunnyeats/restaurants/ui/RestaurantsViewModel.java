package hu.tvarga.sunnyeats.restaurants.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.restaurants.service.RestaurantsStateInteractor;

public class RestaurantsViewModel extends ViewModel {

	private final RestaurantsLiveData restaurantsLiveData;

	private final RestaurantsStateInteractor restaurantsStateInteractor;

	private City city;

	public RestaurantsViewModel(RestaurantsLiveData restaurantsLiveData,
			RestaurantsStateInteractor restaurantsStateInteractor) {
		this.restaurantsLiveData = restaurantsLiveData;
		this.restaurantsStateInteractor = restaurantsStateInteractor;
	}

	public RestaurantsLiveData getRestaurantsLiveData() {
		return restaurantsLiveData;
	}

	public void fetchRestaurants() {
		if (city == null) {
			return;
		}
		restaurantsStateInteractor.fetchRestaurants(city);
	}

	public void setCity(City city) {
		this.city = city;
	}

	static class RestaurantsViewModelFactory implements ViewModelProvider.Factory {

		private final RestaurantsLiveData restaurantsLiveData;

		private final RestaurantsStateInteractor restaurantsStateInteractor;

		@Inject
		public RestaurantsViewModelFactory(RestaurantsLiveData restaurantsLiveData,
				RestaurantsStateInteractor restaurantsStateInteractor) {
			this.restaurantsLiveData = restaurantsLiveData;
			this.restaurantsStateInteractor = restaurantsStateInteractor;
		}

		@NonNull
		@Override
		public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
			//noinspection unchecked
			return (T) new RestaurantsViewModel(restaurantsLiveData, restaurantsStateInteractor);
		}
	}
}
