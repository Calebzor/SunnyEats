package hu.tvarga.sunnyeats.weather.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import hu.tvarga.sunnyeats.weather.R;
import hu.tvarga.sunnyeats.weather.dto.ForecastListElement;
import hu.tvarga.sunnyeats.weather.dto.WeatherViewPagerUIObject;

public class WeatherViewPagerAdapter extends FragmentStatePagerAdapter {

	private static final int PAGE_COUNT = 3;

	private ForecastListElement forecastListElement;

	public WeatherViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		WeatherViewPagerUIObject weatherViewPagerUIObject = getWeatherViewPagerUIObject(position);
		return WeatherViewPagerFragment.create(weatherViewPagerUIObject);
	}

	private WeatherViewPagerUIObject getWeatherViewPagerUIObject(int position) {
		List<BigDecimal> values = new ArrayList<>(PAGE_COUNT);
		List<Integer> valuesUnit = new ArrayList<>(PAGE_COUNT);
		List<Integer> description = new ArrayList<>(PAGE_COUNT);
		switch (position) {
			case 0:
				values.add(new BigDecimal(forecastListElement.forecastMain().humidity()));
				valuesUnit.add(R.string.weather_percentage);
				description.add(R.string.weather_humidity);

				values.add(forecastListElement.forecastWind().speed());
				valuesUnit.add(R.string.weather_unit_wind_speed);
				description.add(R.string.weather_wind);

				values.add(forecastListElement.forecastWind().deg());
				valuesUnit.add(R.string.weather_unit_wind_degree);
				description.add(R.string.weather_wind);
				break;
			case 1:
				values.add(new BigDecimal(forecastListElement.forecastClouds().all()));
				valuesUnit.add(R.string.weather_percentage);
				description.add(R.string.weather_clouds);

				values.add(forecastListElement.forecastMain().tempMin());
				valuesUnit.add(R.string.weather_degree_c);
				description.add(R.string.weather_min);

				values.add(forecastListElement.forecastMain().tempMax());
				valuesUnit.add(R.string.weather_degree_c);
				description.add(R.string.weather_max);
				break;
			case 2:
			default:
				values.add(forecastListElement.forecastMain().pressure());
				valuesUnit.add(R.string.weather_unit_hpa);
				description.add(R.string.weather_pressure);

				values.add(forecastListElement.forecastMain().seaLevel());
				valuesUnit.add(R.string.weather_unit_hpa);
				description.add(R.string.weather_sea_level);

				values.add(forecastListElement.forecastMain().groundLevel());
				valuesUnit.add(R.string.weather_unit_hpa);
				description.add(R.string.weather_ground_level);
				break;
		}
		return WeatherViewPagerUIObject.create(values, valuesUnit, description);
	}

	@Override
	public int getCount() {
		return forecastListElement == null ? 0 : PAGE_COUNT;
	}

	public void setForecastListElement(ForecastListElement forecastListElement) {
		this.forecastListElement = forecastListElement;
	}
}
