package com.nautilus.omni.appmodules.home.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class AchievementsFragment_ViewBinding implements Unbinder {
    private AchievementsFragment target;

    @UiThread
    public AchievementsFragment_ViewBinding(AchievementsFragment target2, View source) {
        this.target = target2;
        target2.listView = (ListView) Utils.findRequiredViewAsType(source, R.id.list, "field 'listView'", ListView.class);
    }

    @CallSuper
    public void unbind() {
        AchievementsFragment target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.listView = null;
    }
}
