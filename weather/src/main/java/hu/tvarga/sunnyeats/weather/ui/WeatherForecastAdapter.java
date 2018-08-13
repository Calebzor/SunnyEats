package hu.tvarga.sunnyeats.weather.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.threeten.bp.format.TextStyle;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.tvarga.sunnyeats.common.app.locale.LocaleProvider;
import hu.tvarga.sunnyeats.weather.R;
import hu.tvarga.sunnyeats.weather.R2;
import hu.tvarga.sunnyeats.weather.dto.ForecastListElement;

public class WeatherForecastAdapter
		extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastItemViewHolder> {

	private final LocaleProvider localeProvider;

	private List<ForecastListElement> listElements;

	@Inject
	public WeatherForecastAdapter(LocaleProvider localeProvider) {
		this.localeProvider = localeProvider;
	}

	@NonNull
	@Override
	public WeatherForecastItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
			int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.weather_forecast_list_item, parent, false);
		return new WeatherForecastItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull WeatherForecastItemViewHolder holder, int position) {
		ForecastListElement forecastListElement = listElements.get(position);
		Context context = holder.itemView.getContext();
		Locale currentLocale = localeProvider.getCurrentLocale();
		holder.weatherListItemDay.setText(forecastListElement.timeOfData().getDayOfWeek()
				.getDisplayName(TextStyle.SHORT, currentLocale));
		holder.weatherListItemHour.setText(
				String.format(currentLocale, context.getString(R.string.weather_hour_display),
						String.valueOf(forecastListElement.timeOfData().getHour())));
		CloudIconHelper.setCloudIcon(holder.weatherListItemCloudyImage, forecastListElement);
		holder.weatherListItemTempMax.setText(
				String.format(currentLocale, context.getString(R.string.weather_degree),
						forecastListElement.forecastMain().tempMax()));
		holder.weatherListItemTempMin.setText(
				String.format(currentLocale, context.getString(R.string.weather_degree),
						forecastListElement.forecastMain().tempMin()));
	}

	@Override
	public int getItemCount() {
		if (listElements == null) {
			return 0;
		}
		return listElements.size();
	}

	public void setWeatherForecast(List<ForecastListElement> listElements) {
		this.listElements = listElements;
	}

	class WeatherForecastItemViewHolder extends RecyclerView.ViewHolder {

		@BindView(R2.id.weatherListItemDay)
		TextView weatherListItemDay;

		@BindView(R2.id.weatherListItemHour)
		TextView weatherListItemHour;

		@BindView(R2.id.weatherListItemCloudyImage)
		ImageView weatherListItemCloudyImage;

		@BindView(R2.id.weatherListItemTempMax)
		TextView weatherListItemTempMax;

		@BindView(R2.id.weatherListItemTempMin)
		TextView weatherListItemTempMin;

		public WeatherForecastItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
