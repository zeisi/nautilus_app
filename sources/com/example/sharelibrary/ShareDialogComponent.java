package com.example.sharelibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.nautilus.omni.util.Constants;
import java.util.ArrayList;
import java.util.List;

public class ShareDialogComponent {
    /* access modifiers changed from: private */
    public BottomSheetDialog dialog;
    private final SheetAdapter mAdapter;
    private final Context mContext;
    private final View view = View.inflate(this.mContext, R.layout.sheet_main, (ViewGroup) null);

    public ShareDialogComponent(Context pContext) {
        this.mContext = pContext;
        this.mAdapter = new SheetAdapter(pContext, getResolveList());
        RecyclerView recyclerView = (RecyclerView) this.view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 4));
        recyclerView.setAdapter(this.mAdapter);
    }

    public void setOnPackageSelectListener(OnSelectPackageListener pOnPackageSelectListener) {
        this.mAdapter.setOnItemClickListener(pOnPackageSelectListener);
    }

    public void show() {
        this.dialog = new BottomSheetDialog(this.mContext);
        if (this.view.getParent() != null) {
            ((ViewGroup) this.view.getParent()).removeView(this.view);
        }
        this.dialog.setContentView(this.view);
        this.dialog.show();
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                BottomSheetDialog unused = ShareDialogComponent.this.dialog = null;
            }
        });
    }

    public void hide() {
        this.dialog.hide();
        this.dialog.dismiss();
    }

    private List<ResolveInfo> getResolveList() {
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.setType(Constants.JPEG_FORMAT);
        return this.mContext.getPackageManager().queryIntentActivities(shareIntent, 0);
    }

    private List<String> loadPackagesList() {
        List<String> packages = new ArrayList<>();
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.setType(Constants.JPEG_FORMAT);
        List<ResolveInfo> resInfos = this.mContext.getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resInfos.isEmpty()) {
            for (ResolveInfo resInfo : resInfos) {
                packages.add(resInfo.activityInfo.packageName);
            }
        }
        return packages;
    }
}
