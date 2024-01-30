package com.buddybuild.sdk.feedback;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.buddybuild.sdk.R;
import com.buddybuild.sdk.feedback.SendFeedbackAsyncTask;
import com.buddybuild.sdk.utils.SystemUtils;

public class FeedbackDialog extends Dialog {
    /* access modifiers changed from: private */
    public final Activity mActivity;
    /* access modifiers changed from: private */
    public final Bitmap mBitmap;
    /* access modifiers changed from: private */
    public final EditText mFeedbackEditText = ((EditText) findViewById(R.id.bb_feedback_edit_text));

    public FeedbackDialog(Activity activity, Bitmap bitmap) {
        super(activity, R.style.bb_dialog_feedback);
        this.mActivity = activity;
        this.mBitmap = bitmap;
        setCancelable(false);
        setContentView(R.layout.bb_feedback_dialog);
        getWindow().setLayout(-1, -1);
        ((ImageView) findViewById(R.id.bb_screenshot_image_view)).setImageBitmap(bitmap);
        init();
    }

    public void init() {
        setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode != 4) {
                    return true;
                }
                FeedbackDialog.this.dismiss();
                FeedbackDialog.this.mActivity.finish();
                return true;
            }
        });
        findViewById(R.id.bb_cancel_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FeedbackDialog.this.dismiss();
                FeedbackDialog.this.mActivity.finish();
            }
        });
        findViewById(R.id.bb_send_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SendFeedbackAsyncTask.FeedbackInfo feedbackInfo = new SendFeedbackAsyncTask.FeedbackInfo(FeedbackDialog.this.mFeedbackEditText.getText().toString(), FeedbackDialog.this.mBitmap, SystemUtils.getSystemInfo(FeedbackDialog.this.mActivity));
                new SendFeedbackAsyncTask().execute(new SendFeedbackAsyncTask.FeedbackInfo[]{feedbackInfo});
                FeedbackDialog.this.mActivity.finish();
            }
        });
    }
}
