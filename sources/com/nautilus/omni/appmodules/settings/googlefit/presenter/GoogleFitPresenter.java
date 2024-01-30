package com.nautilus.omni.appmodules.settings.googlefit.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelperContract;
import com.nautilus.omni.appmodules.settings.googlefit.interactor.GoogleFitInteractorContract;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConfigGoogleFitContract;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConnectionGoogleFitViewContract;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import java.util.List;
import javax.inject.Inject;

public class GoogleFitPresenter extends BasePresenter implements GoogleFitPresenterContract, GoogleFitInteractorContract.OnResult, GoogleFitHelperContract.OnUserFitnessServiceConnect {
    ConfigGoogleFitContract mConfigurationView;
    ConnectionGoogleFitViewContract mConnectionView;
    GoogleFitInteractorContract mGoogleFitInteractor;

    @Inject
    public GoogleFitPresenter(Context context, GoogleFitInteractorContract googleFitInteractor) {
        super(context);
        this.mGoogleFitInteractor = googleFitInteractor;
        this.mGoogleFitHelper.initSharedPreferences();
        this.mGoogleFitHelper.setOnUserFitnessServiceConnect(this);
    }

    public void setConfigurationViewView(ConfigGoogleFitContract mConfigurationViewView) {
        this.mConfigurationView = mConfigurationViewView;
    }

    public void setConnectionView(ConnectionGoogleFitViewContract mConnectionView2) {
        this.mConnectionView = mConnectionView2;
    }

    public void startSync() {
        this.mGoogleFitInteractor.getWorkouts(this);
    }

    public void saveCurrentSwitchesStateInPreferences(boolean time, boolean calories, boolean avgHeartRate, boolean avgRPM, boolean distance) {
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putBoolean(Preferences.GOOGLE_FIT_TIME, time);
        editor.putBoolean(Preferences.GOOGLE_FIT_CALORIES, calories);
        editor.putBoolean(Preferences.GOOGLE_FIT_AVG_HEART_RATE, avgHeartRate);
        editor.putBoolean(Preferences.GOOGLE_FIT_RPM, avgRPM);
        editor.putBoolean(Preferences.GOOGLE_FIT_DISTANCE, distance);
        editor.apply();
    }

    public void loadSwitches() {
        this.mConfigurationView.initSwitches(this.mPreferences.getBoolean(Preferences.GOOGLE_FIT_TIME, true), this.mPreferences.getBoolean(Preferences.GOOGLE_FIT_CALORIES, true), this.mPreferences.getBoolean(Preferences.GOOGLE_FIT_AVG_HEART_RATE, true), this.mPreferences.getBoolean(Preferences.GOOGLE_FIT_RPM, true), this.mPreferences.getBoolean(Preferences.GOOGLE_FIT_DISTANCE, true));
    }

    public void setWorkoutList(List<FitServicesWorkout> workoutList, OmniData omniData) {
        this.mConfigurationView.startSync(workoutList, omniData);
    }

    public void connectToFitnessService(Activity context) {
        this.mGoogleFitHelper.connectWithGoogleFit(context);
    }

    public void updateAuthProgressValue(boolean value) {
        this.mGoogleFitHelper.setAuthInProgress(value);
    }

    public void retryConnection(Activity activity) {
        this.mGoogleFitHelper.googleFitIsConnected(activity);
    }

    public void syncFitnessData(Activity context, FitServicesSyncDataHelper fitServicesSyncDataHelper, OmniData omniData, List<FitServicesWorkout> workoutsToSyncList) {
        this.mGoogleFitHelper.setOmniData(omniData);
        this.mGoogleFitHelper.setFitServicesSyncDataHelper(fitServicesSyncDataHelper);
        this.mGoogleFitHelper.syncWorkoutDataWithGoogleFit(workoutsToSyncList);
    }

    public void disconnectFitnessService(Context context) {
        this.mGoogleFitHelper.setOnUserFitnessServiceConnect(this);
        this.mGoogleFitHelper.disconnectFitnessService();
    }

    public void OnConnectionSuccessListener() {
        this.mConnectionView.onConnectionSuccess();
    }

    public void OnConnectionFailedListener(int errorCode, int requestCode) {
        this.mConnectionView.onConnectionFailed(errorCode, requestCode);
    }

    public void OnDisconnectedSuccess() {
        cleanGoogleFitPrefs();
        this.mConfigurationView.close();
    }

    public void OnDisconnectionError() {
        this.mConfigurationView.showDisconnectionError(this.mContext.getString(R.string.google_fit_disconnection_error_title), this.mContext.getString(R.string.google_fit_disconnection_error_message));
    }

    public void cleanGoogleFitPrefs() {
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putBoolean(Preferences.GOOGLE_FIT_TIME, true);
        editor.putBoolean(Preferences.GOOGLE_FIT_CALORIES, true);
        editor.putBoolean(Preferences.GOOGLE_FIT_AVG_HEART_RATE, true);
        editor.putBoolean(Preferences.GOOGLE_FIT_RPM, true);
        editor.putBoolean(Preferences.GOOGLE_FIT_DISTANCE, true);
        editor.apply();
    }
}
