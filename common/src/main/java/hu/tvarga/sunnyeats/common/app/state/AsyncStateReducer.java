package hu.tvarga.sunnyeats.common.app.state;

// When Reductor 0.15.0 gets released, add Reducer interface here and remove it
// from its child classes, fixing PR: https://github.com/Yarikx/reductor/pull/36
@SuppressWarnings("squid:S1172")
public abstract class AsyncStateReducer<T> {

	protected AsyncState<T> startFetch(AsyncState<T> state) {
		return state.withLoading();
	}

	protected AsyncState<T> succeedFetch(AsyncState<T> state, T obj) {
		return AsyncState.createValue(obj);
	}

	protected AsyncState<T> failFetch(AsyncState<T> state, Exception error) {
		return state.withError(error);
	}

}
