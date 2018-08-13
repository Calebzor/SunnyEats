package hu.tvarga.sunnyeats.restaurants.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.tvarga.sunnyeats.common.app.locale.LocaleProvider;
import hu.tvarga.sunnyeats.common.dto.Location;
import hu.tvarga.sunnyeats.restaurants.R;
import hu.tvarga.sunnyeats.restaurants.R2;
import hu.tvarga.sunnyeats.restaurants.dto.Restaurant;

public class RestaurantAdapter
		extends RecyclerView.Adapter<RestaurantAdapter.RestaurantItemViewHolder> {

	private final LocaleProvider localeProvider;

	private List<Restaurant> restaurants;

	@Inject
	public RestaurantAdapter(LocaleProvider localeProvider) {
		this.localeProvider = localeProvider;
	}

	@NonNull
	@Override
	public RestaurantItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.restaurant_list_element, parent, false);
		return new RestaurantItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RestaurantItemViewHolder holder, int position) {
		Restaurant restaurant = restaurants.get(position);
		byte[] imageData = restaurant.imageData();
		if (imageData != null) {
			holder.restaurantImage.setImageBitmap(
					BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
		}
		holder.restaurantName.setText(restaurant.name());
		holder.restaurantCity.setText(restaurant.city().name());
		float rating = restaurant.rating();
		holder.restaurantRating.setText(
				String.format(localeProvider.getCurrentLocale(), "%.1f", rating));
		ImageView[] stars = new ImageView[]{holder.restaurantStar1, holder.restaurantStar2,
				holder.restaurantStar3, holder.restaurantStar4, holder.restaurantStar5};
		int roundedRating = Math.round(rating);
		Context context = holder.itemView.getContext();
		ColorStateList colorStateList = AppCompatResources.getColorStateList(context,
				R.color.restaurant_rating);
		for (int i = 0; i < roundedRating; i++) {
			ImageViewCompat.setImageTintList(stars[i], colorStateList);
		}
		Location location = restaurant.location();
		if (location != null) {
			holder.restaurantDirection.setOnClickListener(v -> {
				// Create a Uri from an intent string. Use the result to create an Intent.
				Uri gmmIntentUri = Uri.parse(
						String.format("google.streetview:cbll=%s,%s", location.latitude(),
								location.longitude()));

				// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
				// Make the Intent explicit by setting the Google Maps package
				mapIntent.setPackage("com.google.android.apps.maps");

				// Attempt to start an activity that can handle the Intent
				context.startActivity(mapIntent);

			});
		}
	}

	@Override
	public int getItemCount() {
		if (restaurants == null) {
			return 0;
		}
		return restaurants.size();
	}

	public void setRestaurants(List<Restaurant> newRestaurants) {
		DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
			@Override
			public int getOldListSize() {
				return restaurants == null ? 0 : restaurants.size();
			}

			@Override
			public int getNewListSize() {
				return newRestaurants == null ? 0 : newRestaurants.size();
			}

			@Override
			public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
				return compareItems(oldItemPosition, newItemPosition);
			}

			@Override
			public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
				return compareItems(oldItemPosition, newItemPosition);
			}

			private boolean compareItems(int oldItemPosition, int newItemPosition) {
				// this of course could be more sophisticated
				return restaurants != null && newRestaurants != null && restaurants.get(
						oldItemPosition).equals(newRestaurants.get(newItemPosition));
			}
		});
		diffResult.dispatchUpdatesTo(this);
		this.restaurants = newRestaurants;
	}

	class RestaurantItemViewHolder extends RecyclerView.ViewHolder {

		@BindView(R2.id.restaurantImage)
		ImageView restaurantImage;

		@BindView(R2.id.restaurantName)
		TextView restaurantName;

		@BindView(R2.id.restaurantCity)
		TextView restaurantCity;

		@BindView(R2.id.restaurantRating)
		TextView restaurantRating;

		@BindView(R2.id.restaurantStar1)
		ImageView restaurantStar1;

		@BindView(R2.id.restaurantStar2)
		ImageView restaurantStar2;

		@BindView(R2.id.restaurantStar3)
		ImageView restaurantStar3;

		@BindView(R2.id.restaurantStar4)
		ImageView restaurantStar4;

		@BindView(R2.id.restaurantStar5)
		ImageView restaurantStar5;

		@BindView(R2.id.restaurantDirection)
		ImageButton restaurantDirection;

		RestaurantItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}
