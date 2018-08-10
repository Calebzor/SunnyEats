package hu.tvarga.sunnyeats.common.app.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

	private static Gson prettyGson;

	private static Gson gson;

	private GsonHelper() {
		// hiding constructor of static helper class
	}

	public static Gson getPrettyGson() {
		if (prettyGson == null) {
			prettyGson = new GsonBuilder().setPrettyPrinting().create();
		}
		return prettyGson;
	}

	public static Gson get() {
		if (gson == null) {
			gson = new GsonBuilder().create();
		}
		return gson;
	}

}
