package org.acra.dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.acra.ACRA;
import org.acra.prefs.PrefUtils;
import org.acra.prefs.SharedPreferencesFactory;

public class CrashReportDialog extends BaseCrashReportDialog implements DialogInterface.OnClickListener {
    private static final int PADDING = 10;
    private static final String STATE_COMMENT = "comment";
    private static final String STATE_EMAIL = "email";
    private AlertDialog mDialog;
    private LinearLayout scrollable;
    private SharedPreferencesFactory sharedPreferencesFactory;
    private EditText userCommentView;
    private EditText userEmailView;

    /* access modifiers changed from: protected */
    @CallSuper
    public void init(@Nullable Bundle savedInstanceState) {
        this.scrollable = new LinearLayout(this);
        this.scrollable.setOrientation(1);
        this.sharedPreferencesFactory = new SharedPreferencesFactory(getApplicationContext(), getConfig());
        int themeResourceId = getConfig().resDialogTheme();
        if (themeResourceId != 0) {
            setTheme(themeResourceId);
        }
        buildAndShowDialog(savedInstanceState);
    }

    /* access modifiers changed from: protected */
    public void buildAndShowDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        int titleResourceId = getConfig().resDialogTitle();
        if (titleResourceId != 0) {
            dialogBuilder.setTitle(titleResourceId);
        }
        int iconResourceId = getConfig().resDialogIcon();
        if (iconResourceId != 0) {
            dialogBuilder.setIcon(iconResourceId);
        }
        dialogBuilder.setView(buildCustomView(savedInstanceState)).setPositiveButton(getText(getConfig().resDialogPositiveButtonText()), this).setNegativeButton(getText(getConfig().resDialogNegativeButtonText()), this);
        this.mDialog = dialogBuilder.create();
        this.mDialog.setCanceledOnTouchOutside(false);
        this.mDialog.show();
    }

    /* access modifiers changed from: protected */
    @NonNull
    public View buildCustomView(@Nullable Bundle savedInstanceState) {
        ScrollView root = new ScrollView(this);
        root.setPadding(10, 10, 10, 10);
        root.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);
        root.addView(this.scrollable);
        addViewToDialog(getMainView());
        View comment = getCommentLabel();
        if (comment != null) {
            comment.setPadding(comment.getPaddingLeft(), 10, comment.getPaddingRight(), comment.getPaddingBottom());
            addViewToDialog(comment);
            String savedComment = null;
            if (savedInstanceState != null) {
                savedComment = savedInstanceState.getString(STATE_COMMENT);
            }
            this.userCommentView = getCommentPrompt(savedComment);
            addViewToDialog(this.userCommentView);
        }
        View email = getEmailLabel();
        if (email != null) {
            email.setPadding(email.getPaddingLeft(), 10, email.getPaddingRight(), email.getPaddingBottom());
            addViewToDialog(email);
            String savedEmail = null;
            if (savedInstanceState != null) {
                savedEmail = savedInstanceState.getString("email");
            }
            this.userEmailView = getEmailPrompt(savedEmail);
            addViewToDialog(this.userEmailView);
        }
        return root;
    }

    /* access modifiers changed from: protected */
    public final void addViewToDialog(@NonNull View v) {
        this.scrollable.addView(v);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public View getMainView() {
        TextView text = new TextView(this);
        int dialogTextId = getConfig().resDialogText();
        if (dialogTextId != 0) {
            text.setText(getText(dialogTextId));
        }
        return text;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public View getCommentLabel() {
        int commentPromptId = getConfig().resDialogCommentPrompt();
        if (commentPromptId == 0) {
            return null;
        }
        TextView labelView = new TextView(this);
        labelView.setText(getText(commentPromptId));
        return labelView;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public EditText getCommentPrompt(@Nullable CharSequence savedComment) {
        EditText userCommentView2 = new EditText(this);
        userCommentView2.setLines(2);
        if (savedComment != null) {
            userCommentView2.setText(savedComment);
        }
        return userCommentView2;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public View getEmailLabel() {
        int emailPromptId = getConfig().resDialogEmailPrompt();
        if (emailPromptId == 0) {
            return null;
        }
        TextView labelView = new TextView(this);
        labelView.setText(getText(emailPromptId));
        return labelView;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public EditText getEmailPrompt(@Nullable CharSequence savedEmail) {
        EditText userEmailView2 = new EditText(this);
        userEmailView2.setSingleLine();
        userEmailView2.setInputType(33);
        if (savedEmail != null) {
            userEmailView2.setText(savedEmail);
        } else {
            userEmailView2.setText(this.sharedPreferencesFactory.create().getString(ACRA.PREF_USER_EMAIL_ADDRESS, ""));
        }
        return userEmailView2;
    }

    public void onClick(DialogInterface dialog, int which) {
        String userEmail;
        if (which == -1) {
            String comment = this.userCommentView != null ? this.userCommentView.getText().toString() : "";
            SharedPreferences prefs = this.sharedPreferencesFactory.create();
            if (this.userEmailView != null) {
                userEmail = this.userEmailView.getText().toString();
                SharedPreferences.Editor prefEditor = prefs.edit();
                prefEditor.putString(ACRA.PREF_USER_EMAIL_ADDRESS, userEmail);
                PrefUtils.save(prefEditor);
            } else {
                userEmail = prefs.getString(ACRA.PREF_USER_EMAIL_ADDRESS, "");
            }
            sendCrash(comment, userEmail);
        } else {
            cancelReports();
        }
        finish();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!(this.userCommentView == null || this.userCommentView.getText() == null)) {
            outState.putString(STATE_COMMENT, this.userCommentView.getText().toString());
        }
        if (this.userEmailView != null && this.userEmailView.getText() != null) {
            outState.putString("email", this.userEmailView.getText().toString());
        }
    }

    /* access modifiers changed from: protected */
    public AlertDialog getDialog() {
        return this.mDialog;
    }
}
