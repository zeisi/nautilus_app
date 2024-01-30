package com.nautilus.omni.app;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.nautilus.omni.dependencyinjection.HasComponent;

public class BaseFragment extends Fragment {
    protected SharedPreferences mPreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPreferences = ((OmniApplication) getActivity().getApplication()).getAppComponent().getSharedPreferences();
    }

    /* access modifiers changed from: protected */
    public <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent) getActivity()).getComponent());
    }

    /* access modifiers changed from: protected */
    public void showPermissionsDialog(DialogInterface.OnClickListener onClickListener, int title, int message, Context context, int okButtonText, int negativeButtonText) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(okButtonText, onClickListener);
        alertDialog.setNegativeButton(negativeButtonText, (DialogInterface.OnClickListener) null);
        alertDialog.show();
    }
}
