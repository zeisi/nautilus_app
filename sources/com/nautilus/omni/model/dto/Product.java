package com.nautilus.omni.model.dto;

import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "product")
public class Product implements Parcelable {
    public static final String CONSUMER_VARIANT = "consumer_variant";
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    public static final String FIRMWARE_VERSION = "firmware_version";
    public static final String HARDWARE_VARIANT = "hardware_variant";
    public static final String ID_FIELD_NAME = "id";
    public static final String LAST_SYNC = "last_sync";
    public static final String LAST_SYNC_STATUS = "last_sync_status";
    public static final String MANUFACTURER = "manufacturer";
    public static final String PRODUCT_MODEL_NAME = "product_model_name";
    public static final String PRODUCT_MODEL_NUMBER = "product_model_number";
    public static final String SOFTWARE_VERSION = "software_version";
    @DatabaseField(columnName = "consumer_variant")
    private String mConsumerVariant;
    @DatabaseField(columnName = "firmware_version")
    private String mFirmwareVersion;
    @DatabaseField(columnName = "hardware_variant")
    private String mHardwareVariant;
    @DatabaseField(columnName = "id", id = true)
    private int mId;
    @DatabaseField(columnName = "last_sync")
    private DateTime mLastSync;
    @DatabaseField(columnName = "last_sync_status")
    private SyncStatus mLastSyncStatus;
    @DatabaseField(columnName = "manufacturer")
    private String mManufacturer;
    @DatabaseField(columnName = "product_model_name")
    private String mProductModelName;
    @DatabaseField(columnName = "product_model_number")
    private String mProductModelNumber;
    @DatabaseField(columnName = "software_version")
    private String mSoftwareVersion;

    public Product() {
    }

    public Product(Parcel in) {
        this.mId = in.readInt();
        this.mConsumerVariant = in.readString();
        this.mHardwareVariant = in.readString();
        this.mManufacturer = in.readString();
        this.mProductModelName = in.readString();
        this.mFirmwareVersion = in.readString();
        this.mProductModelNumber = in.readString();
        this.mSoftwareVersion = in.readString();
        this.mLastSync = new DateTime(in.readLong());
        this.mLastSyncStatus = SyncStatus.values()[in.readInt()];
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mConsumerVariant);
        dest.writeString(this.mHardwareVariant);
        dest.writeString(this.mManufacturer);
        dest.writeString(this.mProductModelName);
        dest.writeString(this.mFirmwareVersion);
        dest.writeString(this.mProductModelNumber);
        dest.writeString(this.mSoftwareVersion);
        dest.writeLong(this.mLastSync.getMillis());
        dest.writeInt(this.mLastSyncStatus.ordinal());
    }

    public int describeContents() {
        return 0;
    }

    public int getmId() {
        return this.mId;
    }

    public String getmConsumerVariant() {
        return this.mConsumerVariant;
    }

    public String getmHardwareVariant() {
        return this.mHardwareVariant;
    }

    public String getmManufacturer() {
        return this.mManufacturer.replaceAll("\\p{C}", "");
    }

    public String getmProductModelName() {
        return this.mProductModelName.replaceAll("\\p{C}", "");
    }

    public String getmFirmwareVersion() {
        return this.mFirmwareVersion;
    }

    public String getmProductModelNumber() {
        return this.mProductModelNumber;
    }

    public String getmSoftwareVersion() {
        return this.mSoftwareVersion;
    }

    public DateTime getmLastSync() {
        return this.mLastSync;
    }

    public SyncStatus getmLastSyncStatus() {
        return this.mLastSyncStatus;
    }

    public void setmId(int mId2) {
        this.mId = mId2;
    }

    public void setmConsumerVariant(String mConsumerVariant2) {
        this.mConsumerVariant = mConsumerVariant2;
    }

    public void setmHardwareVariant(String mHardwareVariant2) {
        this.mHardwareVariant = mHardwareVariant2;
    }

    public void setmManufacturer(String mManufacturer2) {
        this.mManufacturer = mManufacturer2;
    }

    public void setmProductModelName(String mProductModelName2) {
        this.mProductModelName = mProductModelName2;
    }

    public void setmFirmwareVersion(String mFirmwareVersion2) {
        this.mFirmwareVersion = mFirmwareVersion2;
    }

    public void setmProductModelNumber(String mProductModelNumber2) {
        this.mProductModelNumber = mProductModelNumber2;
    }

    public void setmSoftwareVersion(String mSoftwareVersion2) {
        this.mSoftwareVersion = mSoftwareVersion2;
    }

    public void setmLastSync(DateTime mLastSync2) {
        this.mLastSync = mLastSync2;
    }

    public void setmLastSyncStatus(SyncStatus mLastSyncStatus2) {
        this.mLastSyncStatus = mLastSyncStatus2;
    }
}
