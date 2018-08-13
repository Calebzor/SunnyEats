package hu.tvarga.sunnyeats.weather.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import hu.tvarga.sunnyeats.common.app.locale.LocaleProvider;
import hu.tvarga.sunnyeats.common.ui.BaseFragment;
import hu.tvarga.sunnyeats.weather.Constants;
import hu.tvarga.sunnyeats.weather.R;
import hu.tvarga.sunnyeats.weather.R2;
import hu.tvarga.sunnyeats.weather.dto.WeatherViewPagerUIObject;

public class WeatherViewPagerFragment extends BaseFragment {

	private static final String WEATHER_VIEW_PAGER_UI_OBJECT_KEY =
			"WEATHER_VIEW_PAGER_UI_OBJECT_KEY";

	@BindView(R2.id.weatherViewPagerFirstValue)
	TextView weatherViewPagerFirstValue;

	@BindView(R2.id.weatherViewPagerFirstUnit)
	TextView weatherViewPagerFirstUnit;

	@BindView(R2.id.weatherViewPagerFirstDescription)
	TextView weatherViewPagerFirstDescription;

	@BindView(R2.id.weatherViewPagerSecondValue)
	TextView weatherViewPagerSecondValue;

	@BindView(R2.id.weatherViewPagerSecondUnit)
	TextView weatherViewPagerSecondUnit;

	@BindView(R2.id.weatherViewPagerSecondDescription)
	TextView weatherViewPagerSecondDescription;

	@BindView(R2.id.weatherViewPagerThirdValue)
	TextView weatherViewPagerThirdValue;

	@BindView(R2.id.weatherViewPagerThirdUnit)
	TextView weatherViewPagerThirdUnit;

	@BindView(R2.id.weatherViewPagerThirdDescription)
	TextView weatherViewPagerThirdDescription;

	@Inject
	LocaleProvider localeProvider;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.weather_view_pager_fragment, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle arguments = getArguments();
		if (arguments != null) {
			Parcelable parcelable = arguments.getParcelable(WEATHER_VIEW_PAGER_UI_OBJECT_KEY);
			if (parcelable instanceof WeatherViewPagerUIObject) {
				WeatherViewPagerUIObject weatherViewPagerUIObject =
						(WeatherViewPagerUIObject) parcelable;
				Locale currentLocale = localeProvider.getCurrentLocale();

				weatherViewPagerFirstValue.setText(
						String.format(currentLocale, Constants.NO_PRECISION,
								weatherViewPagerUIObject.values().get(0)));
				weatherViewPagerFirstUnit.setText(weatherViewPagerUIObject.valuesUnit().get(0));
				weatherViewPagerFirstDescription.setText(
						weatherViewPagerUIObject.descriptions().get(0));

				weatherViewPagerSecondValue.setText(
						String.format(currentLocale, Constants.NO_PRECISION,
								weatherViewPagerUIObject.values().get(1)));
				weatherViewPagerSecondUnit.setText(weatherViewPagerUIObject.valuesUnit().get(1));
				weatherViewPagerSecondDescription.setText(
						weatherViewPagerUIObject.descriptions().get(1));

				weatherViewPagerThirdValue.setText(
						String.format(currentLocale, Constants.NO_PRECISION,
								weatherViewPagerUIObject.values().get(1)));
				weatherViewPagerThirdUnit.setText(weatherViewPagerUIObject.valuesUnit().get(2));
				weatherViewPagerThirdDescription.setText(
						weatherViewPagerUIObject.descriptions().get(2));
			}
		}

	}

	public static WeatherViewPagerFragment create(
			WeatherViewPagerUIObject weatherViewPagerUIObject) {

		Bundle bundle = new Bundle();
		bundle.putParcelable(WEATHER_VIEW_PAGER_UI_OBJECT_KEY, weatherViewPagerUIObject);
		WeatherViewPagerFragment weatherViewPagerFragment = new WeatherViewPagerFragment();
		weatherViewPagerFragment.setArguments(bundle);
		return weatherViewPagerFragment;
	}
}
