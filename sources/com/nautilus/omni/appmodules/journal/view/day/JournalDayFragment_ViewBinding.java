package com.nautilus.omni.appmodules.journal.view.day;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class JournalDayFragment_ViewBinding implements Unbinder {
    private JournalDayFragment target;

    @UiThread
    public JournalDayFragment_ViewBinding(JournalDayFragment target2, View source) {
        this.target = target2;
        target2.mJournalDayRecyclerView = (RecyclerView) Utils.findRequiredViewAsType(source, R.id.day_screen_recycler_view, "field 'mJournalDayRecyclerView'", RecyclerView.class);
        target2.mSwipeRefreshLayout = (SwipeRefreshLayout) Utils.findRequiredViewAsType(source, R.id.day_screen_swipe_refresh_layout, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    }

    @CallSuper
    public void unbind() {
        JournalDayFragment target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mJournalDayRecyclerView = null;
        target2.mSwipeRefreshLayout = null;
    }
}
