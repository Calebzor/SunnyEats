package hu.tvarga.sunnyeats.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import dagger.android.support.DaggerAppCompatActivity;
import hu.tvarga.sunnyeats.R;
import hu.tvarga.sunnyeats.common.ui.BaseFragment;
import hu.tvarga.sunnyeats.weather.ui.WeatherFragment;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class MainActivity extends DaggerAppCompatActivity {

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
}
