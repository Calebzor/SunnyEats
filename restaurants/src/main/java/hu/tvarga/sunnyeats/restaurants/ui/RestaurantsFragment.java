package hu.tvarga.sunnyeats.restaurants.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.common.ui.BaseFragment;
import hu.tvarga.sunnyeats.restaurants.R;
import hu.tvarga.sunnyeats.restaurants.R2;

import static hu.tvarga.sunnyeats.common.dto.City.CITY_EXTRA_KEY;

public class RestaurantsFragment extends BaseFragment {

	@BindView(R2.id.weatherCityName)
	TextView weatherCityName;

	public static RestaurantsFragment create() {
		return new RestaurantsFragment();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.restaurants_fragment, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Bundle arguments = getArguments();
		if (arguments != null) {
			City city = arguments.getParcelable(CITY_EXTRA_KEY);
			if (city != null) {
				weatherCityName.setText(city.name());
			}
		}
	}
}
