package com.nautilus.omni.appmodules.navigationdrawer;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.awards.view.AwardsFragment;
import com.nautilus.omni.appmodules.goals.view.GoalsFragment;
import com.nautilus.omni.appmodules.home.view.HomeFragment;
import com.nautilus.omni.appmodules.journal.view.JournalFragment;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class NavigationDrawerHelper {
    public static final int AWARDS_INDEX = 2;
    public static final int GOALS_INDEX = 1;
    public static final int HOME_INDEX = 0;
    public static final int JOURNAL_INDEX = 3;
    private DrawerLayout mDrawer;
    private ArrayList<ObjectDrawerItem> mDrawerItemsArray = new ArrayList<>();
    public ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView.LayoutManager mLayoutManager;
    /* access modifiers changed from: private */
    public MainActivity mMainActivity;
    private WeakReference<MainActivity> mMainActivityReference;
    private NavigationDrawerAdapter mNavigationDrawerAdapter;
    private TypedArray mNavigationDrawerItemActiveIcons;
    private TypedArray mNavigationDrawerItemIcons;
    private String[] mNavigationDrawerItemTitles;
    public RecyclerView mNavigationDrawerList;
    private Resources mResources;

    public NavigationDrawerHelper(MainActivity mainActivity) {
        this.mMainActivityReference = new WeakReference<>(mainActivity);
        this.mMainActivity = (MainActivity) this.mMainActivityReference.get();
        this.mResources = this.mMainActivity.getResources();
    }

    public void initNavigationDrawer() {
        this.mNavigationDrawerItemTitles = this.mResources.getStringArray(R.array.navigation_drawer_items_names_array);
        this.mNavigationDrawerItemIcons = this.mResources.obtainTypedArray(R.array.navigation_drawer_items_icons_array);
        this.mNavigationDrawerItemActiveIcons = this.mResources.obtainTypedArray(R.array.navigation_drawer_items_active_icons_array);
        this.mLayoutManager = new LinearLayoutManager(this.mMainActivity);
        this.mNavigationDrawerList = (RecyclerView) this.mMainActivity.findViewById(R.id.navigation_drawer_recycler_view);
        this.mNavigationDrawerList.setLayoutManager(this.mLayoutManager);
        setNavigationDrawerItemsArray();
        this.mNavigationDrawerAdapter = new NavigationDrawerAdapter(this.mMainActivity.getApplicationContext(), this.mDrawerItemsArray, this.mMainActivity);
        this.mNavigationDrawerList.setAdapter(this.mNavigationDrawerAdapter);
        initDrawerLayout();
        this.mMainActivity.mCurrentNavDrawerItem = 0;
    }

    private void setNavigationDrawerItemsArray() {
        for (int currentItemCounter = 0; currentItemCounter < this.mNavigationDrawerItemTitles.length; currentItemCounter++) {
            this.mDrawerItemsArray.add(new ObjectDrawerItem(this.mNavigationDrawerItemIcons.getResourceId(currentItemCounter, -1), this.mNavigationDrawerItemActiveIcons.getResourceId(currentItemCounter, -1), this.mNavigationDrawerItemTitles[currentItemCounter]));
        }
    }

    private void initDrawerLayout() {
        this.mDrawer = (DrawerLayout) this.mMainActivity.findViewById(R.id.drawer_layout);
        this.mDrawerToggle = new ActionBarDrawerToggle(this.mMainActivity, this.mDrawer, this.mMainActivity.mToolbar, 0, 0) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (NavigationDrawerHelper.this.mMainActivity.mCurrentNavDrawerItem != 0) {
                    NavigationDrawerHelper.this.restorePreviousNavDrawerState(NavigationDrawerHelper.this.mMainActivity.mCurrentNavDrawerItem, NavigationDrawerHelper.this.mNavigationDrawerList.getChildAt(NavigationDrawerHelper.this.mMainActivity.mCurrentNavDrawerItem));
                }
            }
        };
        this.mDrawer.addDrawerListener(this.mDrawerToggle);
        this.mDrawerToggle.syncState();
    }

    public void restorePreviousNavDrawerState(int position, View view) {
        switch (position) {
            case 0:
                updateSelectedMenu(HomeFragment.TAG, view);
                this.mMainActivity.mCurrentNavDrawerItem = 0;
                return;
            case 1:
                updateSelectedMenu(GoalsFragment.TAG, view);
                this.mMainActivity.mCurrentNavDrawerItem = 1;
                return;
            case 2:
                updateSelectedMenu(AwardsFragment.TAG, view);
                this.mMainActivity.mCurrentNavDrawerItem = 2;
                return;
            case 3:
                updateSelectedMenu(JournalFragment.TAG, view);
                this.mMainActivity.mCurrentNavDrawerItem = 3;
                return;
            default:
                return;
        }
    }

    public void onDrawerItemClicked(View view, int position) {
        switch (position) {
            case 0:
                this.mMainActivity.changeFragmentWithTitle(this.mMainActivity.getString(R.string.home_screen_title), HomeFragment.TAG, false);
                updateSelectedMenu(HomeFragment.TAG, view);
                this.mMainActivity.mCurrentNavDrawerItem = 0;
                return;
            case 1:
                this.mMainActivity.changeFragmentWithTitle(this.mNavigationDrawerItemTitles[position], GoalsFragment.TAG, false);
                updateSelectedMenu(GoalsFragment.TAG, view);
                this.mMainActivity.mCurrentNavDrawerItem = 1;
                return;
            case 2:
                this.mMainActivity.changeFragmentWithTitle(this.mNavigationDrawerItemTitles[position], AwardsFragment.TAG, false);
                updateSelectedMenu(AwardsFragment.TAG, view);
                this.mMainActivity.mCurrentNavDrawerItem = 2;
                return;
            case 3:
                this.mMainActivity.changeFragmentWithTitle(this.mNavigationDrawerItemTitles[position], JournalFragment.TAG, false);
                updateSelectedMenu(JournalFragment.TAG, view);
                this.mMainActivity.mCurrentNavDrawerItem = 3;
                return;
            default:
                return;
        }
    }

    public void closeDrawer() {
        this.mDrawer.closeDrawer((View) this.mNavigationDrawerList);
    }

    public void updateSelectedMenu(String selectedMenu, View selectedView) {
        for (int itemCurrentCounter = 0; itemCurrentCounter < this.mNavigationDrawerList.getChildCount(); itemCurrentCounter++) {
            View view = this.mNavigationDrawerList.getChildAt(itemCurrentCounter);
            ((ImageView) view.findViewById(R.id.drawer_icon)).setImageDrawable(ResourcesCompat.getDrawable(this.mResources, this.mDrawerItemsArray.get(itemCurrentCounter).getIcon(), (Resources.Theme) null));
            ((TextView) view.findViewById(R.id.drawer_name)).setTextColor(this.mResources.getColor(R.color.white));
        }
        if (selectedMenu.equals(HomeFragment.TAG)) {
            this.mMainActivity.mCurrentNavDrawerItem = 0;
            updateNavDrawerIcon(selectedView, 0);
        }
        if (selectedMenu.equals(GoalsFragment.TAG)) {
            this.mMainActivity.mCurrentNavDrawerItem = 1;
            updateNavDrawerIcon(selectedView, 1);
        }
        if (selectedMenu.equals(AwardsFragment.TAG)) {
            this.mMainActivity.mCurrentNavDrawerItem = 2;
            updateNavDrawerIcon(selectedView, 2);
        }
        if (selectedMenu.equals(JournalFragment.TAG)) {
            this.mMainActivity.mCurrentNavDrawerItem = 3;
            updateNavDrawerIcon(selectedView, 3);
        }
    }

    private void updateNavDrawerIcon(View selectedView, int menuIndex) {
        ((ImageView) selectedView.findViewById(R.id.drawer_icon)).setImageDrawable(ResourcesCompat.getDrawable(this.mResources, this.mDrawerItemsArray.get(menuIndex).getActiveIcon(), (Resources.Theme) null));
        ((TextView) selectedView.findViewById(R.id.drawer_name)).setTextColor(this.mResources.getColor(R.color.navigation_drawer_active_color));
    }
}
