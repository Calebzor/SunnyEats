package hu.tvarga.sunnyeats.restaurants.state;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

import hu.tvarga.sunnyeats.restaurants.RestaurantsState;

@SuppressWarnings("squid:S1214")
@ActionCreator
public interface RestaurantsActions {

	String START_RESTAURANTS_FETCH = "START_RESTAURANTS_FETCH";

	String SUCCEED_RESTAURANTS_FETCH = "SUCCEED_RESTAURANTS_FETCH";

	String FAIL_RESTAURANTS_FETCH = "FAIL_RESTAURANTS_FETCH";

	@ActionCreator.Action(START_RESTAURANTS_FETCH)
	Action startRestaurantsFetch();

	@ActionCreator.Action(SUCCEED_RESTAURANTS_FETCH)
	Action succeedRestaurantsFetch(RestaurantsState restaurantsState);

	@ActionCreator.Action(FAIL_RESTAURANTS_FETCH)
	Action failRestaurantsFetch(Exception error);
}
