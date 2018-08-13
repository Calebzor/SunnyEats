package hu.tvarga.sunnyeats.restaurants.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import hu.tvarga.sunnyeats.common.app.state.AsyncState;
import hu.tvarga.sunnyeats.common.dto.City;
import hu.tvarga.sunnyeats.common.ui.BaseFragment;
import hu.tvarga.sunnyeats.restaurants.R;
import hu.tvarga.sunnyeats.restaurants.R2;
import hu.tvarga.sunnyeats.restaurants.RestaurantsState;
import hu.tvarga.sunnyeats.restaurants.dto.Restaurant;
import timber.log.Timber;

import static hu.tvarga.sunnyeats.common.dto.City.CITY_EXTRA_KEY;

public class RestaurantsFragment extends BaseFragment {

	@BindView(R2.id.restaurantsRecyclerView)
	RecyclerView restaurantsRecyclerView;

	@Inject
	RestaurantAdapter restaurantAdapter;

	@Inject
	RestaurantsViewModel.RestaurantsViewModelFactory restaurantsViewModelFactory;

	RestaurantsViewModel restaurantsViewModel;

	public static RestaurantsFragment create() {
		return new RestaurantsFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.searchMenuItem) {
			Timber.d("NYI");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_menu, menu);
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

		setToolbarTitle(R.string.nearby_restaurant);

		restaurantsRecyclerView.setAdapter(restaurantAdapter);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		restaurantsViewModel = ViewModelProviders.of(this, restaurantsViewModelFactory).get(
				RestaurantsViewModel.class);

		Bundle arguments = getArguments();
		if (arguments != null) {
			City city = arguments.getParcelable(CITY_EXTRA_KEY);
			restaurantsViewModel.setCity(city);
		}

		restaurantsViewModel.getRestaurantsLiveData().observe(this, this::show);
		restaurantsViewModel.fetchRestaurants();
	}

	private void show(AsyncState<RestaurantsState> restaurantsStateAsyncState) {
		if (restaurantsStateAsyncState.value().isPresent()) {
			RestaurantsState restaurantsState = restaurantsStateAsyncState.value().get();
			Optional<List<Restaurant>> restaurantsOptional = restaurantsState.restaurants();
			if (restaurantsOptional.isPresent()) {
				List<Restaurant> restaurants = restaurantsOptional.get();
				restaurantAdapter.setRestaurants(restaurants);
			}
		}
		else if (restaurantsStateAsyncState.loading()) {
			// show loading
		}
		else if (restaurantsStateAsyncState.error().isPresent()) {
			// show error
		}

	}
}
