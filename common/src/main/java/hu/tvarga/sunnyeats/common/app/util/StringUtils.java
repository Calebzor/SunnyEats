package hu.tvarga.sunnyeats.common.app.util;

/**
 * Helper class for String related methods.
 */
public class StringUtils {

	private StringUtils() {
		// hiding constructor
	}

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	public static boolean isNullOrEmptyTrimmed(String s) {
		return s == null || s.trim().isEmpty();
	}

}