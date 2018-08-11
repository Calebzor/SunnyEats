package hu.tvarga.sunnyeats.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.transition.Slide;

import dagger.android.support.DaggerAppCompatActivity;
import hu.tvarga.sunnyeats.R;
import hu.tvarga.sunnyeats.app.ui.ScreenToFragmentMapper;
import hu.tvarga.sunnyeats.common.app.ui.Screen;
import hu.tvarga.sunnyeats.common.ui.BaseFragment;
import hu.tvarga.sunnyeats.weather.ui.WeatherFragment;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class MainActivity extends DaggerAppCompatActivity
		implements BaseFragment.FragmentNavigator {

	public static final int FRAGMENT_CONTAINER_ID = R.id.fragmentContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			setInitialFragment(WeatherFragment.create());
		}
	}

	protected void setInitialFragment(BaseFragment fragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(FRAGMENT_CONTAINER_ID, fragment);
		ft.addToBackStack(fragment.getClass().getName()).commit();
	}

	@Override
	public void replaceFragment(BaseFragment fragment) {
		replaceFragment(fragment, true);
	}

	@Override
	public void openScreen(Screen screen, Bundle extra) {
		BaseFragment fragmentForScreen = ScreenToFragmentMapper.getFragmentForScreen(screen);
		fragmentForScreen.setArguments(extra);
		replaceFragment(fragmentForScreen);
	}

	public void replaceFragment(BaseFragment fragment, boolean addToBackStack) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			Slide transition = new Slide();
			fragment.setEnterTransition(transition);
			fragment.setExitTransition(transition);
		}
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(FRAGMENT_CONTAINER_ID, fragment);
		if (addToBackStack) {
			fragmentTransaction.addToBackStack(fragment.getClass().getName());
		}
		fragmentTransaction.commit();
	}
}
