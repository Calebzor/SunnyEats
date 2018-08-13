package hu.tvarga.sunnyeats.app.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.tvarga.sunnyeats.common.R;
import hu.tvarga.sunnyeats.common.R2;
import hu.tvarga.sunnyeats.common.app.util.StringUtils;

public class BackgroundLoadingIndicator extends LinearLayout {

	@BindView(R2.id.backgroundLoadingIndicatorText)
	TextView backgroundLoadingIndicatorText;

	@BindView(R2.id.backgroundLoadingIndicatorProgressBar)
	ProgressBar backgroundLoadingIndicatorProgressBar;

	private View rootView;

	public BackgroundLoadingIndicator(Context context) {
		super(context);
		rootView = inflate(getContext(), R.layout.background_loading_indicator, this);
		init();
	}

	public BackgroundLoadingIndicator(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		rootView = inflate(getContext(), R.layout.background_loading_indicator, this);
		init();
	}

	public BackgroundLoadingIndicator(Context context, @Nullable AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		rootView = inflate(getContext(), R.layout.background_loading_indicator, this);
		init();
	}

	@SuppressLint("NewApi")
	private void init() {
		ViewCompat.setElevation(this, 0);
		// For LOLLIPOP it is not possible to remove the stateListAnimator of a disabled button
		// (enabled=false in xml). It will crash the app. The check can be removed as soon as
		// minSdkVersion is MARSHMALLOW.
		if (isEnabled() || Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			// Removes shadow
			setStateListAnimator(null);
		}

		ButterKnife.bind(this);
	}

	public void setText(String text) {
		if (StringUtils.isNullOrEmpty(text)) {
			backgroundLoadingIndicatorText.setVisibility(GONE);
			return;
		}
		backgroundLoadingIndicatorText.setVisibility(VISIBLE);
		backgroundLoadingIndicatorText.setText(text);
	}

	public void setText(@StringRes int text) {
		if (text == 0) {
			backgroundLoadingIndicatorText.setVisibility(GONE);
			return;
		}
		backgroundLoadingIndicatorText.setVisibility(VISIBLE);
		backgroundLoadingIndicatorText.setText(getContext().getString(text));
	}

	public void hideLoadingIndicator() {
		backgroundLoadingIndicatorProgressBar.setVisibility(GONE);
	}

	public void hide() {
		rootView.setVisibility(GONE);
	}

	public void show() {
		backgroundLoadingIndicatorProgressBar.setVisibility(VISIBLE);
		rootView.setVisibility(VISIBLE);
	}

	public void setColor(int color) {
		backgroundLoadingIndicatorText.setTextColor(ContextCompat.getColor(getContext(), color));
		backgroundLoadingIndicatorProgressBar.getIndeterminateDrawable().setColorFilter(
				ContextCompat.getColor(getContext(), color),
				android.graphics.PorterDuff.Mode.MULTIPLY);
	}
}
