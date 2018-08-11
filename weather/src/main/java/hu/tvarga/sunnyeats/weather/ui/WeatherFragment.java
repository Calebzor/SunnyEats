package hu.tvarga.sunnyeats.weather.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.ui.Screen;
import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.common.ui.BaseFragment;
import hu.tvarga.sunnyeats.weather.ForecastState;
import hu.tvarga.sunnyeats.weather.R;
import hu.tvarga.sunnyeats.weather.R2;
import hu.tvarga.sunnyeats.weather.dto.Forecast;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

import static hu.tvarga.sunnyeats.common.dto.City.CITY_EXTRA_KEY;

public class WeatherFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

	public static final int REQUEST_CODE_PERMISSIONS = 1;

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

	private AlertDialog alert;

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

		startDeviceLocationTracking();

		forecastViewModel = ViewModelProviders.of(this, forecastViewModelFactory).get(
				ForecastViewModel.class);

		forecastViewModel.getForecastLiveData().observe(this, this::show);
		forecastViewModel.getLocationLiveData().observe(this, this::onLocationUpdate);
	}

	private void onLocationUpdate(Location location) {
		if (location != null) {
			forecastViewModel.fetchForecast(location);
		}
	}

	@AfterPermissionGranted(REQUEST_CODE_PERMISSIONS)
	void startDeviceLocationTracking() {
		String[] perms = getPermissions();
		if (EasyPermissions.hasPermissions(requireContext(), perms)) {
			startTracking();
		}
		else {
			EasyPermissions.requestPermissions(this,
					getString(R.string.weather_locationPermission_rational),
					REQUEST_CODE_PERMISSIONS, perms);
		}
	}

	public static String[] getPermissions() {
		return new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
	}

	private void startTracking() {
		LocationManager manager = (LocationManager) requireContext().getSystemService(
				Context.LOCATION_SERVICE);

		if (manager != null && !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();
		}
		else {
			if (alert != null) {
				alert.dismiss();
			}

			FusedLocationProviderClient fusedLocationProviderClient =
					LocationServices.getFusedLocationProviderClient(requireActivity());
			try {
				// for our use case just getting the last location is fine
				fusedLocationProviderClient.getLastLocation().addOnSuccessListener(
						requireActivity(), location -> {
							if (location != null) {
								forecastViewModel.getLocationLiveData().setValue(location);
							}
						});
			}
			catch (SecurityException unlikely) {
				Timber.e(unlikely, "Lost location permission.");
			}
		}
	}

	private void buildAlertMessageNoGps() {
		FragmentActivity activity = getActivity();
		if (alert != null || activity == null) {
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(R.string.weather_gpsDisabledDialog_rational).setCancelable(false)
				.setPositiveButton(R.string.weather_gpsDisabledDialog_button_goToSettings,
						(dialog, id) -> startActivity(new Intent(
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
		alert = builder.create();
		alert.show();
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

	@OnClick(R2.id.weatherPopularRestaurantsButton)
	public void weatherPopularRestaurantsButtonOnClick() {
		City city = forecastViewModel.getCity();
		if (city != null) {
			Bundle extra = new Bundle();
			extra.putParcelable(CITY_EXTRA_KEY, city);
			openScreen(Screen.RESTAURANTS, extra);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
			@NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		// Forward results to EasyPermissions
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@Override
	public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
		if (requestCode == REQUEST_CODE_PERMISSIONS) {
			startTracking();
		}
	}

	@Override
	public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
		if (requestCode == REQUEST_CODE_PERMISSIONS) {
			FragmentActivity activity = getActivity();
			if (activity != null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage(R.string.weather_locationDialog_permissionDeniedRational);
				builder.setPositiveButton(android.R.string.ok,
						(dialog, which) -> activity.finish());
				builder.create().show();
			}
		}
	}
}
