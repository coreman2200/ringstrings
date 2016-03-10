package com.coreman2200.ringstrings.rsdisplay.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

//import java.util.GregorianCalendar;

import android.app.Activity;
//import android.opengl.GLSurfaceView.GLWrapper;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
//import javax.microedition.khronos.opengles.GL;
//import android.util.Log;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import android.os.AsyncTask;

import com.coreman2200.ringstrings.R;
import com.coreman2200.ringstrings.rsdisplay.fragments.SymbolViewContentFragment;
import com.coreman2200.ringstrings.rsdisplay.fragments.SymbolViewTabs;


public class RingStringsActivity extends AppCompatActivity implements SymbolViewContentFragment.OnFragmentInteractionListener
{
	private Toolbar toolbar;
	private TabLayout tabLayout;
	private ViewPager viewPager;

 	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);

		setContentView(R.layout.rssymbolview_layout);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
		setupViewPager(viewPager);

		tabLayout = (TabLayout)findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewPager);
  	}

	private void setupViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		for (SymbolViewTabs tab : SymbolViewTabs.values()) {
			adapter.addFragment(new SymbolViewContentFragment(), tab);
		}
		viewPager.setAdapter(adapter);
	}

    public void onFragmentInteraction(Uri uri) {
        return;
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