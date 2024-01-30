package com.nautilus.omni.appmodules.connectionwizard.domain.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;

public class WizardReceivers {
    private ConnectionErrorReceiver connectionErrorReceiver;
    private final Context context;
    private final HideConnectionDeviceToastReceiver hideConnectionDeviceToastReceiver = new HideConnectionDeviceToastReceiver();
    private final OmniDataReceiver omniDataReceiver = new OmniDataReceiver();
    private final OmniUpdateDataReceiver omniUpdateDataReceiver = new OmniUpdateDataReceiver();
    private final ShowErrorConnectingDialogReceiver showErrorConnectingDialogReceiver = new ShowErrorConnectingDialogReceiver();
    private final UnexpectedDisconnectionProcessReceiver unexpectedDisconnectionProcessReceiver = new UnexpectedDisconnectionProcessReceiver();
    /* access modifiers changed from: private */
    public IWizardReceiversResponse wizardReceiversResponse;

    public WizardReceivers(Context context2) {
        this.context = context2;
    }

    public void setWizardReceiversResponse(IWizardReceiversResponse wizardReceiversResponse2) {
        this.wizardReceiversResponse = wizardReceiversResponse2;
    }

    public void registerBroadcastReceivers(ISelectUserReceiversResponse selectUserReceiversResponse) {
        this.connectionErrorReceiver = new ConnectionErrorReceiver(selectUserReceiversResponse);
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.connectionErrorReceiver, new IntentFilter(BroadcastKeys.OMNI_CONNECTION_ERROR));
    }

    public void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.omniDataReceiver, new IntentFilter(BroadcastKeys.ADD_OMNI_DEVICE));
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.omniUpdateDataReceiver, new IntentFilter(BroadcastKeys.OMNI_UPDATE));
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.hideConnectionDeviceToastReceiver, new IntentFilter(BroadcastKeys.HIDE_CONNECTING_DEVICE_TOAST));
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.showErrorConnectingDialogReceiver, new IntentFilter(BroadcastKeys.SHOW_ERROR_CONNECTING_DIALOG));
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.unexpectedDisconnectionProcessReceiver, new IntentFilter(BroadcastKeys.UNEXPECTED_DISCONNECTION));
    }

    public void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.omniDataReceiver);
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.omniUpdateDataReceiver);
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.hideConnectionDeviceToastReceiver);
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.showErrorConnectingDialogReceiver);
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.unexpectedDisconnectionProcessReceiver);
    }

    public void unregisterUserSelectBroadcastReceivers() {
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.connectionErrorReceiver);
    }

    public class OmniDataReceiver extends BroadcastReceiver {
        public OmniDataReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            WizardReceivers.this.wizardReceiversResponse.onAddOmniDeviceResponse((OmniData) intent.getExtras().getParcelable(Constants.OMNI_TRAINER));
        }
    }

    public class OmniUpdateDataReceiver extends BroadcastReceiver {
        public OmniUpdateDataReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            WizardReceivers.this.wizardReceiversResponse.onUpdateReceiverResponse((OmniData) intent.getExtras().getParcelable(Constants.OMNI_TRAINER));
        }
    }

    public class HideConnectionDeviceToastReceiver extends BroadcastReceiver {
        public HideConnectionDeviceToastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            WizardReceivers.this.wizardReceiversResponse.onSuccessfulConnectionReceiverResponse();
        }
    }

    public class ShowErrorConnectingDialogReceiver extends BroadcastReceiver {
        public ShowErrorConnectingDialogReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            WizardReceivers.this.wizardReceiversResponse.onShowErrorConnectingDialogReceiverResponse();
        }
    }

    public class UnexpectedDisconnectionProcessReceiver extends BroadcastReceiver {
        public UnexpectedDisconnectionProcessReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            WizardReceivers.this.wizardReceiversResponse.onUnexpectedDisconnProcessReceiverResponse();
        }
    }

    public class ConnectionErrorReceiver extends BroadcastReceiver {
        ISelectUserReceiversResponse selectUserReceiversResponse;

        public ConnectionErrorReceiver(ISelectUserReceiversResponse selectUserReceiversResponse2) {
            this.selectUserReceiversResponse = selectUserReceiversResponse2;
        }

        public void onReceive(Context context, Intent intent) {
            this.selectUserReceiversResponse.onReceiverConnectionErrorResponse();
        }
    }
}
