package com.myfitnesspal.android.sdk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import com.myfitnesspal.shared.utils.Ln;

public final class Util {
    private static boolean ENABLE_LOG = true;
    private static final String MFP_APP_SIGNATURE = "3082021930820182a00302010202044bd5ddbd300d06092a864886f70d01010505003051310b3009060355040613025553311330110603550408130a43616c69666f726e6961311230100603550407130953616e20446965676f31193017060355040313104d794669746e65737350616c204c4c43301e170d3130303432363138333835335a170d3335303432303138333835335a3051310b3009060355040613025553311330110603550408130a43616c69666f726e6961311230100603550407130953616e20446965676f31193017060355040313104d794669746e65737350616c204c4c4330819f300d06092a864886f70d010101050003818d00308189028181009c68d057f7f4d1544cc5a4c9a32a047c2cec9ed773a17830e91c462eb9dad832912dcdfce00cdf62c3276456d30053edaddeb7c326e75749a7569eb5031ebb60688959fcf27cac67364ee852c7a8ca99505e9c281bac69961d726ce0ba46dce798c6b43760a78560ab4c976f0bf38c6d7f43651929eba8ccef8f9961aa0d82870203010001300d06092a864886f70d01010505000381810086049aff894cb055b6d31357ab677491fc8650286dadc7588671a7be19a224b6bf9c1c3a1cb13c196e85bf0998e53e6f8af3b6312f87e1d062700d019facfd0b4efc87845b9971adf439bb1095accb4c015460db12e260f282120e5e7dd81075ebe0e80aeb468eb7477ce6891841cbcb2f6712dfa3ade3631a601e62d1b11ca6";

    public static void showAlert(Context context, String title, String text) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(text);
        alertBuilder.create().show();
    }

    public static boolean validateActivityIntent(Context context, Intent intent, boolean validateSignature) {
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveInfo == null) {
            Ln.d("Could not get resolve info", new Object[0]);
            return false;
        } else if (validateSignature) {
            return validateAppSignatureForPackage(context, resolveInfo.activityInfo.packageName);
        } else {
            return true;
        }
    }

    public static boolean validateAppSignatureForPackage(Context context, String packageName) {
        try {
            for (Signature signature : context.getPackageManager().getPackageInfo(packageName, 64).signatures) {
                if (signature.toCharsString().equals(MFP_APP_SIGNATURE)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isUriAvailable(Context context, String uri) {
        if (context.getPackageManager().resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse(uri)), 0) != null) {
            return true;
        }
        return false;
    }
}
