package com.nautilus.omni.appmodules.journal.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.journal.adapters.JournalViewPagerAdapter;
import com.nautilus.omni.customviews.slidingtabs.SlidingTabLayout;
import java.lang.reflect.Field;

public class JournalFragment extends Fragment {
    public static final String TAG = JournalFragment.class.getSimpleName();
    private SlidingTabLayout mSlidingTabs;
    private String[] mTabsTitleList;
    private ViewPager mViewPager;
    private JournalViewPagerAdapter mViewPagerAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_journal, container, false);
        setupJournalTabs(rootView);
        return rootView;
    }

    private void setupJournalTabs(View rootView) {
        this.mTabsTitleList = getResources().getStringArray(R.array.journal_tabs_titles);
        this.mViewPagerAdapter = new JournalViewPagerAdapter(getChildFragmentManager(), this.mTabsTitleList.length, this.mTabsTitleList);
        this.mViewPager = (ViewPager) rootView.findViewById(R.id.journal_view_pager);
        this.mViewPager.setAdapter(this.mViewPagerAdapter);
        this.mSlidingTabs = (SlidingTabLayout) rootView.findViewById(R.id.journal_tabs);
        this.mSlidingTabs.setDistributeEvenly(true);
        this.mSlidingTabs.setmTabsTextColor(-1);
        this.mSlidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            public int getIndicatorColor(int position) {
                return JournalFragment.this.getResources().getColor(R.color.orange_nautilus);
            }
        });
        this.mSlidingTabs.setViewPager(this.mViewPager);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, (Object) null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }
}
