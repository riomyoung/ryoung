package com.example.pckosek.master_detailexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MainActivity extends AppCompatActivity implements CommunicatorFragment.CommunicatorFragmentInterface {

    /* ------------------------*/
    /*    member variables     */

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private NonSwipeableViewPager mViewPager;
    private RestaurantOrders mRestaurantOrders;
    private MasterFragment mMasterFragment;
    private DetailFragment mDetailFragment;

    private Gson mGson;

    private int mRestaurantItemIndex = -1;


    /* ------------------------------------------*/
    /*    LIFECYCLE METHODS                      */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar stuff
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // pull data from shared preferences
        SharedPreferences mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String storedDataString = mSettings.getString("RestaurantItems", "");

        mGson = new GsonBuilder().create();

        if ( storedDataString.equals("") ) {
            // IF : there is no data in shared prefs, make dummy data
            mRestaurantOrders = new RestaurantOrders(4);
        }
        else {
            // OTHERWISE : read string and convert it to a class object
            mRestaurantOrders = mGson.fromJson(storedDataString, RestaurantOrders.class);
        }

        // viewpager ops
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (NonSwipeableViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    /* ------------------------------------------*/
    /*    INTERFACE METHODS                      */


    // called by master fragment
    @Override
    public void notifySelected(int ItemNumber) {

        // tell system which fragment we're on
        mRestaurantItemIndex = ItemNumber;

        // update display
        mDetailFragment.updateDisplay( mRestaurantOrders.getItem(ItemNumber) );

        // switch fragments
        mViewPager.setCurrentItem(1, false);    //
    }

    // called by detail fragment
    @Override
    public void notifyUpdated(RestaurantOrder r) {

        // update the master reference
        mRestaurantOrders.setRestaurantOrder(r, mRestaurantItemIndex);

        // update shared preferences
        saveToSharedPreferences();

        // update master fragment
        mMasterFragment.updateDataSet(mRestaurantOrders);
        mMasterFragment.updateDisplay();

        // switch pages
        mViewPager.setCurrentItem(0, false);

    }

    /* ------------------------------------------*/
    /*    HELPER FUNCTIONS                       */

    public void saveToSharedPreferences() {
        SharedPreferences mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("RestaurantItems", mGson.toJson(mRestaurantOrders));
        editor.apply();
    }

    /* ------------------------------------------*/
    /*    HELPER CLASSES                         */

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0 :
                    mMasterFragment = MasterFragment.newInstance(
                            mGson.toJson(mRestaurantOrders)
                    );
                    return mMasterFragment;
                case 1 :
                    mDetailFragment = DetailFragment.newInstance();
                    return mDetailFragment;
                default :
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
