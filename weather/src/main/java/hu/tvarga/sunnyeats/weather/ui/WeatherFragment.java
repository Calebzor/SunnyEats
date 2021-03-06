package hu.tvarga.sunnyeats.weather.ui;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import hu.tvarga.sunnyeats.app.layout.BackgroundLoadingIndicator;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.app.ui.Screen;
import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.common.ui.BaseFragment;
import hu.tvarga.sunnyeats.weather.ForecastState;
import hu.tvarga.sunnyeats.weather.R;
import hu.tvarga.sunnyeats.weather.R2;
import hu.tvarga.sunnyeats.weather.dto.Forecast;
import hu.tvarga.sunnyeats.weather.dto.ForecastListElement;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

import static hu.tvarga.sunnyeats.common.dto.City.CITY_EXTRA_KEY;

public class WeatherFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

	public static final int REQUEST_CODE_PERMISSIONS = 1;

	@BindView(R2.id.weatherPopularRestaurantsButton)
	Button weatherPopularRestaurantsButton;

	@BindView(R2.id.weatherCityName)
	TextView weatherCityName;

	@BindView(R2.id.weatherDay)
	TextView weatherDay;

	@BindView(R2.id.weatherDescription)
	TextView weatherDescription;

	@BindView(R2.id.weatherTemp)
	android.support.v7.widget.AppCompatTextView weatherTemp;

	@BindView(R2.id.weatherTempUnit)
	TextView weatherTempUnit;

	@BindView(R2.id.weatherCloudsImage)
	ImageView weatherCloudsImage;

	@BindView(R2.id.weatherViewPager)
	ViewPager weatherViewPager;

	@BindView(R2.id.weatherForecastRecyclerView)
	RecyclerView weatherForecastRecyclerView;

	@BindView(R2.id.weatherLoadingIndicator)
	BackgroundLoadingIndicator weatherLoadingIndicator;

	@Inject
	WeatherForecastAdapter weatherForecastAdapter;

	@Inject
	ForecastViewModel.ForecastViewModelFactory forecastViewModelFactory;

	ForecastViewModel forecastViewModel;

	private AlertDialog alert;

	private WeatherViewPagerAdapter weatherViewPagerAdapter;

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
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		weatherForecastRecyclerView.setAdapter(weatherForecastAdapter);
		if (weatherViewPagerAdapter == null) {
			weatherViewPagerAdapter = new WeatherViewPagerAdapter(getChildFragmentManager());
		}
		weatherViewPager.setAdapter(weatherViewPagerAdapter);
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
				new Handler().postDelayed(this::handlePotentialMissingLocation, 10000);
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

	private void handlePotentialMissingLocation() {
		Location value = forecastViewModel.getLocationLiveData().getValue();
		if (value == null) {
			showError();
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
				weatherLoadingIndicator.hide();
				weatherPopularRestaurantsButton.setVisibility(View.VISIBLE);
				Forecast forecast = forecastOptional.get();
				ForecastListElement forecastListElementFirst = forecast.list().get(0);
				CloudIconHelper.setCloudIcon(weatherCloudsImage, forecastListElementFirst);
				weatherCityName.setText(forecast.city().name());
				weatherDay.setText(forecastViewModel.getDay(0));
				weatherDescription.setText(forecastViewModel.getDescription(0));
				weatherTemp.setText(forecastViewModel.getTemp(0));
				weatherTempUnit.setText(getString(R.string.weather_degree_c));
				weatherForecastAdapter.setWeatherForecast(forecast.list());
				weatherForecastAdapter.notifyDataSetChanged();
				weatherViewPagerAdapter.setForecastListElement(forecastListElementFirst);
				weatherViewPagerAdapter.notifyDataSetChanged();
			}
			else if (forecastStateAsyncState.loading()) {
				weatherLoadingIndicator.setText(R.string.weather_loading);
				weatherLoadingIndicator.show();
			}
			else if (forecastStateAsyncState.error().isPresent()) {
				showError();
			}
		}
	}

	private void showError() {
		weatherLoadingIndicator.setText(R.string.weather_error);
		weatherLoadingIndicator.hideLoadingIndicator();
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
