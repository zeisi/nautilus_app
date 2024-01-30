package com.nautilus.omni.appmodules.awards.adapters.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.awards.AwardTypeEnum;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.model.dto.Award;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AwardDetailAdapter extends RecyclerView.Adapter<AwardDetailViewHolder> {
    private static final int HEADER_ROW_VIEW_TYPE = 0;
    private static final int ITEM_ROW_VIEW_TYPE = 1;
    private AwardValueBuilder mAwardValueBuilder;
    private List<Award> mAwardsList = new ArrayList();
    private boolean mIsBestWorkoutAward;
    private int mUnit;

    public AwardDetailAdapter(AwardValueBuilder awardValueBuilder) {
        this.mAwardValueBuilder = awardValueBuilder;
    }

    public AwardDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new AwardDetailHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_award_detail_header, parent, false), this.mAwardValueBuilder, this.mUnit);
            case 1:
                return new AwardDetailItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_award_detail_item, parent, false), this.mAwardValueBuilder);
            default:
                return null;
        }
    }

    public void onBindViewHolder(AwardDetailViewHolder holder, int position) {
        holder.loadView(position, this.mAwardsList.get(position), this.mIsBestWorkoutAward);
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    public int getItemCount() {
        return this.mAwardsList.size();
    }

    public void setmAwardsList(List<Award> awardsList, int unit) {
        this.mUnit = unit;
        this.mAwardsList = awardsList;
        if (isBestWorkoutAward()) {
            this.mAwardsList.addAll(removeAnyBestWorkoutAward(awardsList.get(0).getmWorkout().getmAwards()));
        }
        notifyDataSetChanged();
    }

    private boolean isBestWorkoutAward() {
        this.mIsBestWorkoutAward = this.mAwardsList.get(0).getmAwardType().getmName().equals(AwardTypeEnum.BEST_WORKOUT.toString());
        return this.mIsBestWorkoutAward;
    }

    private ArrayList<Award> removeAnyBestWorkoutAward(Collection<Award> awardsList) {
        ArrayList<Award> newAwardList = new ArrayList<>();
        for (Award award : awardsList) {
            if (!award.getmAwardType().getmName().equals(AwardTypeEnum.BEST_WORKOUT.toString())) {
                newAwardList.add(award);
            }
        }
        return newAwardList;
    }
}
