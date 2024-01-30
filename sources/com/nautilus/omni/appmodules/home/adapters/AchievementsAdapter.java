package com.nautilus.omni.appmodules.home.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.awards.adapters.detail.AwardHeaderBuilder;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.GoalType;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;

public class AchievementsAdapter extends BaseAdapter {
    List<Achievement> achievements = new ArrayList();
    List<Award> awards = new ArrayList();
    /* access modifiers changed from: private */
    public AwardHeaderBuilder mAwardHeaderBuilder;
    private AwardValueBuilder mAwardValueBuilder;
    private Context mContext;
    private int mUnit;

    public AchievementsAdapter(Context context, AwardValueBuilder awardValueBuilder) {
        this.mContext = context;
        this.mAwardValueBuilder = awardValueBuilder;
        this.mAwardHeaderBuilder = new AwardHeaderBuilder(this.mContext, this.mAwardValueBuilder, this.mUnit);
    }

    public void setmUnit(int unit) {
        this.mUnit = unit;
        this.mAwardHeaderBuilder.setUnit(this.mUnit);
        this.mAwardHeaderBuilder.setUnit(this.mUnit);
        notifyDataSetChanged();
    }

    public void setAchievementsAndAwards(List<Award> pAwards, List<Achievement> pAchievements, int pUnit) {
        this.mUnit = pUnit;
        this.awards = pAwards;
        this.achievements = pAchievements;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.awards.size() + this.achievements.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AchievementsViewHolder achivementsViewHolder;
        if (convertView != null) {
            achivementsViewHolder = (AchievementsViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_achievement, parent, false);
            achivementsViewHolder = new AchievementsViewHolder(convertView);
            convertView.setTag(achivementsViewHolder);
        }
        if (position < this.achievements.size()) {
            achivementsViewHolder.setAchievement(this.achievements.get(position), parent.getContext());
        } else {
            achivementsViewHolder.setAward(this.awards.get(position - this.achievements.size()), parent.getContext());
        }
        return convertView;
    }

    public class AchievementsViewHolder {
        Achievement achievement;
        Award award;
        ImageView imageViewAchievementsRow;
        View itemView;
        TextView textViewHomeDateOfAchievement;
        TextView textViewHomeNameAchievement;

        public AchievementsViewHolder(View itemView2) {
            this.itemView = itemView2;
            this.imageViewAchievementsRow = (ImageView) itemView2.findViewById(R.id.imageViewAchievementsRow);
            this.textViewHomeNameAchievement = (TextView) itemView2.findViewById(R.id.textViewHomeNameAchievement);
            this.textViewHomeDateOfAchievement = (TextView) itemView2.findViewById(R.id.textViewHomeDateOfAchievement);
        }

        public void setAward(Award award2, Context context) {
            this.award = award2;
            this.imageViewAchievementsRow.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), award2.getmAwardType().getmMediumImage(), (Resources.Theme) null));
            this.textViewHomeDateOfAchievement.setText(DateTimeFormat.forPattern(context.getResources().getString(R.string.home_date_achievements)).print((ReadableInstant) award2.getmDate()));
            setAwardValue();
        }

        private void setAwardValue() {
            this.textViewHomeNameAchievement.setText(AchievementsAdapter.this.mAwardHeaderBuilder.getAwardHeaderValue(this.award).toUpperCase());
        }

        public void setAchievement(Achievement achievement2, Context context) {
            this.achievement = achievement2;
            this.imageViewAchievementsRow.setImageResource(R.drawable.ic_goal_achieved_award);
            this.textViewHomeDateOfAchievement.setText(DateTimeFormat.forPattern(context.getResources().getString(R.string.home_date_achievements)).print((ReadableInstant) achievement2.getmGoalAchievementDate()));
            this.textViewHomeNameAchievement.setText(getStringForAchievement(achievement2.getmGoalType(), context).toUpperCase());
        }

        public String getStringForAchievement(GoalType goalType, Context ctx) {
            if (goalType.equals(GoalType.CALORIES_PER_WORKOUT)) {
                return ctx.getString(R.string.home_won_calorie_goal_title);
            }
            if (goalType.equals(GoalType.WORKOUTS_PER_WEEK)) {
                return ctx.getString(R.string.home_won_workout_number_goal_title);
            }
            return ctx.getString(R.string.home_won_workout_time_goal_title);
        }
    }
}
