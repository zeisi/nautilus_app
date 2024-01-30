package com.example.sharelibrary;

import android.content.pm.ResolveInfo;

public interface OnSelectPackageListener {
    void onPackageSelect(String str, ResolveInfo resolveInfo);
}
