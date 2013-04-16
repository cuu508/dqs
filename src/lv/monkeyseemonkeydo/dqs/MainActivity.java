package lv.monkeyseemonkeydo.dqs;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(29);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
	 * sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private Date mToday = new Date();

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new ChartFragment();
			
			Calendar c = Calendar.getInstance();
			c.setTime(mToday);
			c.add(Calendar.DATE, position - 29);

			String formatted = DateFormat.format("yyyyMMdd", c.getTime()).toString();
			
			Bundle args = new Bundle();
			args.putString(ChartFragment.ARG_DATE, formatted);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 30;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Calendar c = Calendar.getInstance();
			c.setTime(mToday);
			c.add(Calendar.DATE, position - 29);

			return DateFormat.format("E, MMMM d", c.getTime()).toString();
		}
	}

}
