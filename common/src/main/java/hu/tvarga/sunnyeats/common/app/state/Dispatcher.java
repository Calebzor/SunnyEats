package hu.tvarga.sunnyeats.common.app.state;

import com.yheriatovych.reductor.Action;

public interface Dispatcher {

	void dispatch(Action action);
}