package hu.tvarga.sunnyeats.weather.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.weather.service.ForecastStateInteractor;

public class ForecastViewModel extends ViewModel {

	private final ForecastLiveData forecastLiveData;

	private final ForecastStateInteractor forecastStateInteractor;

	public ForecastViewModel(ForecastLiveData forecastLiveData,
			ForecastStateInteractor forecastStateInteractor) {
		this.forecastLiveData = forecastLiveData;
		this.forecastStateInteractor = forecastStateInteractor;
	}

	public ForecastLiveData getForecastLiveData() {
		return forecastLiveData;
	}

	public void fetchForecast() {
		forecastStateInteractor.fetchForecast();
	}

	static class ForecastViewModelFactory implements ViewModelProvider.Factory {

		private final ForecastLiveData forecastLiveData;

		private final ForecastStateInteractor forecastStateInteractor;

		@Inject
		public ForecastViewModelFactory(ForecastLiveData forecastLiveData,
				ForecastStateInteractor forecastStateInteractor) {
			this.forecastLiveData = forecastLiveData;
			this.forecastStateInteractor = forecastStateInteractor;
		}

		@NonNull
		@Override
		public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
			//noinspection unchecked
			return (T) new ForecastViewModel(forecastLiveData, forecastStateInteractor);
		}
	}
}
