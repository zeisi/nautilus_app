package com.nautilus.omni.model.dto;

import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.nautilus.omni.bleservices.ble.OmniDevice;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "omni")
public class OmniData implements Parcelable {
    public static final String BUSY = "busy";
    public static final Parcelable.Creator<OmniData> CREATOR = new Parcelable.Creator<OmniData>() {
        public OmniData createFromParcel(Parcel in) {
            return new OmniData(in);
        }

        public OmniData[] newArray(int size) {
            return new OmniData[size];
        }
    };
    public static final String ID_FIELD_NAME = "id";
    public static final String NAME = "name";
    public static final String OMNI_CONSOLE_TYPE = "omni_console_type";
    public static final String OMNI_MACHINE_TYPE = "omni_machine_type";
    public static final String SIGNAL_STRENGTH = "signal_strength";
    public static final String UNIQUE_ID = "unique_id";
    public static final String USER_NUMBER = "user_number";
    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;
    @DatabaseField(columnName = "busy")
    private boolean mIsBusy;
    private Boolean mIsEnabled;
    @DatabaseField(columnName = "name")
    private String mName;
    @DatabaseField(columnName = "omni_console_type")
    private OmniConsoleType mOmniConsoleType;
    @DatabaseField(columnName = "omni_machine_type")
    private OmniMachineType mOmniMachineType;
    private OmniDevice mOmniTrainerBluetoothDevice;
    @DatabaseField(columnName = "signal_strength")
    private int mSignalStrength;
    @DatabaseField(columnName = "unique_id")
    private String mUniqueID;
    private DateTime mUpdateTime;
    @DatabaseField(columnName = "user_number")
    private int mUserNumber;

    public OmniData() {
    }

    public OmniData(Parcel in) {
        boolean z = true;
        this.mUniqueID = in.readString();
        this.mName = in.readString();
        this.mSignalStrength = in.readInt();
        this.mUserNumber = in.readInt();
        this.mIsBusy = in.readByte() != 0;
        this.mOmniTrainerBluetoothDevice = (OmniDevice) in.readParcelable(OmniDevice.class.getClassLoader());
        this.mUpdateTime = (DateTime) in.readSerializable();
        this.mIsEnabled = Boolean.valueOf(in.readByte() == 0 ? false : z);
        this.mOmniConsoleType = OmniConsoleType.values()[in.readInt()];
        this.mOmniMachineType = OmniMachineType.values()[in.readInt()];
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i = 1;
        dest.writeString(this.mUniqueID);
        dest.writeString(this.mName);
        dest.writeInt(this.mSignalStrength);
        dest.writeInt(this.mUserNumber);
        dest.writeByte((byte) (this.mIsBusy ? 1 : 0));
        dest.writeParcelable(this.mOmniTrainerBluetoothDevice, 1);
        dest.writeSerializable(this.mUpdateTime);
        if (!this.mIsEnabled.booleanValue()) {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeInt(this.mOmniConsoleType.ordinal());
        dest.writeInt(this.mOmniMachineType.ordinal());
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object treadClimberObject) {
        return ((OmniData) treadClimberObject).getmUniqueID().equals(getmUniqueID());
    }

    public int getmId() {
        return this.mId;
    }

    public String getmUniqueID() {
        return this.mUniqueID;
    }

    public String getmName() {
        return this.mName;
    }

    public int getmSignalStrength() {
        return this.mSignalStrength;
    }

    public int getmUserNumber() {
        return this.mUserNumber;
    }

    public OmniDevice getmOmniTrainerBluetoothDevice() {
        return this.mOmniTrainerBluetoothDevice;
    }

    public boolean ismIsBusy() {
        return this.mIsBusy;
    }

    public DateTime getmUpdateTime() {
        return this.mUpdateTime;
    }

    public Boolean isEnabled() {
        return this.mIsEnabled;
    }

    public OmniConsoleType getmOmniConsoleType() {
        return this.mOmniConsoleType;
    }

    public OmniMachineType getmOmniMachineType() {
        return this.mOmniMachineType;
    }

    public void setmId(int mId2) {
        this.mId = mId2;
    }

    public void setmUniqueID(String mUniqueID2) {
        this.mUniqueID = mUniqueID2;
    }

    public void setmName(String mName2) {
        this.mName = mName2;
    }

    public void setmSignalStrength(int mSignalStrength2) {
        this.mSignalStrength = mSignalStrength2;
    }

    public void setmUserNumber(int mUserNumber2) {
        this.mUserNumber = mUserNumber2;
    }

    public void setIsBusy(boolean isBusy) {
        this.mIsBusy = isBusy;
    }

    public void setmIsBusy(boolean mIsBusy2) {
        this.mIsBusy = mIsBusy2;
    }

    public void setmOmniBluetoothDevice(OmniDevice mOmniTrainerBluetoothDevice2) {
        this.mOmniTrainerBluetoothDevice = mOmniTrainerBluetoothDevice2;
    }

    public void setmUpdateTime(DateTime mUpdateTime2) {
        this.mUpdateTime = mUpdateTime2;
    }

    public void setmIsEnabled(Boolean mIsEnabled2) {
        this.mIsEnabled = mIsEnabled2;
    }

    public void setmOmniConsoleType(OmniConsoleType mOmniConsoleType2) {
        this.mOmniConsoleType = mOmniConsoleType2;
    }

    public void setmOmniMachineType(OmniMachineType mOmniMachineType2) {
        this.mOmniMachineType = mOmniMachineType2;
    }
}
