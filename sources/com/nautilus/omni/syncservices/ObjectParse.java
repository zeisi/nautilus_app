package com.nautilus.omni.syncservices;

import android.os.Bundle;

public class ObjectParse {
    String mAction;
    Bundle mBundle;

    public ObjectParse(String mAction2, Bundle mBundle2) {
        this.mAction = mAction2;
        this.mBundle = mBundle2;
    }

    public String getmAction() {
        return this.mAction;
    }

    public Bundle getmBundle() {
        return this.mBundle;
    }
}
