package com.nautilus.omni.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.R;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.appmodules.awards.AwardTypeEnum;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.Gender;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.model.dto.OmniMachineType;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.syncservices.ObservableObject;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class Util {
    private static final String TAG = Util.class.getSimpleName();

    public static void executeViewFadeInAnimation(Context context, int duration, View button) {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.abc_fade_in);
        fadeInAnimation.setDuration((long) duration);
        button.setVisibility(0);
        button.startAnimation(fadeInAnimation);
    }

    public static void executeViewFadeOutAnimation(Context context, int duration, View button) {
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.abc_fade_out);
        fadeOutAnimation.setDuration((long) duration);
        button.setVisibility(8);
        button.startAnimation(fadeOutAnimation);
    }

    public static String convertSecondsToDuration(long seconds, boolean showSeconds) {
        Period period = new Duration(1000 * seconds).toPeriod(PeriodType.yearDayTime());
        StringBuilder stringBuilder = new StringBuilder();
        if (String.valueOf(period.getHours()).length() == 1) {
            stringBuilder.append("0");
        }
        stringBuilder.append(period.getHours()).append(Constants.COLON_SEPARATOR);
        if (String.valueOf(period.getMinutes()).length() == 1) {
            stringBuilder.append("0");
        }
        if (showSeconds) {
            stringBuilder.append(period.getMinutes()).append(Constants.COLON_SEPARATOR);
            if (String.valueOf(period.getSeconds()).length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(period.getSeconds());
        } else {
            stringBuilder.append(period.getMinutes());
        }
        return stringBuilder.toString();
    }

    public static String convertSecondsToTimeFormatInMinutesAndSeconds(int seconds) {
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(seconds / 60), Integer.valueOf(seconds % 60)});
    }

    public static int getSecondsFromTimeGoal(String goalTimeValue) {
        int selectedMinutes = Integer.parseInt(goalTimeValue.substring(0, 2));
        return (selectedMinutes * 60) + Integer.parseInt(goalTimeValue.substring(3, 5));
    }

    public static long buildWorkoutStartTime(Workout workout) {
        return buildWorkoutEndTime(workout) - ((long) (workout.getmElapsedTime() * 1000));
    }

    public static long buildWorkoutEndTime(Workout workout) {
        return workout.getmFinishTime().getMillis();
    }

    public static float getAvgCalorieBurnRatePerMinute(Workout workout) {
        return Float.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)).format((double) (((float) workout.getmCalories()) / (((float) workout.getmElapsedTime()) / 60.0f)))).floatValue();
    }

    public static String getAvgCalorieBurnRateAsStringOneDecimal(Workout workout) {
        return new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.getDefault())).format((double) workout.getmAvgCalorieBurnRate());
    }

    public static String getAvgCalorieBurnRateAsStringTwoDecimal(Workout workout) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format((double) workout.getmAvgCalorieBurnRate());
    }

    public static String getWorkoutSpeedInMilesAsString(Workout workout) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format(((double) workout.getmAvgSpeed()) / 100.0d);
    }

    public static String getWorkoutSpeedInKilometersAsString(Workout workout) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format((((double) workout.getmAvgSpeed()) / 100.0d) * 1.6093440055847168d);
    }

    public static double getWorkoutSpeedInMiles(Workout workout) {
        return Double.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)).format(((double) workout.getmAvgSpeed()) / 100.0d)).doubleValue();
    }

    public static double getWorkoutSpeedInKilometers(Workout workout) {
        return Double.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)).format((((double) workout.getmAvgSpeed()) / 100.0d) * 1.6093440055847168d)).doubleValue();
    }

    public static String getWeekAvgSpeedInMilesAsString(Week week) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format((((double) week.getmTotalWorkoutsSpeed()) / 100.0d) / ((double) week.getmTotalWorkouts()));
    }

    public static String getWeekAvgSpeedInKmAsString(Week week) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format(((((double) week.getmTotalWorkoutsSpeed()) / 100.0d) * 1.6093440055847168d) / ((double) week.getmTotalWorkouts()));
    }

    public static String getWeekAvgSpeedInMilesAsStringOneDecimal(Week week) {
        return new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.getDefault())).format((((double) week.getmTotalWorkoutsSpeed()) / 100.0d) / ((double) week.getmTotalWorkouts()));
    }

    public static String getWeekAvgSpeedInKmAsStringOneDecimal(Week week) {
        return new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.getDefault())).format(((((double) week.getmTotalWorkoutsSpeed()) / 100.0d) * 1.6093440055847168d) / ((double) week.getmTotalWorkouts()));
    }

    public static String getWorkoutDistanceInKilometersAsString(Workout workout) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format((((double) workout.getmDistance()) / 100.0d) * 1.6093440055847168d);
    }

    public static double getWorkoutDistanceInKilometers(Workout workout) {
        return Double.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)).format((((double) workout.getmDistance()) / 100.0d) * 1.6093440055847168d)).doubleValue();
    }

    public static String getWorkoutDistanceInMilesAsString(Workout workout) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format(((double) workout.getmDistance()) / 100.0d);
    }

    public static double getWorkoutDistanceInMiles(Workout workout) {
        return Double.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)).format(((double) workout.getmDistance()) / 100.0d)).doubleValue();
    }

    public static String getWeekDistanceInMilesAsString(Week week) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format(((double) week.getmTotalWorkoutsDistance()) / 100.0d);
    }

    public static String getWeekDistanceInKmAsString(Week week) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format((((double) week.getmTotalWorkoutsDistance()) / 100.0d) * 1.6093440055847168d);
    }

    public static String getWeekDistanceInMilesAsStringOneDecimal(Week week) {
        return new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.getDefault())).format(((double) week.getmTotalWorkoutsDistance()) / 100.0d);
    }

    public static String getWeekDistanceInKmAsStringOneDecimal(Week week) {
        return new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.getDefault())).format((((double) week.getmTotalWorkoutsDistance()) / 100.0d) * 1.6093440055847168d);
    }

    public static String getAwardDistanceValueDependingOnCurrentUnit(Context context, int unit, Award award) {
        StringBuilder stringBuilder = new StringBuilder();
        if (unit == 1) {
            return stringBuilder.append(getWorkoutDistanceInKilometersAsString(award.getmWorkout())).append(context.getString(R.string.awards_kilometers)).toString();
        }
        return stringBuilder.append(getWorkoutDistanceInMilesAsString(award.getmWorkout())).append(context.getString(R.string.awards_miles)).toString();
    }

    public static String getWeekDistanceValueDependingOnCurrentUnit(Context context, int unit, Week week) {
        StringBuilder stringBuilder = new StringBuilder();
        if (unit == 1) {
            return stringBuilder.append(getWeekDistanceInKmAsString(week)).append(context.getString(R.string.awards_kilometers)).toString();
        }
        return stringBuilder.append(getWeekDistanceInMilesAsString(week)).append(context.getString(R.string.awards_miles)).toString();
    }

    public static String getAwardSpeedValueDependingOnCurrentUnit(Context context, int unit, Award award) {
        StringBuilder stringBuilder = new StringBuilder();
        if (unit == 1) {
            return stringBuilder.append(getWorkoutSpeedInKilometersAsString(award.getmWorkout())).append(context.getString(R.string.awards_kilometers_per_hour)).toString();
        }
        return stringBuilder.append(getWorkoutSpeedInMilesAsString(award.getmWorkout())).append(context.getString(R.string.awards_miles_per_hour)).toString();
    }

    public static float getWorkoutDistanceInMeters(Workout workout) {
        return ((float) getWorkoutDistanceInMiles(workout)) * 1609.34f;
    }

    public static double getWorkoutPower(Workout workout) {
        return Double.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)).format(((double) workout.getmAvgPower()) / 100.0d)).doubleValue();
    }

    public static String getWorkoutPowerAsStringTwoDecimals(Workout workout) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format(((double) workout.getmAvgPower()) / 100.0d);
    }

    public static Intent buildShareIntent(Context context, String packageName, ResolveInfo resolveInfo) {
        Intent sharingIntent = new Intent();
        sharingIntent.setComponent(new ComponentName(packageName, resolveInfo.activityInfo.name));
        sharingIntent.setAction("android.intent.action.SEND");
        sharingIntent.setType(Constants.JPEG_FORMAT);
        sharingIntent.putExtra("android.intent.extra.SUBJECT", context.getResources().getString(R.string.share_my_workout));
        sharingIntent.setPackage(packageName);
        return sharingIntent;
    }

    public static boolean updateShareMessageAccordingWithAppSelected(Context context, Intent intent, Activity activity, int layoutId) {
        String packageName = intent.getComponent().getPackageName();
        File file = null;
        try {
            file = saveBitmap(takeScreenshot(activity, layoutId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file));
        intent.addFlags(1);
        if (packageName.equals(Constants.TWITTER_APP)) {
            intent.putExtra("android.intent.extra.TEXT", context.getString(R.string.twitter_share_message));
            context.startActivity(intent);
        } else {
            intent.putExtra("android.intent.extra.TEXT", context.getString(R.string.share_message));
            context.startActivity(intent);
        }
        return true;
    }

    public static Bitmap takeScreenshot(Activity activity, int layoutId) {
        activity.getWindow().getDecorView().findViewById(layoutId).setDrawingCacheEnabled(false);
        activity.getWindow().getDecorView().findViewById(layoutId).setDrawingCacheEnabled(true);
        return activity.getWindow().getDecorView().findViewById(layoutId).getDrawingCache();
    }

    public static File saveBitmap(Bitmap bitmap) throws Exception {
        File outputFile = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return outputFile;
    }

    public static String convertValueToDecimal(int value) {
        return new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.getDefault())).format((long) value);
    }

    public static String convertDoubleToTwoDecimals(double value) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.getDefault())).format(value);
    }

    public static ArrayList<Award> createEmptyAwards(Context context) {
        ArrayList<Award> emptyAwardsList = new ArrayList<>();
        int emptyCirclesAmount = Integer.parseInt(context.getString(R.string.awards_empty_circles_amount));
        for (int counter = 0; counter < emptyCirclesAmount; counter++) {
            emptyAwardsList.add(new Award());
        }
        return emptyAwardsList;
    }

    public static void enableBluetooth(Activity activity) {
        BluetoothManager btManager = (BluetoothManager) activity.getSystemService("bluetooth");
        if (btManager != null) {
            BluetoothAdapter bluetoothAdaptor = btManager.getAdapter();
        }
        activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 1);
    }

    public static int getMetricBasedOnCurrentLocale(Locale locale) {
        if (Locale.ENGLISH.getLanguage().equals(locale.getLanguage())) {
            return 0;
        }
        return 1;
    }

    public static void insertTemporalWorkouts(DataBaseHelper dataBaseHelper) {
        try {
            Dao<Workout, Integer> workoutDao = dataBaseHelper.getWorkoutDao();
            ArrayList<Workout> workoutsList = new ArrayList<>();
            Dao<User, Integer> userDao = dataBaseHelper.getUserDao();
            DateTime date = new DateTime().minusYears(4);
            User user = new User();
            user.setmUserMachineIndex(1);
            user.setAge(12);
            user.setGender(Gender.MALE);
            user.setmLastSync(date);
            user.setmAchievements((Collection<Achievement>) null);
            user.setmNumberOfWorkoutRecords(1);
            user.setmTrainingGoals((Collection<TrainingGoal>) null);
            user.setWeight(60.0d);
            user.setmWorkouts(workoutsList);
            userDao.createIfNotExists(user);
            for (int counter = 1; counter < 6; counter++) {
                Workout workout = new Workout();
                workout.setmAwards((Collection<Award>) null);
                workout.setmAvgCalorieBurnRate(10.0f);
                workout.setmAvgHeartRate(10);
                workout.setmCalories(counter * 10);
                workout.setmElapsedTime(3600);
                workout.setmFinishTime(date);
                workout.setmAvgPower(10);
                workout.setmHeartRateMeasuredTime(60);
                workout.setmAvgResistance(100);
                workout.setmAvgRPM(counter * 10);
                workout.setmProgramId(4);
                workout.setmRecordId(counter);
                workout.setmWorkoutDate(date);
                workout.setmWorkoutLapsCompleted(1);
                workout.setmUser(user);
                workoutDao.createIfNotExists(workout);
                workoutsList.add(workout);
                date = date.plusYears(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertTemporalAwards(DataBaseHelper dataBaseHelper) {
        try {
            Dao<Award, Integer> awardDao = dataBaseHelper.getAwardsDao();
            Award award = new Award();
            award.setmAwardType(dataBaseHelper.getAwardTypeDao().queryBuilder().where().eq("name", AwardTypeEnum.MOST_CALORIES_WORKOUT.toString()).queryForFirst());
            award.setmDate(new DateTime());
            award.setmValue("250 Cal");
            award.setmWorkout(dataBaseHelper.getWorkoutDao().queryForId(1));
            awardDao.createIfNotExists(award);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableObject getObservableObject(Context context) {
        return ((OmniApplication) context.getApplicationContext()).getAppComponent().getObservableObject();
    }

    public static boolean hasWatts(Context context) {
        AppComponent appComponent = ((OmniApplication) context.getApplicationContext()).getAppComponent();
        OmniTrainerDaoHelper omniTrainerDaoHelper = appComponent.getOmniTrainerDaoHelper();
        FitServicesSyncDataHelper fitServicesDataHelper = appComponent.getFitServicesDataHelper();
        OmniData omniData = appComponent.getOmniTrainerDaoHelper().getFirstOmniData();
        if (omniData == null) {
            return false;
        }
        OmniMachineType machineType = omniData.getmOmniMachineType();
        return machineType.equals(OmniMachineType.ELLIPTICAL_E628_INTERNATIONAL) || machineType.equals(OmniMachineType.UPRIGHT_BIKE_UR628_INTERNATIONAL) || machineType.equals(OmniMachineType.MY16_INTERNATIONAL_RECUMBENT_BIKE);
    }
}
