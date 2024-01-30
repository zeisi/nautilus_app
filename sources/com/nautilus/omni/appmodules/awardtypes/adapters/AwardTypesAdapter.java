package com.nautilus.omni.appmodules.awardtypes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.AwardType;
import java.util.ArrayList;
import java.util.List;

public class AwardTypesAdapter extends RecyclerView.Adapter<AwardTypesViewHolder> {
    private AwardValueBuilder mAwardValueBuilder;
    private List<Object> mAwardsList = new ArrayList();
    private int mUnit;

    public AwardTypesAdapter(AwardValueBuilder awardValueBuilder) {
        this.mAwardValueBuilder = awardValueBuilder;
    }

    public AwardTypesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AwardTypesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_award_type_item, parent, false), this.mAwardValueBuilder);
    }

    public void onBindViewHolder(AwardTypesViewHolder holder, int position) {
        if (this.mAwardsList.get(position) instanceof Award) {
            holder.bind((AwardType) null, (Award) this.mAwardsList.get(position));
        } else {
            holder.bind((AwardType) this.mAwardsList.get(position), (Award) null);
        }
    }

    public int getItemCount() {
        return this.mAwardsList.size();
    }

    public void setmAwardsList(List<Object> mAwardsList2, int unit) {
        this.mUnit = unit;
        this.mAwardValueBuilder.setUnit(this.mUnit);
        this.mAwardsList = mAwardsList2;
        notifyDataSetChanged();
    }
}
