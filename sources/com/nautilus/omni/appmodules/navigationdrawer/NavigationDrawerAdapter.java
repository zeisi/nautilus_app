package com.nautilus.omni.appmodules.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nautilus.omni.R;
import com.nautilus.omni.app.Callbacks;
import java.util.ArrayList;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerViewHolder> {
    /* access modifiers changed from: private */
    public static Callbacks.NavDrawerCallback mNavDrawerCallback;
    private static ArrayList<ObjectDrawerItem> mObjectDrawerItemList;
    private Context mContext;

    public static class NavigationDrawerViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageViewItemIcon;
        public TextView mTextViewItemName;

        public NavigationDrawerViewHolder(View view) {
            super(view);
            this.mTextViewItemName = (TextView) view.findViewById(R.id.drawer_name);
            this.mImageViewItemIcon = (ImageView) view.findViewById(R.id.drawer_icon);
            initItemOnClickListener(view);
        }

        private void initItemOnClickListener(View view) {
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    NavigationDrawerAdapter.mNavDrawerCallback.onDrawerItemClicked(v, NavigationDrawerViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    public NavigationDrawerAdapter(Context context, ArrayList<ObjectDrawerItem> objectDrawerItemList, Activity activity) {
        this.mContext = context;
        mObjectDrawerItemList = objectDrawerItemList;
        try {
            mNavDrawerCallback = (Callbacks.NavDrawerCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Cast error");
        }
    }

    public NavigationDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NavigationDrawerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_item_row, parent, false));
    }

    public void onBindViewHolder(NavigationDrawerViewHolder holder, int position) {
        if (position == 0) {
            holder.mTextViewItemName.setText(mObjectDrawerItemList.get(position).getName());
            holder.mTextViewItemName.setTextColor(this.mContext.getResources().getColor(R.color.navigation_drawer_active_color));
            holder.mImageViewItemIcon.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), mObjectDrawerItemList.get(position).getActiveIcon(), (Resources.Theme) null));
            return;
        }
        holder.mTextViewItemName.setText(mObjectDrawerItemList.get(position).getName());
        holder.mImageViewItemIcon.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), mObjectDrawerItemList.get(position).getIcon(), (Resources.Theme) null));
    }

    public int getItemCount() {
        if (mObjectDrawerItemList != null) {
            return mObjectDrawerItemList.size();
        }
        return 0;
    }

    public void setmObjectDrawerItemList(ArrayList<ObjectDrawerItem> objectDrawerItemList) {
        mObjectDrawerItemList = objectDrawerItemList;
        notifyDataSetChanged();
    }
}
