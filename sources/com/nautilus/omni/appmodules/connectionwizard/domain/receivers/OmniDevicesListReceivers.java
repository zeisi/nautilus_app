package com.nautilus.omni.appmodules.connectionwizard.domain.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;

public class OmniDevicesListReceivers {
    private BroadcastReceiver addOmniTrainerToListReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            OmniDevicesListReceivers.this.omniDevicesListReceivers.onAddOmniTrainerToListReceiverResponse((OmniData) intent.getExtras().getParcelable(Constants.OMNI_TRAINER));
        }
    };
    public BroadcastReceiver connectionErrorReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            OmniDevicesListReceivers.this.omniDevicesListReceivers.onConnectionErrorReceiverResponse();
        }
    };
    Context context;
    IOmniDevicesListReceivers omniDevicesListReceivers;
    private BroadcastReceiver omniTrainerUpdatesReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            OmniDevicesListReceivers.this.omniDevicesListReceivers.onOmniTrainerUpdatesReceiverResponse((OmniData) intent.getExtras().getParcelable(Constants.OMNI_TRAINER));
        }
    };
    public BroadcastReceiver successfulConnectionReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            OmniDevicesListReceivers.this.omniDevicesListReceivers.onSuccessfulConnectionReceiverResponse();
        }
    };
    public BroadcastReceiver unexpectedDisconnectionProcessReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            OmniDevicesListReceivers.this.omniDevicesListReceivers.onUnexpectedDisconnectionProcessReceiverResponse();
        }
    };

    public OmniDevicesListReceivers(Context context2, IOmniDevicesListReceivers omniDevicesListReceivers2) {
        this.context = context2;
        this.omniDevicesListReceivers = omniDevicesListReceivers2;
    }

    public void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.addOmniTrainerToListReceiver, new IntentFilter(BroadcastKeys.ADD_OMNI_DEVICE));
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.omniTrainerUpdatesReceiver, new IntentFilter(BroadcastKeys.OMNI_UPDATE));
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.successfulConnectionReceiver, new IntentFilter(BroadcastKeys.OMNI_SUCCESSFUL_CONNECTION));
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.connectionErrorReceiver, new IntentFilter(BroadcastKeys.OMNI_CONNECTION_ERROR));
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.unexpectedDisconnectionProcessReceiver, new IntentFilter(BroadcastKeys.UNEXPECTED_DISCONNECTION));
    }

    public void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.addOmniTrainerToListReceiver);
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.omniTrainerUpdatesReceiver);
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.successfulConnectionReceiver);
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.connectionErrorReceiver);
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.unexpectedDisconnectionProcessReceiver);
    }
}
