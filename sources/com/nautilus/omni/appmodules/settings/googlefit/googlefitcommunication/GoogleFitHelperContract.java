package com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication;

public interface GoogleFitHelperContract {

    public interface OnUserFitnessServiceConnect {
        void OnConnectionFailedListener(int i, int i2);

        void OnConnectionSuccessListener();

        void OnDisconnectedSuccess();

        void OnDisconnectionError();
    }
}
