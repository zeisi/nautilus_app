package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

@TargetApi(13)
@RequiresApi(13)
public abstract class FragmentStatePagerAdapter extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentStatePagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<>();

    public abstract Fragment getItem(int i);

    public FragmentStatePagerAdapter(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    public void startUpdate(ViewGroup container) {
        if (container.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    public Object instantiateItem(ViewGroup container, int position) {
        Fragment.SavedState fss;
        Fragment f;
        if (this.mFragments.size() > position && (f = this.mFragments.get(position)) != null) {
            return f;
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        Fragment fragment = getItem(position);
        if (this.mSavedState.size() > position && (fss = this.mSavedState.get(position)) != null) {
            fragment.setInitialSavedState(fss);
        }
        while (this.mFragments.size() <= position) {
            this.mFragments.add((Object) null);
        }
        FragmentCompat.setMenuVisibility(fragment, false);
        FragmentCompat.setUserVisibleHint(fragment, false);
        this.mFragments.set(position, fragment);
        this.mCurTransaction.add(container.getId(), fragment);
        return fragment;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment.SavedState savedState;
        Fragment fragment = (Fragment) object;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= position) {
            this.mSavedState.add((Object) null);
        }
        ArrayList<Fragment.SavedState> arrayList = this.mSavedState;
        if (fragment.isAdded()) {
            savedState = this.mFragmentManager.saveFragmentInstanceState(fragment);
        } else {
            savedState = null;
        }
        arrayList.set(position, savedState);
        this.mFragments.set(position, (Object) null);
        this.mCurTransaction.remove(fragment);
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                FragmentCompat.setMenuVisibility(this.mCurrentPrimaryItem, false);
                FragmentCompat.setUserVisibleHint(this.mCurrentPrimaryItem, false);
            }
            if (fragment != null) {
                FragmentCompat.setMenuVisibility(fragment, true);
                FragmentCompat.setUserVisibleHint(fragment, true);
            }
            this.mCurrentPrimaryItem = fragment;
        }
    }

    public void finishUpdate(ViewGroup container) {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
        }
    }

    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    public Parcelable saveState() {
        Bundle state = null;
        if (this.mSavedState.size() > 0) {
            state = new Bundle();
            Fragment.SavedState[] fss = new Fragment.SavedState[this.mSavedState.size()];
            this.mSavedState.toArray(fss);
            state.putParcelableArray("states", fss);
        }
        for (int i = 0; i < this.mFragments.size(); i++) {
            Fragment f = this.mFragments.get(i);
            if (f != null && f.isAdded()) {
                if (state == null) {
                    state = new Bundle();
                }
                this.mFragmentManager.putFragment(state, "f" + i, f);
            }
        }
        return state;
    }

    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if (fss != null) {
                for (Parcelable parcelable : fss) {
                    this.mSavedState.add((Fragment.SavedState) parcelable);
                }
            }
            for (String key : bundle.keySet()) {
                if (key.startsWith("f")) {
                    int index = Integer.parseInt(key.substring(1));
                    Fragment f = this.mFragmentManager.getFragment(bundle, key);
                    if (f != null) {
                        while (this.mFragments.size() <= index) {
                            this.mFragments.add((Object) null);
                        }
                        FragmentCompat.setMenuVisibility(f, false);
                        this.mFragments.set(index, f);
                    } else {
                        Log.w(TAG, "Bad fragment at key " + key);
                    }
                }
            }
        }
    }
}
