package com.coreman2200.presentation.rsdisplay.presenter.flat.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.coreman2200.presentation.R;
import com.coreman2200.presentation.rsdisplay.model.FloatingActionButton;
import com.coreman2200.presentation.rsdisplay.presenter.flat.fragments.FragmentDrawer;
import com.coreman2200.presentation.rsdisplay.presenter.flat.fragments.FragmentSymbolViewContent;
import com.coreman2200.presentation.rsdisplay.presenter.flat.fragments.SymbolViewTabs;


public class RingStringsActivity extends AppCompatActivity implements FragmentSymbolViewContent.OnFragmentInteractionListener, FragmentDrawer.FragmentDrawerListener
{
	public static final String TAG = "RingStringsActivity";

	private Toolbar toolbar;
	private TabLayout tabLayout;
	private ViewPager viewPager;
    private FragmentDrawer drawerFragment;
    private FloatingActionButton fab;

 	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);

		setContentView(R.layout.rssymbolview_layout);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        //drawerFragment.setDrawerListener(this);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
		setupViewPager(viewPager);

		tabLayout = (TabLayout)findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);

        fab = (FloatingActionButton)findViewById(R.id.fab_1);

  	}

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		for (SymbolViewTabs tab : SymbolViewTabs.values()) {
			adapter.addFragment(FragmentSymbolViewContent.newInstance(tab.getTagId(), tab.getLayoutForTag()), tab);
		}
		viewPager.setAdapter(adapter);
	}

    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<SymbolViewTabs> mFragmentTabList = new ArrayList<>();

		public ViewPagerAdapter(FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		public void addFragment(Fragment fragment, SymbolViewTabs tab) {
			mFragmentList.add(fragment);
			mFragmentTabList.add(tab);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Resources res = getResources();
			return mFragmentTabList.get(position).getTagName(res);
		}
	}
}