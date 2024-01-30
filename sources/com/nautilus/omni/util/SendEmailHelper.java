package com.nautilus.omni.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.format.DateFormat;
import android.widget.Toast;
import com.nautilus.omni.R;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.SyncStatus;
import com.nautilus.omni.model.dto.User;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SendEmailHelper {
    private Context mContext;
    private User mCurrentUser;
    private DateTimeFormatter mDateFormatter;
    private Product product;

    public SendEmailHelper(Context context, Product productDao, User currentUser) {
        this.mContext = context;
        this.product = productDao;
        this.mCurrentUser = currentUser;
    }

    public void sendEmail() {
        Intent sendEmailIntent = new Intent("android.intent.action.SEND");
        sendEmailIntent.setType(this.mContext.getString(R.string.email_rfc));
        sendEmailIntent.putExtra("android.intent.extra.EMAIL", new String[]{this.mContext.getString(R.string.email_address)});
        sendEmailIntent.putExtra("android.intent.extra.SUBJECT", this.mContext.getString(R.string.email_subject));
        setRightDataFormatter();
        sendEmailIntent.putExtra("android.intent.extra.TEXT", addBodyInfo());
        try {
            this.mContext.startActivity(Intent.createChooser(sendEmailIntent, this.mContext.getString(R.string.email_send_email)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this.mContext.getApplicationContext(), R.string.email_no_email_installed, 0).show();
        }
    }

    private String addBodyInfo() {
        String version = this.mContext.getString(R.string.email_version, new Object[]{""});
        String build = this.mContext.getString(R.string.email_build, new Object[]{""});
        try {
            PackageInfo pInfo = this.mContext.getApplicationContext().getPackageManager().getPackageInfo(this.mContext.getApplicationContext().getPackageName(), 0);
            version = this.mContext.getString(R.string.email_version, new Object[]{pInfo.versionName});
            build = this.mContext.getString(R.string.email_build, new Object[]{Integer.valueOf(pInfo.versionCode)});
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String app = this.mContext.getString(R.string.email_app, new Object[]{this.mContext.getString(R.string.app_name)});
        String model = this.mContext.getString(R.string.email_device, new Object[]{Build.MODEL});
        String androidVersion = this.mContext.getString(R.string.email_android_version, new Object[]{Integer.valueOf(Build.VERSION.SDK_INT)});
        String lastSycnStatus = this.mContext.getString(R.string.email_last_sync_status, new Object[]{getProductStatus(this.product)});
        String lastSync = this.mContext.getString(R.string.email_last_sync, new Object[]{getProductLast(this.product)});
        String deviceStatus = this.mContext.getString(R.string.email_device_status, new Object[]{getDeviceStatus()});
        String hardwareVariant = this.mContext.getString(R.string.email_hardware_variant, new Object[]{getHardwareVariant(this.product)});
        String manufacturer = this.mContext.getString(R.string.email_manufaturer, new Object[]{getManufacturer(this.product)});
        String productModel = this.mContext.getString(R.string.email_product_model, new Object[]{getProductModel(this.product)});
        String firmwareModel = this.mContext.getString(R.string.email_firmware_model, new Object[]{getFirmware(this.product)});
        return this.mContext.getString(R.string.email_body, new Object[]{app, version, build, model, androidVersion, lastSycnStatus, lastSync, deviceStatus, hardwareVariant, manufacturer, productModel, firmwareModel});
    }

    private void setRightDataFormatter() {
        if (DateFormat.is24HourFormat(this.mContext.getApplicationContext())) {
            this.mDateFormatter = DateTimeFormat.forPattern(this.mContext.getString(R.string.email_date_format24));
        } else {
            this.mDateFormatter = DateTimeFormat.forPattern(this.mContext.getString(R.string.device_info_date_format));
        }
    }

    private String getDeviceStatus() {
        if (this.mCurrentUser != null) {
            return this.mContext.getString(R.string.device_info_status_value);
        }
        return this.mContext.getString(R.string.device_info_not_paired_omni_trainer);
    }

    private String getProductStatus(Product product2) {
        if (product2 != null) {
            if (product2.getmLastSyncStatus().equals(SyncStatus.SUCCESSFUL)) {
                return this.mContext.getString(R.string.device_info_successful_pairing);
            }
            if (product2.getmLastSyncStatus().equals(SyncStatus.SUCCESSFUL_NO_WORKOUTS)) {
                return this.mContext.getString(R.string.device_info_successful_no_new_workouts);
            }
            if (product2.getmLastSyncStatus().equals(SyncStatus.FAILED)) {
                return this.mContext.getString(R.string.device_info_failed_pairing);
            }
            if (product2.getmLastSyncStatus().equals(SyncStatus.UNABLE_TO_CONNECT)) {
                return this.mContext.getString(R.string.device_info_unabled_to_connect);
            }
        }
        return "";
    }

    private String getProductLast(Product product2) {
        if (product2 != null) {
            return this.mDateFormatter.print((ReadableInstant) product2.getmLastSync());
        }
        return "";
    }

    private String getHardwareVariant(Product product2) {
        if (product2 != null) {
            return product2.getmHardwareVariant();
        }
        return "";
    }

    private String getManufacturer(Product product2) {
        if (product2 != null) {
            return product2.getmManufacturer();
        }
        return "";
    }

    private String getFirmware(Product product2) {
        if (product2 != null) {
            return product2.getmFirmwareVersion();
        }
        return "";
    }

    private String getProductModel(Product product2) {
        if (product2 != null) {
            return product2.getmProductModelName();
        }
        return "";
    }
}
