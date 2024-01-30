package com.nautilus.omni.appmodules.splash.view;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.appmodules.splash.presenter.SplashPresenterContract;
import com.nautilus.omni.appmodules.tutorial.view.TutorialActivity;
import com.nautilus.omni.dependencyinjection.components.DaggerSplashComponent;
import com.nautilus.omni.dependencyinjection.modules.splash.SplashModule;
import com.nautilus.omni.syncservices.BLECallbacksHandlerService;
import com.nautilus.omni.util.Constants;
import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashViewContract {
    private static final long SPLASH_TIME = 1500;
    public static final String TAG = LauncherActivity.class.getSimpleName();
    /* access modifiers changed from: private */
    public LoadDataTask mLoadDataTask;
    @Inject
    SplashPresenterContract splashPresenter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_splash);
        onGraphActivity();
    }

    private void onGraphActivity() {
        DaggerSplashComponent.builder().appComponent(getAppComponent()).splashModule(new SplashModule(this)).build().inject(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        if (this.mLoadDataTask == null) {
            this.mLoadDataTask = new LoadDataTask(this.splashPresenter);
            this.mLoadDataTask.execute(new Void[0]);
        }
    }

    private class LoadDataTask extends AsyncTask<Void, Void, Long> {
        SplashPresenterContract mSplashPresenter;

        public LoadDataTask(SplashPresenterContract splashPresenter) {
            this.mSplashPresenter = splashPresenter;
            this.mSplashPresenter.showUpdatingDatabaseToast();
        }

        /* access modifiers changed from: protected */
        public Long doInBackground(Void... voids) {
            long startTime = System.currentTimeMillis();
            this.mSplashPresenter.updateTimeGoalValues();
            this.mSplashPresenter.checkFirstPreferences();
            this.mSplashPresenter.createAwardTypesInDatabase();
            return Long.valueOf(startTime);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Long startTimeMillis) {
            super.onPostExecute(startTimeMillis);
            SplashActivity.this.checkInitTime(System.currentTimeMillis() - startTimeMillis.longValue());
            LoadDataTask unused = SplashActivity.this.mLoadDataTask = null;
        }
    }

    /* access modifiers changed from: private */
    public void checkInitTime(long elapseTime) {
        if (elapseTime >= SPLASH_TIME) {
            this.splashPresenter.startNextActivity();
            finish();
            return;
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.splashPresenter.startNextActivity();
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME - elapseTime);
    }

    public void startTutorialActivity() {
        startActivity(new Intent(this, TutorialActivity.class));
    }

    public void startHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.SHOULD_START_AUTO_SYNC, true);
        startActivity(intent);
    }

    public void startBLECallbacksHandlerService() {
        startService(new Intent(getApplicationContext(), BLECallbacksHandlerService.class));
    }

    public void startConnectionWizard() {
        startActivity(new Intent(this, ConnectionWizardActivity.class));
    }

    public void showUpdatingDatabaseToast(String message) {
        Toast.makeText(this, message, 1).show();
    }
}
