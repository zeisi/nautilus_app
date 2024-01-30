package com.nautilus.omni.appmodules.device.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class DeviceFragment_ViewBinding implements Unbinder {
    private DeviceFragment target;
    private View view2131755271;
    private View view2131755272;
    private View view2131755273;

    @UiThread
    public DeviceFragment_ViewBinding(final DeviceFragment target2, View source) {
        this.target = target2;
        target2.mStatusTextView = (TextView) Utils.findRequiredViewAsType(source, R.id.status_value_text_view, "field 'mStatusTextView'", TextView.class);
        target2.mHardwareVariantTextView = (TextView) Utils.findRequiredViewAsType(source, R.id.hardware_variant_text_view, "field 'mHardwareVariantTextView'", TextView.class);
        target2.mManufacturerTextView = (TextView) Utils.findRequiredViewAsType(source, R.id.manufacturer_text_view, "field 'mManufacturerTextView'", TextView.class);
        target2.mProductModelTextView = (TextView) Utils.findRequiredViewAsType(source, R.id.product_model_text_view, "field 'mProductModelTextView'", TextView.class);
        target2.mFirmwareVersionTextView = (TextView) Utils.findRequiredViewAsType(source, R.id.firmware_version_text_view, "field 'mFirmwareVersionTextView'", TextView.class);
        target2.mLastSyncTextView = (TextView) Utils.findRequiredViewAsType(source, R.id.last_sync_text_view, "field 'mLastSyncTextView'", TextView.class);
        target2.mLastSyncStatusTextView = (TextView) Utils.findRequiredViewAsType(source, R.id.last_sync_status_text_view, "field 'mLastSyncStatusTextView'", TextView.class);
        View view = Utils.findRequiredView(source, R.id.forget_device_button, "field 'mForgetDeviceButton' and method 'forgetDevice'");
        target2.mForgetDeviceButton = (Button) Utils.castView(view, R.id.forget_device_button, "field 'mForgetDeviceButton'", Button.class);
        this.view2131755271 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.forgetDevice();
            }
        });
        target2.mSyncNowProgressBar = (ProgressBar) Utils.findRequiredViewAsType(source, R.id.sync_now_progress_bar, "field 'mSyncNowProgressBar'", ProgressBar.class);
        View view2 = Utils.findRequiredView(source, R.id.text_view_sync_now, "field 'mTxtViewSyncNow' and method 'syncNowTextViewAction'");
        target2.mTxtViewSyncNow = (TextView) Utils.castView(view2, R.id.text_view_sync_now, "field 'mTxtViewSyncNow'", TextView.class);
        this.view2131755273 = view2;
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.syncNowTextViewAction();
            }
        });
        View view3 = Utils.findRequiredView(source, R.id.sync_now_button_container, "field 'mSyncNowButtonContainer' and method 'syncNowContainerAction'");
        target2.mSyncNowButtonContainer = (LinearLayout) Utils.castView(view3, R.id.sync_now_button_container, "field 'mSyncNowButtonContainer'", LinearLayout.class);
        this.view2131755272 = view3;
        view3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.syncNowContainerAction();
            }
        });
    }

    @CallSuper
    public void unbind() {
        DeviceFragment target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mStatusTextView = null;
        target2.mHardwareVariantTextView = null;
        target2.mManufacturerTextView = null;
        target2.mProductModelTextView = null;
        target2.mFirmwareVersionTextView = null;
        target2.mLastSyncTextView = null;
        target2.mLastSyncStatusTextView = null;
        target2.mForgetDeviceButton = null;
        target2.mSyncNowProgressBar = null;
        target2.mTxtViewSyncNow = null;
        target2.mSyncNowButtonContainer = null;
        this.view2131755271.setOnClickListener((View.OnClickListener) null);
        this.view2131755271 = null;
        this.view2131755273.setOnClickListener((View.OnClickListener) null);
        this.view2131755273 = null;
        this.view2131755272.setOnClickListener((View.OnClickListener) null);
        this.view2131755272 = null;
    }
}
