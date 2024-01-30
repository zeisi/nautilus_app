package com.nautilus.omni.appmodules.mainactivity.presenter;

import android.os.Handler;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.goals.view.GoalsFragment;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.util.Util;
import java.lang.ref.WeakReference;

public class GoalsReminderToastViewBuilder {
    public static final int ANIMATION_DURATION = 2000;
    public static final int OUT_ANIMATION_DELAY = 5000;
    /* access modifiers changed from: private */
    public MainActivity mMainActivity = ((MainActivity) this.mMainActivityReference.get());
    private WeakReference<MainActivity> mMainActivityReference;

    public GoalsReminderToastViewBuilder(MainActivity mainActivity) {
        this.mMainActivityReference = new WeakReference<>(mainActivity);
    }

    public void showGoalsReminderToast() {
        View toastLayout = this.mMainActivity.getLayoutInflater().inflate(R.layout.custom_animated_toast, (ViewGroup) this.mMainActivity.findViewById(R.id.custom_animated_toast_layout_root));
        TextView toastTextView = (TextView) toastLayout.findViewById(R.id.custom_toast_text_view);
        SpannableString spannableString = new SpannableString(this.mMainActivity.getString(R.string.goals_reminder_message));
        ClickableSpan clickableSpan = new ClickableSpan() {
            public void onClick(View widget) {
                GoalsReminderToastViewBuilder.this.mMainActivity.changeFragmentWithTitle(GoalsReminderToastViewBuilder.this.mMainActivity.getString(R.string.title_goals), GoalsFragment.TAG, false);
                GoalsReminderToastViewBuilder.this.mMainActivity.mNavigationDrawerHelper.updateSelectedMenu(GoalsFragment.TAG, GoalsReminderToastViewBuilder.this.mMainActivity.mNavigationDrawerHelper.mNavigationDrawerList.getChildAt(1));
            }

            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
                ds.setColor(-1);
            }
        };
        toastTextView.setMovementMethod(LinkMovementMethod.getInstance());
        spannableString.setSpan(clickableSpan, spannableString.toString().indexOf("!") + 2, spannableString.length(), 33);
        toastTextView.setText(spannableString);
        Util.executeViewFadeInAnimation(this.mMainActivity, 2000, toastLayout);
        makeGoalsReminderOutAnimation(toastLayout);
    }

    private void makeGoalsReminderOutAnimation(final View toastLayout) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Util.executeViewFadeOutAnimation(GoalsReminderToastViewBuilder.this.mMainActivity.getApplicationContext(), 2000, toastLayout);
            }
        }, 5000);
    }
}
