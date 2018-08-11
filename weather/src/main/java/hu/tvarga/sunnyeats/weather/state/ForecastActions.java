package hu.tvarga.sunnyeats.weather.state;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

import hu.tvarga.sunnyeats.weather.ForecastState;

@SuppressWarnings("squid:S1214")
@ActionCreator
public interface ForecastActions {

	String START_FORECAST_FETCH = "START_FORECAST_FETCH";

	String SUCCEED_FORECAST_FETCH = "SUCCEED_FORECAST_FETCH";

	String FAIL_FORECAST_FETCH = "FAIL_FORECAST_FETCH";

	@ActionCreator.Action(START_FORECAST_FETCH)
	Action startForecastFetch();

	@ActionCreator.Action(SUCCEED_FORECAST_FETCH)
	Action succeedForecastFetch(ForecastState forecastState);

	@ActionCreator.Action(FAIL_FORECAST_FETCH)
	Action failForecastFetch(Exception error);
}
