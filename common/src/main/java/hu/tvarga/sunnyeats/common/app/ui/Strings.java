package hu.tvarga.sunnyeats.common.app.ui;

import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;

public interface Strings {

	String getString(@StringRes int resId);

	String getString(@StringRes int resId, Object... formatArgs);

	String[] getStringArray(@ArrayRes int resId);
}