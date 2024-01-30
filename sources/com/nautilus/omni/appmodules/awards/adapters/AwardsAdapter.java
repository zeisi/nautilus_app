package com.nautilus.omni.appmodules.awards.adapters;

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
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.AwardType;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AwardsAdapter extends RecyclerView.Adapter<AwardsViewHolder> {
    private AwardValueBuilder mAwardValueBuilder;
    private List<Award> mAwardsList = new ArrayList();
    private Context mContext;
    private DateTimeFormatter mDateFormatter = DateTimeFormat.forPattern(this.mContext.getString(R.string.award_date_format));
    /* access modifiers changed from: private */
    public OnItemClickListener mListener;
    private int mUnit;

    public interface OnItemClickListener {
        void onItemClicked(Award award);
    }

    public static class AwardsViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImgViewAwardCircle;
        public ImageView mImgViewAwardIcon;
        public Award mItem;
        public TextView mTxtViewAwardDate;
        public TextView mTxtViewAwardValue;
        public View mView;

        public AwardsViewHolder(View view) {
            super(view);
            this.mView = view;
            this.mImgViewAwardCircle = (ImageView) view.findViewById(R.id.image_view_award_circle);
            this.mImgViewAwardIcon = (ImageView) view.findViewById(R.id.image_view_award_type_icon);
            this.mTxtViewAwardValue = (TextView) view.findViewById(R.id.text_view_award_value);
            this.mTxtViewAwardDate = (TextView) view.findViewById(R.id.text_view_header_award_date);
        }
    }

    public AwardsAdapter(Context context, OnItemClickListener listener, AwardValueBuilder awardValueBuilder) {
        this.mContext = context;
        this.mListener = listener;
        this.mAwardValueBuilder = awardValueBuilder;
    }

    public AwardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AwardsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_awards, parent, false));
    }

    public void onBindViewHolder(AwardsViewHolder holder, int position) {
        holder.mItem = this.mAwardsList.get(position);
        if (holder.mItem.getmAwardType() != null) {
            buildAwardCircle(holder);
        } else {
            buildEmptyAwardCircle(holder);
        }
    }

    private void buildAwardCircle(AwardsViewHolder holder) {
        AwardType awardType = holder.mItem.getmAwardType();
        holder.mImgViewAwardCircle.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), awardType.getmCircle(), (Resources.Theme) null));
        holder.mImgViewAwardIcon.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), awardType.getmSmallImage(), (Resources.Theme) null));
        holder.mTxtViewAwardDate.setText(holder.mItem.getmDate().toString(this.mDateFormatter));
        buildAwardValue(holder);
        holder.mImgViewAwardIcon.setVisibility(0);
        holder.mTxtViewAwardDate.setVisibility(0);
    }

    private void buildAwardValue(final AwardsViewHolder holder) {
        holder.mTxtViewAwardValue.setVisibility(0);
        holder.mTxtViewAwardValue.setText(this.mAwardValueBuilder.getAwardValue(holder.mItem));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (AwardsAdapter.this.mListener != null && v != null && v.getId() != R.id.container_awards_list) {
                    AwardsAdapter.this.mListener.onItemClicked(holder.mItem);
                }
            }
        });
    }

    private void buildEmptyAwardCircle(AwardsViewHolder holder) {
        holder.mImgViewAwardCircle.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_grey_circle, (Resources.Theme) null));
        holder.mImgViewAwardIcon.setVisibility(8);
        holder.mTxtViewAwardDate.setVisibility(8);
        holder.mTxtViewAwardValue.setVisibility(8);
        holder.mView.setOnClickListener((View.OnClickListener) null);
    }

    public int getItemCount() {
        return this.mAwardsList.size();
    }

    public void setmAwardsList(List<Award> awardsList, int unit) {
        this.mUnit = unit;
        this.mAwardsList = awardsList;
        this.mAwardValueBuilder.setUnit(this.mUnit);
        notifyDataSetChanged();
    }
}
