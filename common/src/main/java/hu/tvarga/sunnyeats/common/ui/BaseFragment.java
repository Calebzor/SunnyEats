package hu.tvarga.sunnyeats.common.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.DaggerFragment;
import hu.tvarga.sunnyeats.common.app.ui.Screen;

/**
 * This helps us with view binding and class injection
 */
public class BaseFragment extends DaggerFragment {

	private Unbinder unbinder;

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		unbinder = ButterKnife.bind(this, view);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (unbinder != null) {
			unbinder.unbind();
		}
	}

	protected void openScreen(Screen screen, Bundle extra) {
		FragmentActivity activity = getActivity();
		if (activity instanceof FragmentNavigator) {
			((FragmentNavigator) activity).openScreen(screen, extra);
		}
	}

	protected void replaceFragment(BaseFragment baseFragment) {
		FragmentActivity activity = getActivity();
		if (activity instanceof FragmentNavigator) {
			((FragmentNavigator) activity).replaceFragment(baseFragment);
		}
	}

	protected void setToolbarTitle(@StringRes int titleStringRes) {
		FragmentActivity activity = getActivity();
		if (activity instanceof DaggerAppCompatActivity) {
			ActionBar supportActionBar = ((DaggerAppCompatActivity) activity).getSupportActionBar();
			if (supportActionBar != null) {
				supportActionBar.setTitle(titleStringRes);
			}
		}
	}

	public interface FragmentNavigator {

		void replaceFragment(BaseFragment baseFragment);

		void openScreen(Screen screen, Bundle extra);
	}
}
