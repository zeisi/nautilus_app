package com.nautilus.omni.appmodules.journal.adapters;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.GoalType;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.Util;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JournalDayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String ACHIEVEMENT_ID = "achievement";
    private static final String AWARD_ID = "award";
    private static final int AWARD_ROW_VIEW_TYPE = 3;
    private static final String DATE_ID = "date";
    private static final int DATE_ROW_VIEW_TYPE = 1;
    private static final int DIVIDER = 5;
    public static final String DIVIDER_ID = "divider";
    private static final int GOAL_ROW_VIEW_TYPE = 2;
    private static final String WORKOUT_ID = "workout";
    private static final int WORKOUT_ROW_VIEW_TYPE = 4;
    private String mCalorieGoalTitle;
    private Context mContext;
    private List<Object> mDataList = new ArrayList();
    private DateTimeFormatter mDateFormatter;
    private SparseBooleanArray mSelectedItems;
    private String mWorkoutNumberGoalTitle;
    private String mWorkoutTimeGoalTitle;

    public class DateRowViewHolder_ViewBinding implements Unbinder {
        private DateRowViewHolder target;

        @UiThread
        public DateRowViewHolder_ViewBinding(DateRowViewHolder target2, View source) {
            this.target = target2;
            target2.mTextViewDate = (TextView) Utils.findRequiredViewAsType(source, R.id.day_date_text_view, "field 'mTextViewDate'", TextView.class);
        }

        @CallSuper
        public void unbind() {
            DateRowViewHolder target2 = this.target;
            if (target2 == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            target2.mTextViewDate = null;
        }
    }

    public class AchievementRowViewHolder_ViewBinding implements Unbinder {
        private AchievementRowViewHolder target;

        @UiThread
        public AchievementRowViewHolder_ViewBinding(AchievementRowViewHolder target2, View source) {
            this.target = target2;
            target2.mAchievementImageView = (ImageView) Utils.findRequiredViewAsType(source, R.id.achievement_row_image_view, "field 'mAchievementImageView'", ImageView.class);
            target2.mAchievementMessageTextView = (TextView) Utils.findRequiredViewAsType(source, R.id.achievement_row_message_text_view, "field 'mAchievementMessageTextView'", TextView.class);
        }

        @CallSuper
        public void unbind() {
            AchievementRowViewHolder target2 = this.target;
            if (target2 == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            target2.mAchievementImageView = null;
            target2.mAchievementMessageTextView = null;
        }
    }

    public class GoalRowViewHolder_ViewBinding implements Unbinder {
        private GoalRowViewHolder target;

        @UiThread
        public GoalRowViewHolder_ViewBinding(GoalRowViewHolder target2, View source) {
            this.target = target2;
            target2.mTextViewGoalTitle = (TextView) Utils.findRequiredViewAsType(source, R.id.goal_title_text_view, "field 'mTextViewGoalTitle'", TextView.class);
            target2.mTextViewGoalValue = (TextView) Utils.findRequiredViewAsType(source, R.id.goal_value_text_view, "field 'mTextViewGoalValue'", TextView.class);
            target2.mStarImageView = (ImageView) Utils.findRequiredViewAsType(source, R.id.goal_icon_image_view, "field 'mStarImageView'", ImageView.class);
        }

        @CallSuper
        public void unbind() {
            GoalRowViewHolder target2 = this.target;
            if (target2 == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            target2.mTextViewGoalTitle = null;
            target2.mTextViewGoalValue = null;
            target2.mStarImageView = null;
        }
    }

    public class WorkoutRowViewHolder_ViewBinding implements Unbinder {
        private WorkoutRowViewHolder target;

        @UiThread
        public WorkoutRowViewHolder_ViewBinding(WorkoutRowViewHolder target2, View source) {
            this.target = target2;
            target2.mWorkoutTitle = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_workout_program_name, "field 'mWorkoutTitle'", TextView.class);
            target2.mWorkoutValue = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_workout_program_value, "field 'mWorkoutValue'", TextView.class);
            target2.mInfoImageView = (ImageView) Utils.findRequiredViewAsType(source, R.id.workout_info_image_view, "field 'mInfoImageView'", ImageView.class);
            target2.mWorkoutId = (TextView) Utils.findRequiredViewAsType(source, R.id.workout_id_text_view, "field 'mWorkoutId'", TextView.class);
        }

        @CallSuper
        public void unbind() {
            WorkoutRowViewHolder target2 = this.target;
            if (target2 == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.target = null;
            target2.mWorkoutTitle = null;
            target2.mWorkoutValue = null;
            target2.mInfoImageView = null;
            target2.mWorkoutId = null;
        }
    }

    public static class DateRowViewHolder extends RecyclerView.ViewHolder {
        @BindView(2131755360)
        public TextView mTextViewDate;

        public DateRowViewHolder(View view) {
            super(view);
            ButterKnife.bind((Object) this, view);
        }
    }

    public static class GoalRowViewHolder extends RecyclerView.ViewHolder {
        @BindView(2131755361)
        public ImageView mStarImageView;
        @BindView(2131755362)
        public TextView mTextViewGoalTitle;
        @BindView(2131755363)
        public TextView mTextViewGoalValue;

        public GoalRowViewHolder(View view) {
            super(view);
            ButterKnife.bind((Object) this, view);
        }
    }

    public static class AchievementRowViewHolder extends RecyclerView.ViewHolder {
        @BindView(2131755358)
        public ImageView mAchievementImageView;
        @BindView(2131755359)
        public TextView mAchievementMessageTextView;

        public AchievementRowViewHolder(View view) {
            super(view);
            ButterKnife.bind((Object) this, view);
        }
    }

    public static class WorkoutRowViewHolder extends RecyclerView.ViewHolder {
        @BindView(2131755370)
        public ImageView mInfoImageView;
        public Workout mWorkout;
        @BindView(2131755371)
        public TextView mWorkoutId;
        @BindView(2131755367)
        public TextView mWorkoutTitle;
        @BindView(2131755369)
        public TextView mWorkoutValue;

        public WorkoutRowViewHolder(View view) {
            super(view);
            ButterKnife.bind((Object) this, view);
        }
    }

    public static class DividerRowViewHolder extends RecyclerView.ViewHolder {
        public DividerRowViewHolder(View view) {
            super(view);
        }
    }

    public JournalDayAdapter(Context context) {
        this.mContext = context;
        this.mSelectedItems = new SparseBooleanArray();
        this.mDateFormatter = DateTimeFormat.forPattern(this.mContext.getString(R.string.journal_date_extended));
        this.mCalorieGoalTitle = this.mContext.getString(R.string.journal_day_calorie_goal_title);
        this.mWorkoutNumberGoalTitle = this.mContext.getString(R.string.journal_day_workout_number_goal_title);
        this.mWorkoutTimeGoalTitle = this.mContext.getString(R.string.journal_day_workout_time_goal_title);
    }

    public int getItemViewType(int position) {
        if (this.mDataList.get(position) instanceof DateTime) {
            return 1;
        }
        if (this.mDataList.get(position) instanceof Achievement) {
            return 2;
        }
        if (this.mDataList.get(position) instanceof Award) {
            return 3;
        }
        if (this.mDataList.get(position) instanceof Workout) {
            return 4;
        }
        return 5;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new DateRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_day_date_row, parent, false));
            case 2:
                return new GoalRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_day_goal_row, parent, false));
            case 3:
                return new AchievementRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_day_achievement_row, parent, false));
            case 4:
                return new WorkoutRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_day_workout_removable_row, parent, false));
            case 5:
                return new DividerRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_day_divider_row, parent, false));
            default:
                return null;
        }
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setActivated(this.mSelectedItems.get(position, false));
        if (getItemViewType(position) == 1) {
            addDateRow((DateRowViewHolder) holder, (DateTime) this.mDataList.get(position));
        } else if (getItemViewType(position) == 2) {
            addGoalRow((GoalRowViewHolder) holder, (Achievement) this.mDataList.get(position));
        } else if (getItemViewType(position) == 4) {
            addWorkoutRow((WorkoutRowViewHolder) holder, (Workout) this.mDataList.get(position));
        }
    }

    private void addDateRow(DateRowViewHolder viewHolder, DateTime date) {
        viewHolder.mTextViewDate.setText(date.toString(this.mDateFormatter));
    }

    private void addGoalRow(GoalRowViewHolder viewHolder, Achievement achievement) {
        if (achievement.getmGoalType().equals(GoalType.CALORIES_PER_WORKOUT)) {
            viewHolder.mTextViewGoalTitle.setText(this.mCalorieGoalTitle);
        } else if (achievement.getmGoalType().equals(GoalType.WORKOUTS_PER_WEEK)) {
            viewHolder.mTextViewGoalTitle.setText(this.mWorkoutNumberGoalTitle);
        } else if (achievement.getmGoalType().equals(GoalType.TIME_PER_WORKOUT)) {
            viewHolder.mTextViewGoalTitle.setText(this.mWorkoutTimeGoalTitle);
        }
        viewHolder.mTextViewGoalValue.setText(achievement.getmGoalAchievedValue());
    }

    private void addWorkoutRow(WorkoutRowViewHolder viewHolder, Workout workout) {
        viewHolder.mWorkoutTitle.setText(Util.convertSecondsToDuration((long) workout.getmElapsedTime(), true));
        viewHolder.mWorkoutValue.setText(getWorkoutProgramCalorieValue(workout));
        viewHolder.mWorkout = workout;
        viewHolder.mWorkoutId.setText(String.valueOf(workout.getmId()));
    }

    private String getWorkoutProgramCalorieValue(Workout workout) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(workout.getmCalories()).append(Constants.EMPTY_SPACE).append(this.mContext.getString(R.string.journal_day_calories_indicator));
        return stringBuilder.toString();
    }

    public int getItemCount() {
        return this.mDataList.size();
    }

    public void updateWorkoutsList(List<Object> dataList) {
        this.mDataList = new ArrayList(dataList);
        notifyDataSetChanged();
    }

    public void toggleSelection(int pos) {
        if (this.mSelectedItems.get(pos, false)) {
            this.mSelectedItems.delete(pos);
        } else {
            this.mSelectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        this.mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return this.mSelectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(this.mSelectedItems.size());
        for (int i = 0; i < this.mSelectedItems.size(); i++) {
            items.add(Integer.valueOf(this.mSelectedItems.keyAt(i)));
        }
        return items;
    }

    public void removeData(int position) {
        this.mDataList.remove(position);
        if (isDividerCell(position)) {
            this.mDataList.remove(position);
        }
        notifyItemRemoved(position);
    }

    private boolean isDividerCell(int position) {
        return this.mDataList.get(position) instanceof String;
    }

    public long getItemId(int position) {
        if (this.mDataList.get(position) instanceof Workout) {
            return (long) ("workout" + ((Workout) this.mDataList.get(position)).getmWorkoutDate().getMillis() + ((Workout) this.mDataList.get(position)).getmId()).hashCode();
        }
        if (this.mDataList.get(position) instanceof Achievement) {
            return (long) (ACHIEVEMENT_ID + ((Achievement) this.mDataList.get(position)).getmGoalAchievementDate().getMillis() + ((Achievement) this.mDataList.get(position)).getmId()).hashCode();
        }
        if (this.mDataList.get(position) instanceof DateTime) {
            return (long) ("date" + ((DateTime) this.mDataList.get(position)).getMillis()).hashCode();
        }
        if (this.mDataList.get(position) instanceof Award) {
            return (long) ("award" + ((Award) this.mDataList.get(position)).getmId()).hashCode();
        }
        return (long) (DIVIDER_ID + position).hashCode();
    }

    public List<Object> getmDataList() {
        return this.mDataList;
    }
}
