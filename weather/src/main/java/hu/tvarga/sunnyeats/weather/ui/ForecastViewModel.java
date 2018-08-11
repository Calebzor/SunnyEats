package hu.tvarga.sunnyeats.weather.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import org.pcollections.TreePVector;
import org.threeten.bp.format.TextStyle;

import javax.inject.Inject;

import hu.tvarga.sunnyeats.common.app.locale.LocaleProvider;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.ui.Strings;
import hu.tvarga.sunnyeats.weather.ForecastState;
import hu.tvarga.sunnyeats.weather.R;
import hu.tvarga.sunnyeats.weather.dto.Forecast;
import hu.tvarga.sunnyeats.weather.dto.ForecastListElement;
import hu.tvarga.sunnyeats.weather.dto.ForecastWeather;
import hu.tvarga.sunnyeats.weather.service.ForecastStateInteractor;

public class ForecastViewModel extends ViewModel {

	private final ForecastLiveData forecastLiveData;

	private final ForecastStateInteractor forecastStateInteractor;

	private final LocaleProvider localeProvider;

	private final Strings strings;

	public ForecastViewModel(ForecastLiveData forecastLiveData,
			ForecastStateInteractor forecastStateInteractor, LocaleProvider localeProvider,
			Strings strings) {
		this.forecastLiveData = forecastLiveData;
		this.forecastStateInteractor = forecastStateInteractor;
		this.localeProvider = localeProvider;
		this.strings = strings;
	}

	public ForecastLiveData getForecastLiveData() {
		return forecastLiveData;
	}

	public void fetchForecast() {
		forecastStateInteractor.fetchForecast();
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
			return strings.getString(R.string.weather_unit_celsius,
					forecastListElement.forecastMain().temp());
		}
		return getNa();
	}

	public String getHumidity(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return String.format("%s%%", forecastListElement.forecastMain().humidity());
		}
		return getNa();
	}

	public String getWindSpeed(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return strings.getString(R.string.weather_unit_wind_speed,
					forecastListElement.forecastWind().speed());
		}
		return getNa();
	}

	public String getWindDegree(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return strings.getString(R.string.weather_unit_wind_degree,
					forecastListElement.forecastWind().deg());
		}
		return getNa();
	}

	public String getClouds(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return String.format("%s%%", forecastListElement.forecastClouds().all());
		}
		return getNa();
	}

	public String getTempMin(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return strings.getString(R.string.weather_unit_celsius,
					forecastListElement.forecastMain().tempMin());
		}
		return getNa();
	}

	public String getTempMax(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return strings.getString(R.string.weather_unit_celsius,
					forecastListElement.forecastMain().tempMax());
		}
		return getNa();
	}

	public String getPressure(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return strings.getString(R.string.weather_unit_hpa,
					forecastListElement.forecastMain().pressure());
		}
		return getNa();
	}

	public String getSeaLevel(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return strings.getString(R.string.weather_unit_hpa,
					forecastListElement.forecastMain().seaLevel());
		}
		return getNa();
	}

	public String getGroundLevel(int index) {
		ForecastListElement forecastListElement = getForecastListElement(index);
		if (forecastListElement != null) {
			return strings.getString(R.string.weather_unit_hpa,
					forecastListElement.forecastMain().groundLevel());
		}
		return getNa();
	}

	static class ForecastViewModelFactory implements ViewModelProvider.Factory {

		private final ForecastLiveData forecastLiveData;

		private final ForecastStateInteractor forecastStateInteractor;

		private final LocaleProvider localeProvider;

		private final Strings strings;

		@Inject
		public ForecastViewModelFactory(ForecastLiveData forecastLiveData,
				ForecastStateInteractor forecastStateInteractor, LocaleProvider localeProvider,
				Strings strings) {
			this.forecastLiveData = forecastLiveData;
			this.forecastStateInteractor = forecastStateInteractor;
			this.localeProvider = localeProvider;
			this.strings = strings;
		}

		@NonNull
		@Override
		public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
			//noinspection unchecked
			return (T) new ForecastViewModel(forecastLiveData, forecastStateInteractor,
					localeProvider, strings);
		}
	}
}
