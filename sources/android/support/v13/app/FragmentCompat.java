package android.support.v13.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.os.BuildCompat;
import java.util.Arrays;

@TargetApi(13)
@RequiresApi(13)
public class FragmentCompat {
    static final FragmentCompatImpl IMPL;

    interface FragmentCompatImpl {
        void requestPermissions(Fragment fragment, String[] strArr, int i);

        void setMenuVisibility(Fragment fragment, boolean z);

        void setUserVisibleHint(Fragment fragment, boolean z);

        boolean shouldShowRequestPermissionRationale(Fragment fragment, String str);
    }

    public interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr);
    }

    static class BaseFragmentCompatImpl implements FragmentCompatImpl {
        BaseFragmentCompatImpl() {
        }

        public void setMenuVisibility(Fragment f, boolean visible) {
        }

        public void setUserVisibleHint(Fragment f, boolean deferStart) {
        }

        public void requestPermissions(final Fragment fragment, final String[] permissions, final int requestCode) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    int[] grantResults = new int[permissions.length];
                    Context context = fragment.getActivity();
                    if (context != null) {
                        PackageManager packageManager = context.getPackageManager();
                        String packageName = context.getPackageName();
                        int permissionCount = permissions.length;
                        for (int i = 0; i < permissionCount; i++) {
                            grantResults[i] = packageManager.checkPermission(permissions[i], packageName);
                        }
                    } else {
                        Arrays.fill(grantResults, -1);
                    }
                    ((OnRequestPermissionsResultCallback) fragment).onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            });
        }

        public boolean shouldShowRequestPermissionRationale(Fragment fragment, String permission) {
            return false;
        }
    }

    static class ICSFragmentCompatImpl extends BaseFragmentCompatImpl {
        ICSFragmentCompatImpl() {
        }

        public void setMenuVisibility(Fragment f, boolean visible) {
            FragmentCompatICS.setMenuVisibility(f, visible);
        }
    }

    static class ICSMR1FragmentCompatImpl extends ICSFragmentCompatImpl {
        ICSMR1FragmentCompatImpl() {
        }

        public void setUserVisibleHint(Fragment f, boolean deferStart) {
            FragmentCompatICSMR1.setUserVisibleHint(f, deferStart);
        }
    }

    static class MncFragmentCompatImpl extends ICSMR1FragmentCompatImpl {
        MncFragmentCompatImpl() {
        }

        public void requestPermissions(Fragment fragment, String[] permissions, int requestCode) {
            FragmentCompat23.requestPermissions(fragment, permissions, requestCode);
        }

        public boolean shouldShowRequestPermissionRationale(Fragment fragment, String permission) {
            return FragmentCompat23.shouldShowRequestPermissionRationale(fragment, permission);
        }
    }

    static class NFragmentCompatImpl extends MncFragmentCompatImpl {
        NFragmentCompatImpl() {
        }

        public void setUserVisibleHint(Fragment f, boolean deferStart) {
            FragmentCompatApi24.setUserVisibleHint(f, deferStart);
        }
    }

    static {
        if (BuildCompat.isAtLeastN()) {
            IMPL = new NFragmentCompatImpl();
        } else if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new MncFragmentCompatImpl();
        } else if (Build.VERSION.SDK_INT >= 15) {
            IMPL = new ICSMR1FragmentCompatImpl();
        } else if (Build.VERSION.SDK_INT >= 14) {
            IMPL = new ICSFragmentCompatImpl();
        } else {
            IMPL = new BaseFragmentCompatImpl();
        }
    }

    public static void setMenuVisibility(Fragment f, boolean visible) {
        IMPL.setMenuVisibility(f, visible);
    }

    public static void setUserVisibleHint(Fragment f, boolean deferStart) {
        IMPL.setUserVisibleHint(f, deferStart);
    }

    public static void requestPermissions(@NonNull Fragment fragment, @NonNull String[] permissions, int requestCode) {
        IMPL.requestPermissions(fragment, permissions, requestCode);
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Fragment fragment, @NonNull String permission) {
        return IMPL.shouldShowRequestPermissionRationale(fragment, permission);
    }
}
