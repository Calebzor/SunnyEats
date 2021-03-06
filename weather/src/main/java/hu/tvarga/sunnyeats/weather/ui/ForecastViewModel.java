package hu.tvarga.sunnyeats.weather.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.location.Location;
import android.support.annotation.NonNull;

import com.annimon.stream.Optional;

import org.pcollections.TreePVector;
import org.threeten.bp.format.TextStyle;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.app.locale.LocaleProvider;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.ui.Strings;
import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.location.LocationLiveData;
import hu.tvarga.sunnyeats.weather.Constants;
import hu.tvarga.sunnyeats.weather.ForecastState;
import hu.tvarga.sunnyeats.weather.R;
import hu.tvarga.sunnyeats.weather.dto.Forecast;
import hu.tvarga.sunnyeats.weather.dto.ForecastListElement;
import hu.tvarga.sunnyeats.weather.dto.ForecastWeather;
import hu.tvarga.sunnyeats.weather.service.ForecastStateInteractor;

public class ForecastViewModel extends ViewModel {

	private final ForecastLiveData forecastLiveData;

	private final LocationLiveData locationLiveData;

	private final ForecastStateInteractor forecastStateInteractor;

	private final LocaleProvider localeProvider;

	private final Strings strings;

	public ForecastViewModel(ForecastLiveData forecastLiveData,
			ForecastStateInteractor forecastStateInteractor, LocaleProvider localeProvider,
			Strings strings, LocationLiveData locationLiveData) {
		this.forecastLiveData = forecastLiveData;
		this.forecastStateInteractor = forecastStateInteractor;
		this.localeProvider = localeProvider;
		this.strings = strings;
		this.locationLiveData = locationLiveData;
	}

	public ForecastLiveData getForecastLiveData() {
		return forecastLiveData;
	}

	public LocationLiveData getLocationLiveData() {
		return locationLiveData;
	}

	public void fetchForecast(@NonNull Location location) {
		forecastStateInteractor.fetchForecast(location);
	}

	private ForecastListElement getForecastListElement(int index) {
		AsyncState<ForecastState> value = forecastLiveData.getValue();
		if (value != null && value.value().isPresent()) {
			ForecastState forecastState = value.value().get();
			if (forecastState.forecast().isPresent()) {
				Forecast forecast = forecastState.forecast().get();
				TreePVector<ForecastListElement> list = forecast.list();
				if (!list.isEmpty()) {
					return list.get(index);
				}
			}
		}
		return null;
	}

	private String getNa() {
		return strings.getString(R.string.na);
	}

	public String getDay(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return forecastListElement.timeOfData().getDayOfWeek().getDisplayName(TextStyle.FULL,
					localeProvider.getCurrentLocale());
		}
		return getNa();
	}

	public String getDescription(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			TreePVector<ForecastWeather> weathers = forecastListElement.forecastWeathers();
			if (!weathers.isEmpty()) {
				ForecastWeather forecastWeather = weathers.get(0);
				return forecastWeather.description();
			}
		}
		return getNa();
	}

	public String getTemp(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return String.format(localeProvider.getCurrentLocale(), Constants.NO_PRECISION,
					forecastListElement.forecastMain().temp());
		}
		return getNa();
	}

	public City getCity() {
		AsyncState<ForecastState> value = forecastLiveData.getValue();
		if (value != null && value.value().isPresent()) {
			ForecastState forecastState = value.value().get();
			Optional<Forecast> forecastOptional = forecastState.forecast();
			if (forecastOptional.isPresent()) {
				Forecast forecast = forecastOptional.get();
				return forecast.city();
			}
		}
		return null;
	}

	static class ForecastViewModelFactory implements ViewModelProvider.Factory {

		private final ForecastLiveData forecastLiveData;

		private final ForecastStateInteractor forecastStateInteractor;

		private final LocaleProvider localeProvider;

		private final Strings strings;

		private final LocationLiveData locationLiveData;

		@Inject
		public ForecastViewModelFactory(ForecastLiveData forecastLiveData,
				ForecastStateInteractor forecastStateInteractor, LocaleProvider localeProvider,
				Strings strings, LocationLiveData locationLiveData) {
			this.forecastLiveData = forecastLiveData;
			this.forecastStateInteractor = forecastStateInteractor;
			this.localeProvider = localeProvider;
			this.strings = strings;
			this.locationLiveData = locationLiveData;
		}

		@NonNull
		@Override
		public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
			//noinspection unchecked
			return (T) new ForecastViewModel(forecastLiveData, forecastStateInteractor,
					localeProvider, strings, locationLiveData);
		}
	}
}
