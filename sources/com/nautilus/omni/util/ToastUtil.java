package com.nautilus.omni.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nautilus.omni.R;

public class ToastUtil {
    public static Toast createSuccessToast(Activity activity, String message, int duration) {
        return createToast(activity, message, ResourcesCompat.getColor(activity.getResources(), R.color.custom_toast_color, (Resources.Theme) null), ResourcesCompat.getColor(activity.getResources(), R.color.white, (Resources.Theme) null), ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_toast_checkmark, (Resources.Theme) null), duration);
    }

    public static Toast createIssueToast(Activity activity, String message, int duration) {
        return createToast(activity, message, ResourcesCompat.getColor(activity.getResources(), R.color.toast_sync_failed_color, (Resources.Theme) null), ResourcesCompat.getColor(activity.getResources(), R.color.black, (Resources.Theme) null), ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_clear_button, (Resources.Theme) null), duration);
    }

    public static Toast createSyncIssueToast(Activity activity, String message, int duration) {
        return createToast(activity, message, activity.getResources().getColor(R.color.toast_sync_failed_color), activity.getResources().getColor(R.color.black), ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_toast_bluetooth_grey, (Resources.Theme) null), duration);
    }

    public static Toast createToast(Activity activity, String message, int color, int textColor, Drawable drawable, int duration) {
        View toastLayout = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.custom_regular_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_layout_root));
        TextView toastTextView = (TextView) toastLayout.findViewById(R.id.custom_toast_text_view);
        toastTextView.setText(message);
        toastTextView.setTextColor(textColor);
        ImageView imageView = (ImageView) toastLayout.findViewById(R.id.custom_toast_image_view);
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setVisibility(8);
        }
        toastLayout.setBackgroundColor(color);
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(55, 0, 0);
        toast.setDuration(duration);
        toast.setView(toastLayout);
        return toast;
    }
}
