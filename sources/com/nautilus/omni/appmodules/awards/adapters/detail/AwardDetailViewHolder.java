package com.nautilus.omni.appmodules.awards.adapters.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.nautilus.omni.model.dto.Award;

public abstract class AwardDetailViewHolder extends RecyclerView.ViewHolder {
    public abstract void loadView(int i, Award award, boolean z);

    public AwardDetailViewHolder(View itemView) {
        super(itemView);
    }
}
