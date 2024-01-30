package com.nautilus.omni.model.dto;

import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.Collection;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "user")
public class User implements Serializable, Parcelable {
    public static final String ACHIEVEMENTS = "achievements";
    public static final String AGE = "age";
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    public static final String GENDER = "gender";
    public static final String ID_FIELD_NAME = "id";
    public static final String LAST_SYNC = "last_sync";
    public static final String NUMBER_OF_WORKOUTS = "number_of_workouts_records";
    public static final String TRAINING_GOALS = "training_goals";
    public static final String UNITS = "units";
    public static final String USER = "user data";
    public static final String USER_INDEX = "user_index";
    public static final String WEIGHT = "weight";
    public static final String WORKOUTS = "workouts";
    @ForeignCollectionField(columnName = "achievements", eager = false, maxEagerLevel = 3)
    public Collection<Achievement> mAchievements;
    @DatabaseField(columnName = "age")
    private int mAge;
    @DatabaseField(columnName = "gender")
    private Gender mGender;
    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;
    @DatabaseField(columnName = "last_sync")
    private DateTime mLastSync;
    @DatabaseField(columnName = "number_of_workouts_records")
    private int mNumberOfWorkoutRecords;
    @ForeignCollectionField(columnName = "training_goals", eager = false, maxEagerLevel = 3)
    public Collection<TrainingGoal> mTrainingGoals;
    @DatabaseField(columnName = "units")
    private int mUnits;
    @DatabaseField(columnName = "user_index")
    private int mUserMachineIndex;
    @DatabaseField(columnName = "weight")
    private double mWeight;
    @ForeignCollectionField(columnName = "workouts", eager = false, maxEagerLevel = 3)
    private Collection<Workout> mWorkouts;

    public User() {
    }

    protected User(Parcel in) {
        this.mId = in.readInt();
        this.mUserMachineIndex = in.readInt();
        this.mWeight = in.readDouble();
        this.mAge = in.readInt();
        this.mNumberOfWorkoutRecords = in.readInt();
        this.mUnits = in.readInt();
        this.mLastSync = new DateTime(in.readLong());
        this.mGender = Gender.values()[in.readInt()];
        this.mWorkouts = null;
        this.mTrainingGoals = null;
        this.mAchievements = null;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mUserMachineIndex);
        dest.writeDouble(this.mWeight);
        dest.writeInt(this.mAge);
        dest.writeInt(this.mNumberOfWorkoutRecords);
        dest.writeInt(this.mUnits);
        dest.writeLong(this.mLastSync.getMillis());
        dest.writeInt(this.mGender.ordinal());
    }

    public int describeContents() {
        return 0;
    }

    public int getId() {
        return this.mId;
    }

    public int getUserOmniTrainerIndex() {
        return this.mUserMachineIndex;
    }

    public Gender getGender() {
        return this.mGender;
    }

    public double getWeight() {
        return this.mWeight;
    }

    public int getAge() {
        return this.mAge;
    }

    public int getmNumberOfWorkoutRecords() {
        return this.mNumberOfWorkoutRecords;
    }

    public int getmUnits() {
        return this.mUnits;
    }

    public DateTime getmLastSync() {
        return this.mLastSync;
    }

    public Collection<TrainingGoal> getmTrainingGoals() {
        return this.mTrainingGoals;
    }

    public Collection<Workout> getmWorkouts() {
        return this.mWorkouts;
    }

    public Collection<Achievement> getmAchievements() {
        return this.mAchievements;
    }

    public void setId(int mId2) {
        this.mId = mId2;
    }

    public void setmUserMachineIndex(int mUserMachineIndex2) {
        this.mUserMachineIndex = mUserMachineIndex2;
    }

    public void setGender(Gender gender) {
        this.mGender = gender;
    }

    public void setWeight(double weight) {
        this.mWeight = weight;
    }

    public void setAge(int age) {
        this.mAge = age;
    }

    public void setmNumberOfWorkoutRecords(int mNumberOfWorkoutRecords2) {
        this.mNumberOfWorkoutRecords = mNumberOfWorkoutRecords2;
    }

    public void setmUnits(int mUnits2) {
        this.mUnits = mUnits2;
    }

    public void setmLastSync(DateTime mLastSync2) {
        this.mLastSync = mLastSync2;
    }

    public void setmTrainingGoals(Collection<TrainingGoal> mTrainingGoals2) {
        this.mTrainingGoals = mTrainingGoals2;
    }

    public void setmWorkouts(Collection<Workout> mWorkouts2) {
        this.mWorkouts = mWorkouts2;
    }

    public void setmAchievements(Collection<Achievement> mAchievements2) {
        this.mAchievements = mAchievements2;
    }
}
