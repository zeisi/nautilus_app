package com.example.sharelibrary;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class SheetAdapter extends RecyclerView.Adapter<ItemHolder> {
    Context context;
    private List<ResolveInfo> list;
    PackageManager mPm;
    /* access modifiers changed from: private */
    public OnSelectPackageListener onSelectPackageListener;

    public SheetAdapter(Context context2, List<ResolveInfo> list2) {
        this.mPm = context2.getPackageManager();
        this.context = context2;
        this.list = list2;
    }

    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sheet_main, parent, false));
    }

    public void onBindViewHolder(final ItemHolder holder, int position) {
        holder.resolveInfo = this.list.get(position);
        holder.packageName = holder.resolveInfo.activityInfo.packageName;
        holder.view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SheetAdapter.this.onSelectPackageListener.onPackageSelect(holder.packageName, holder.resolveInfo);
            }
        });
        try {
            CharSequence appName = this.mPm.getApplicationLabel(this.mPm.getApplicationInfo(holder.packageName, 0));
            holder.imageView.setImageDrawable(this.mPm.getApplicationIcon(holder.packageName));
            holder.textView.setText(appName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.list.size();
    }

    public void setOnItemClickListener(OnSelectPackageListener pOnSelectPackageListener) {
        this.onSelectPackageListener = pOnSelectPackageListener;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        String packageName;
        ResolveInfo resolveInfo;
        TextView textView;
        View view;

        public ItemHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
