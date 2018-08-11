package hu.tvarga.sunnyeats.weather.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Optional;

import javax.inject.Inject;

import butterknife.BindView;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.ui.BaseFragment;
import hu.tvarga.sunnyeats.weather.ForecastState;
import hu.tvarga.sunnyeats.weather.R;
import hu.tvarga.sunnyeats.weather.R2;
import hu.tvarga.sunnyeats.weather.dto.Forecast;

public class WeatherFragment extends BaseFragment {

	@BindView(R2.id.weatherCityName)
	TextView weatherCityName;

	@BindView(R2.id.weatherDay)
	TextView weatherDay;

	@BindView(R2.id.weatherDescription)
	TextView weatherDescription;

	@BindView(R2.id.weatherTemp)
	TextView weatherTemp;

	@BindView(R2.id.weatherHumidity)
	TextView weatherHumidity;

	@BindView(R2.id.weatherWindSpeed)
	TextView weatherWindSpeed;

	@BindView(R2.id.weatherWindDegree)
	TextView weatherWindDegree;

	@BindView(R2.id.weatherClouds)
	TextView weatherClouds;

	@BindView(R2.id.weatherTempMin)
	TextView weatherTempMin;

	@BindView(R2.id.weatherTempMax)
	TextView weatherTempMax;

	@BindView(R2.id.weatherPressure)
	TextView weatherPressure;

	@BindView(R2.id.weatherSeaLevel)
	TextView weatherSeaLevel;

	@BindView(R2.id.weatherGroundLevel)
	TextView weatherGroundLevel;

	@Inject
	ForecastViewModel.ForecastViewModelFactory forecastViewModelFactory;

	ForecastViewModel forecastViewModel;

	public static WeatherFragment create() {
		return new WeatherFragment();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.weather_fragment, container, false);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		forecastViewModel = ViewModelProviders.of(this, forecastViewModelFactory).get(
				ForecastViewModel.class);

		forecastViewModel.getForecastLiveData().observe(this, this::show);
	}

	@Override
	public void onStart() {
		super.onStart();

		forecastViewModel.fetchForecast();
	}

	private void show(AsyncState<ForecastState> forecastStateAsyncState) {
		if (forecastStateAsyncState.value().isPresent()) {
			ForecastState forecastState = forecastStateAsyncState.value().get();
			Optional<Forecast> forecastOptional = forecastState.forecast();
			if (forecastOptional.isPresent()) {
				Forecast forecast = forecastOptional.get();
				weatherCityName.setText(forecast.city().name());
				weatherDay.setText(forecastViewModel.getDay(0));
				weatherDescription.setText(forecastViewModel.getDescription(0));
				weatherTemp.setText(forecastViewModel.getTemp(0));
				weatherHumidity.setText(forecastViewModel.getHumidity(0));
				weatherWindSpeed.setText(forecastViewModel.getWindSpeed(0));
				weatherWindDegree.setText(forecastViewModel.getWindDegree(0));
				weatherClouds.setText(forecastViewModel.getClouds(0));
				weatherTempMin.setText(forecastViewModel.getTempMin(0));
				weatherTempMax.setText(forecastViewModel.getTempMax(0));
				weatherPressure.setText(forecastViewModel.getPressure(0));
				weatherSeaLevel.setText(forecastViewModel.getSeaLevel(0));
				weatherGroundLevel.setText(forecastViewModel.getGroundLevel(0));
			}
			else if (forecastStateAsyncState.loading()) {
				// show loading
			}
			else if (forecastStateAsyncState.error().isPresent()) {
				// show error
			}
		}
	}
}
