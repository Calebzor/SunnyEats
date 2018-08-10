/*
 * Derived from: https://gist.github.com/baudm/1d403548f63012bc503ed61f7c4f61ca which has
 * that licence statement:
 *
 * Copyright 2016 Darwin Bautista
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.tvarga.sunnyeats.common.app.api;

import android.support.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalAccessor;
import org.threeten.bp.temporal.TemporalQuery;

import java.io.IOException;

import hu.tvarga.sunnyeats.common.app.util.StringUtils;

/**
 * Gson TypeAdapter's helper for org.threeten.bp.** classes
 */
public final class Gson310TypeAdapters {

	public static final TypeAdapter<ZoneId> ZONE_ID = new TypeAdapter<ZoneId>() {
		@Override
		public void write(JsonWriter out, ZoneId value) throws IOException {
			out.value(value.getId());
		}

		@Override
		public ZoneId read(JsonReader in) throws IOException {
			return ZoneId.of(in.nextString());
		}
	}.nullSafe();

	private Gson310TypeAdapters() {}

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T extends TemporalAccessor> TypeAdapter<T> create(Class<T> type,
			final DateTimeFormatter formatter) {
		final TemporalQuery<T> temporalType;
		try {
			temporalType = (TemporalQuery<T>) type.getField("FROM").get(null);
		}
		catch (NoSuchFieldException | IllegalAccessException e) {
			return null;
		}
		return new TypeAdapter<T>() {
			@Override
			public void write(JsonWriter out, T value) throws IOException {
				out.value(formatter.format(value));
			}

			@Override
			public T read(JsonReader in) throws IOException {
				String str = in.nextString();
				if (StringUtils.isNullOrEmpty(str)) {
					return null;
				}
				return formatter.parse(str, temporalType);
			}
		}.nullSafe();
	}

	public static <T extends TemporalAccessor> void register(GsonBuilder builder, Class<T> type,
			DateTimeFormatter formatter) {
		builder.registerTypeAdapter(type, create(type, formatter));
	}
}
