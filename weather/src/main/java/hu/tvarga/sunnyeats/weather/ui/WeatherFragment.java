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
