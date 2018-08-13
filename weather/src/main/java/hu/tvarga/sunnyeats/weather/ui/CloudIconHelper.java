package hu.tvarga.sunnyeats.weather.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import hu.tvarga.sunnyeats.weather.R;
import hu.tvarga.sunnyeats.weather.dto.ForecastListElement;

public final class CloudIconHelper {

	private CloudIconHelper() {
		// hiding constructor of static helper class
	}

	public static void setCloudIcon(@NonNull ImageView imageView,
			@NonNull ForecastListElement forecastListElement) {
		Context context = imageView.getContext();
		Drawable[] cloudIcons = new Drawable[]{ContextCompat.getDrawable(context, R.drawable.sunny),
				ContextCompat.getDrawable(context, R.drawable.sunny1), ContextCompat.getDrawable(
				context, R.drawable.sunny2), ContextCompat.getDrawable(context, R.drawable.sunny3)};
		Integer all = forecastListElement.forecastClouds().all();
		int positionToUse = Math.min(Math.round(all / (100f / (float) cloudIcons.length)),
				cloudIcons.length - 1);
		imageView.setImageDrawable(cloudIcons[positionToUse]);
	}
}
