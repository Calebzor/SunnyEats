package hu.tvarga.sunnyeats.common.app.state;

import com.annimon.stream.Optional;
import com.google.auto.value.AutoValue;

@AutoValue
@SuppressWarnings("squid:S1610") // auto value only works on classes
public abstract class AsyncState<T> {

	public static <T> AsyncState<T> createLoading() {
		return new AutoValue_AsyncState<>(true, Optional.<Exception>empty(), Optional.<T>empty());
	}

	public static <T> AsyncState<T> createError(Exception error) {
		return new AutoValue_AsyncState<>(false, Optional.of(error), Optional.<T>empty());
	}

	public static <T> AsyncState<T> createValue(T value) {
		return new AutoValue_AsyncState<>(false, Optional.<Exception>empty(), Optional.of(value));
	}

	public static <T> AsyncState<T> createEmpty() {
		return new AutoValue_AsyncState<>(false, Optional.<Exception>empty(), Optional.<T>empty());
	}

	public <T2> AsyncState<T2> withValue(T2 value) {
		return new AutoValue_AsyncState<>(loading(), error(), Optional.of(value));
	}

	public <T2> AsyncState<T2> withEmptyValue() {
		return new AutoValue_AsyncState<>(loading(), error(), Optional.<T2>empty());
	}

	public AsyncState<T> withLoading() {
		return new AutoValue_AsyncState<>(true, error(), value());
	}

	public AsyncState<T> withError(Exception error) {
		return new AutoValue_AsyncState<>(false, Optional.of(error), value());
	}

	public abstract boolean loading();

	public abstract Optional<Exception> error();

	public abstract Optional<T> value();

}
