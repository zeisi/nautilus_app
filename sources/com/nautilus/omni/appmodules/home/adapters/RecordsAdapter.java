package com.nautilus.omni.appmodules.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.home.presenter.AchievementsPresenterContract;

public class RecordsAdapter extends BaseAdapter {
    private static final int RECORDS_LIST_SIZE = 10;
    /* access modifiers changed from: private */
    public AchievementsPresenterContract iAchievementsPresenter;
    Context mContext;
    /* access modifiers changed from: private */
    public int mUnit;

    public RecordsAdapter(Context context, int unit, AchievementsPresenterContract iAchievementsPresenter2) {
        this.mContext = context;
        this.mUnit = unit;
        this.iAchievementsPresenter = iAchievementsPresenter2;
    }

    public void setmUnit(int mUnit2) {
        this.mUnit = mUnit2;
        notifyDataSetChanged();
    }

    public int getCount() {
        return 10;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        AchievementsViewHolder achivementsViewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_record, parent, false);
            achivementsViewHolder = new AchievementsViewHolder(view);
            view.setTag(achivementsViewHolder);
        } else {
            view = convertView;
            achivementsViewHolder = (AchievementsViewHolder) view.getTag();
        }
        achivementsViewHolder.setPosition(position);
        return view;
    }

    public class AchievementsViewHolder {
        static final int HIGHEST_AVG_SPEED_PER_WEEK = 7;
        static final int HIGHEST_AVG_SPEED_PER_WORKOUT = 6;
        static final int HIGHEST_BURN_RATE_PER_WORKOUT = 2;
        static final int LONGEST_WORKOUT = 3;
        static final int MOST_CALORIES_BURNED_PER_WEEK = 1;
        static final int MOST_CALORIES_BURNED_PER_WORKOUT = 0;
        static final int MOST_DISTANCE_PER_WEEK = 9;
        static final int MOST_DISTANCE_PER_WORKOUT = 8;
        static final int MOST_WORKOUTS_PER_WEEK = 5;
        static final int MOST_WORKOUT_TIME_PER_WEEK = 4;
        View mView;
        TextView textViewSeparator;
        TextView textViewTitle;
        TextView textViewValue;

        public AchievementsViewHolder(View itemView) {
            this.mView = itemView;
            this.textViewTitle = (TextView) itemView.findViewById(R.id.textViewRecordRowTitle);
            this.textViewValue = (TextView) itemView.findViewById(R.id.textViewRecordRowValue);
            this.textViewSeparator = (TextView) itemView.findViewById(R.id.textViewRecordRowSeparator);
        }

        public void setPosition(int i) {
            setTitle(i);
            setColorSeparator(i);
            setValue(i);
        }

        public void setTitle(int position) {
            switch (position) {
                case 0:
                    this.textViewTitle.setText(R.string.home_records_most_calories_workout);
                    return;
                case 1:
                    this.textViewTitle.setText(R.string.home_records_most_calories_week);
                    return;
                case 2:
                    this.textViewTitle.setText(R.string.home_records_high_burn);
                    return;
                case 3:
                    this.textViewTitle.setText(R.string.home_records_longest);
                    return;
                case 4:
                    this.textViewTitle.setText(R.string.home_records_most_time);
                    return;
                case 5:
                    this.textViewTitle.setText(R.string.home_records_most_workouts_week);
                    return;
                case 6:
                    setAvgSpeedPerWorkoutTitle();
                    return;
                case 7:
                    setAvgSpeedPerWeekTitle();
                    return;
                case 8:
                    setMostDistancePerWorkoutTitle();
                    return;
                case 9:
                    setMostDistancePerWeekTitle();
                    return;
                default:
                    return;
            }
        }

        private void setAvgSpeedPerWorkoutTitle() {
            if (RecordsAdapter.this.mUnit == 0) {
                this.textViewTitle.setText(R.string.home_records_highest_avg_speed_workout_mph);
            } else {
                this.textViewTitle.setText(R.string.home_records_highest_avg_speed_workout_kph);
            }
        }

        private void setAvgSpeedPerWeekTitle() {
            if (RecordsAdapter.this.mUnit == 0) {
                this.textViewTitle.setText(R.string.home_records_highest_avg_speed_week_mph);
            } else {
                this.textViewTitle.setText(R.string.home_records_highest_avg_speed_week_kph);
            }
        }

        private void setMostDistancePerWorkoutTitle() {
            if (RecordsAdapter.this.mUnit == 0) {
                this.textViewTitle.setText(R.string.home_records_most_distance_workout_miles);
            } else {
                this.textViewTitle.setText(R.string.home_records_most_distance_workout_kilometers);
            }
        }

        private void setMostDistancePerWeekTitle() {
            if (RecordsAdapter.this.mUnit == 0) {
                this.textViewTitle.setText(R.string.home_records_most_distance_week_miles);
            } else {
                this.textViewTitle.setText(R.string.home_records_most_distance_week_kilometers);
            }
        }

        public void setValue(int position) {
            switch (position) {
                case 0:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getCaloriesBurnedPerWorkout());
                    return;
                case 1:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getMostCaloriesBurnedPerWeek());
                    return;
                case 2:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getHighestBurnRatePerWorkout());
                    return;
                case 3:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getLongestWorkout());
                    return;
                case 4:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getMostWorkoutTimePerWeek());
                    return;
                case 5:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getMostWorkoutsPerWeek());
                    return;
                case 6:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getHighestAvgSpeedPerWorkout(RecordsAdapter.this.mUnit));
                    return;
                case 7:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getHighestAvgSpeedPerWeek(RecordsAdapter.this.mUnit));
                    return;
                case 8:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getMostDistancePerWorkout(RecordsAdapter.this.mUnit));
                    return;
                case 9:
                    this.textViewValue.setText(RecordsAdapter.this.iAchievementsPresenter.getMostDistancePerWeek(RecordsAdapter.this.mUnit));
                    return;
                default:
                    return;
            }
        }

        public void setColorSeparator(int position) {
            switch (position) {
                case 0:
                case 1:
                case 2:
                    this.textViewSeparator.setBackgroundColor(this.mView.getResources().getColor(R.color.award_orange_color));
                    return;
                case 3:
                case 4:
                    this.textViewSeparator.setBackgroundColor(this.mView.getResources().getColor(R.color.award_purple_color));
                    return;
                case 5:
                    this.textViewSeparator.setBackgroundColor(this.mView.getResources().getColor(R.color.award_records_divider_light_blue_color));
                    return;
                case 6:
                case 7:
                    this.textViewSeparator.setBackgroundColor(this.mView.getResources().getColor(R.color.award_red_color));
                    return;
                case 8:
                case 9:
                    this.textViewSeparator.setBackgroundColor(this.mView.getResources().getColor(R.color.award_green_light_color));
                    return;
                default:
                    return;
            }
        }
    }
}
