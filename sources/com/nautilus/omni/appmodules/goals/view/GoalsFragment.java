package com.nautilus.omni.appmodules.goals.view;

import android.animation.LayoutTransition;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseFragment;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.goals.presenter.GoalsPresenter;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import com.nautilus.omni.model.dto.TrainingGoal;
import javax.inject.Inject;

public class GoalsFragment extends BaseFragment implements GoalsViewContract {
    private static final float ALPHA = 0.3f;
    private static final int BUTTONS_ANIMATION_DURATION = 1000;
    public static final String TAG = GoalsFragment.class.getSimpleName();
    @BindView(2131755283)
    View goalsTitleGone;
    /* access modifiers changed from: private */
    public boolean hideButtonsEnable;
    @BindView(2131755292)
    RelativeLayout mButtonsContainer;
    private String mCaloriesDefaultValue;
    @BindView(2131755293)
    Button mCancelButton;
    private TrainingGoal mCurrentCaloriePerWorkoutGoal;
    private TrainingGoal mCurrentTimePerWorkoutGoal;
    private TrainingGoal mCurrentWorkoutsPerWeekGoal;
    private boolean mGoalsEnabled;
    @Inject
    GoalsPresenter mGoalsPresenter;
    private Toast mGoalsToast;
    private Animation mInButtonsAnimation;
    private Animation mOutButtonsAnimation;
    @BindView(2131755294)
    Button mSaveButton;
    private boolean mShowGoalsSavedToast;
    @BindView(2131755287)
    Spinner mSpinnerCaloriesPerWorkout;
    private ArrayAdapter<String> mSpinnerCaloriesPerWorkoutAdapter;
    private String[] mSpinnerCaloriesPerWorkoutData;
    @BindView(2131755289)
    Spinner mSpinnerTimePerWorkout;
    private ArrayAdapter<String> mSpinnerTimePerWorkoutAdapter;
    private String[] mSpinnerTimePerWorkoutData;
    @BindView(2131755291)
    Spinner mSpinnerWorkoutsPerWeek;
    private ArrayAdapter<String> mSpinnerWorkoutsPerWeekAdapter;
    private String[] mSpinnerWorkoutsPerWeekData;
    @BindView(2131755285)
    SwitchCompat mSwitchGoals;
    @BindView(2131755286)
    TextView mTxtViewCaloriesPerWorkout;
    @BindView(2131755288)
    TextView mTxtViewTimePerWorkout;
    @BindView(2131755290)
    TextView mTxtViewWorkoutsPerWeek;
    private String mWorkoutNumberDefaultValue;
    private String mWorkoutTimeDefaultValue;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSharedPreferences();
    }

    private void getSharedPreferences() {
        this.mPreferences = ((OmniApplication) getActivity().getApplication()).getAppComponent().getSharedPreferences();
        this.mGoalsEnabled = this.mPreferences.getBoolean(Preferences.GOALS_ENABLED, false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goals, container, false);
        ButterKnife.bind((Object) this, rootView);
        setTransition((LinearLayout) rootView);
        initCustomToast();
        initAnimations();
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainOmniComponent) getComponent(MainOmniComponent.class)).inject(this);
        this.mGoalsPresenter.setIGoalsView(this);
        setHasOptionsMenu(true);
        loadGoalsPickers();
        loadCurrentGoals();
        initGoalsSwitch();
    }

    private void setTransition(LinearLayout rootView) {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.disableTransitionType(3);
        rootView.setLayoutTransition(layoutTransition);
    }

    private void loadGoalsPickers() {
        this.mGoalsPresenter.loadGoalPickerValues();
    }

    public void loadCaloriePicker(String[] arrayCalorieValues, String calorieDefaultValue) {
        this.mSpinnerCaloriesPerWorkoutData = arrayCalorieValues;
        this.mCaloriesDefaultValue = calorieDefaultValue;
        initCaloriesPerWeekSpinnerData();
    }

    public void loadTimeGoalPicker(String[] arrayTimeGoalValues, String timeDefaultValue) {
        this.mSpinnerTimePerWorkoutData = arrayTimeGoalValues;
        this.mWorkoutTimeDefaultValue = timeDefaultValue;
        initTimePerWorkoutSpinnerData();
    }

    public void loadNumberWorkoutsPicker(String[] arrayNumberOfWorkoutsValues, String workoutNumberDefaultValue) {
        this.mSpinnerWorkoutsPerWeekData = arrayNumberOfWorkoutsValues;
        this.mWorkoutNumberDefaultValue = workoutNumberDefaultValue;
        initWorkoutsPerWeekSpinnerData();
    }

    private void loadCurrentGoals() {
        if (this.mGoalsPresenter.getCurrentUser() != null) {
            this.mGoalsPresenter.loadCurrentGoals();
        }
    }

    public void loadCurrentCalorieGoal(TrainingGoal currentCaloriePerWorkoutGoal) {
        this.mCurrentCaloriePerWorkoutGoal = currentCaloriePerWorkoutGoal;
        this.mSpinnerCaloriesPerWorkout.setSelection(this.mSpinnerCaloriesPerWorkoutAdapter.getPosition(this.mCurrentCaloriePerWorkoutGoal.getmGoalValue()));
    }

    public void loadCurrentTimeGoal(TrainingGoal currentTimePerWorkoutGoal) {
        this.mCurrentTimePerWorkoutGoal = currentTimePerWorkoutGoal;
        this.mSpinnerTimePerWorkout.setSelection(this.mSpinnerTimePerWorkoutAdapter.getPosition(this.mCurrentTimePerWorkoutGoal.getmGoalValue()));
    }

    public void loadCurrentNumberWorkoutsGoal(TrainingGoal currentWorkoutsPerWeekGoal) {
        this.mCurrentWorkoutsPerWeekGoal = currentWorkoutsPerWeekGoal;
        this.mSpinnerWorkoutsPerWeek.setSelection(this.mSpinnerWorkoutsPerWeekAdapter.getPosition(this.mCurrentWorkoutsPerWeekGoal.getmGoalValue()));
    }

    private void initCaloriesPerWeekSpinnerData() {
        this.mSpinnerCaloriesPerWorkoutAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_goals_spinner_item, this.mSpinnerCaloriesPerWorkoutData);
        this.mSpinnerCaloriesPerWorkoutAdapter.setDropDownViewResource(R.layout.custom_goals_spinner_dropdown_item);
        this.mSpinnerCaloriesPerWorkout.setAdapter(this.mSpinnerCaloriesPerWorkoutAdapter);
        this.mSpinnerCaloriesPerWorkout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (GoalsFragment.this.isNewCalorieGoal(GoalsFragment.this.mSpinnerCaloriesPerWorkout.getSelectedItem().toString()) && GoalsFragment.this.mButtonsContainer.getVisibility() == 4) {
                    GoalsFragment.this.showButtons();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpinnerCaloriesPerWorkout.setSelection(this.mSpinnerCaloriesPerWorkoutAdapter.getPosition(this.mCaloriesDefaultValue));
    }

    private void initWorkoutsPerWeekSpinnerData() {
        this.mSpinnerWorkoutsPerWeekAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_goals_spinner_item, this.mSpinnerWorkoutsPerWeekData);
        this.mSpinnerWorkoutsPerWeekAdapter.setDropDownViewResource(R.layout.custom_goals_spinner_dropdown_item);
        this.mSpinnerWorkoutsPerWeek.setAdapter(this.mSpinnerWorkoutsPerWeekAdapter);
        this.mSpinnerWorkoutsPerWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (GoalsFragment.this.isNewWorkoutNumberGoal(GoalsFragment.this.mSpinnerWorkoutsPerWeek.getSelectedItem().toString()) && GoalsFragment.this.mButtonsContainer.getVisibility() == 4) {
                    GoalsFragment.this.showButtons();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpinnerWorkoutsPerWeek.setSelection(this.mSpinnerWorkoutsPerWeekAdapter.getPosition(this.mWorkoutNumberDefaultValue));
    }

    private void initTimePerWorkoutSpinnerData() {
        this.mSpinnerTimePerWorkoutAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_goals_spinner_item, this.mSpinnerTimePerWorkoutData);
        this.mSpinnerTimePerWorkoutAdapter.setDropDownViewResource(R.layout.custom_goals_spinner_dropdown_item);
        this.mSpinnerTimePerWorkout.setAdapter(this.mSpinnerTimePerWorkoutAdapter);
        this.mSpinnerTimePerWorkout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (GoalsFragment.this.isNewWorkoutTimeGoal(GoalsFragment.this.mSpinnerTimePerWorkout.getSelectedItem().toString()) && GoalsFragment.this.mButtonsContainer.getVisibility() == 4) {
                    GoalsFragment.this.showButtons();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.mSpinnerTimePerWorkout.setSelection(this.mSpinnerTimePerWorkoutAdapter.getPosition(this.mWorkoutTimeDefaultValue));
    }

    /* access modifiers changed from: private */
    public boolean isNewCalorieGoal(String mSelectedElement) {
        if (this.mCurrentCaloriePerWorkoutGoal != null && !mSelectedElement.equals(this.mCurrentCaloriePerWorkoutGoal.getmGoalValue())) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean isNewWorkoutTimeGoal(String mSelectedElement) {
        if (this.mCurrentTimePerWorkoutGoal != null && !mSelectedElement.equals(this.mCurrentTimePerWorkoutGoal.getmGoalValue())) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean isNewWorkoutNumberGoal(String mSelectedElement) {
        if (this.mCurrentWorkoutsPerWeekGoal != null && !mSelectedElement.equals(this.mCurrentWorkoutsPerWeekGoal.getmGoalValue())) {
            return true;
        }
        return false;
    }

    private void initGoalsSwitch() {
        this.mSwitchGoals.setChecked(this.mGoalsEnabled);
        changeGoalsState(true);
        this.mSwitchGoals.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GoalsFragment.this.changeGoalsState(false);
            }
        });
    }

    /* access modifiers changed from: private */
    public void changeGoalsState(boolean isInitialSetup) {
        boolean isTreadClimberPaired;
        if (this.mSwitchGoals.isChecked()) {
            enableGoals(isInitialSetup);
            this.goalsTitleGone.setVisibility(8);
            return;
        }
        if (this.mGoalsPresenter.getCurrentUser() != null) {
            isTreadClimberPaired = true;
        } else {
            isTreadClimberPaired = false;
        }
        disableGoals(isInitialSetup, isTreadClimberPaired);
        this.goalsTitleGone.setVisibility(0);
    }

    private void enableGoals(boolean isInitialSetup) {
        this.mGoalsEnabled = true;
        changeGoalsLabelsColorToEnabled();
        enableGoalsSpinners();
        if (!isInitialSetup) {
            this.mShowGoalsSavedToast = false;
            saveGoalsPreference();
            executeSaveGoalsOperation();
        }
    }

    private void changeGoalsLabelsColorToEnabled() {
        this.mTxtViewCaloriesPerWorkout.setAlpha(1.0f);
        this.mTxtViewTimePerWorkout.setAlpha(1.0f);
        this.mTxtViewWorkoutsPerWeek.setAlpha(1.0f);
    }

    private void enableGoalsSpinners() {
        this.mSpinnerCaloriesPerWorkout.setEnabled(true);
        this.mSpinnerWorkoutsPerWeek.setEnabled(true);
        this.mSpinnerTimePerWorkout.setEnabled(true);
        changeSelectedItemColor(this.mSpinnerCaloriesPerWorkout, true);
        changeSelectedItemColor(this.mSpinnerWorkoutsPerWeek, true);
        changeSelectedItemColor(this.mSpinnerTimePerWorkout, true);
    }

    private void disableGoals(boolean isInitialSetup, boolean isTreadClimberPaired) {
        this.mGoalsEnabled = false;
        changeGoalsLabelsColorToDisabled();
        disableGoalsSpinners();
        changeGoalsSwitchState(isInitialSetup, isTreadClimberPaired);
        hideButtonsIfVisible();
        executeGoalsDisabling(isInitialSetup);
    }

    private void changeGoalsLabelsColorToDisabled() {
        this.mTxtViewCaloriesPerWorkout.setAlpha(ALPHA);
        this.mTxtViewTimePerWorkout.setAlpha(ALPHA);
        this.mTxtViewWorkoutsPerWeek.setAlpha(ALPHA);
    }

    private void disableGoalsSpinners() {
        this.mSpinnerCaloriesPerWorkout.setEnabled(false);
        this.mSpinnerWorkoutsPerWeek.setEnabled(false);
        this.mSpinnerTimePerWorkout.setEnabled(false);
        changeSelectedItemColor(this.mSpinnerCaloriesPerWorkout, false);
        changeSelectedItemColor(this.mSpinnerWorkoutsPerWeek, false);
        changeSelectedItemColor(this.mSpinnerTimePerWorkout, false);
    }

    public void changeSelectedItemColor(Spinner spinner, boolean active) {
        spinner.setAlpha(active ? 1.0f : ALPHA);
    }

    private void changeGoalsSwitchState(boolean isInitialSetup, boolean isTreadClimberPaired) {
        if (isTreadClimberPaired) {
            if (!isInitialSetup) {
                resetSpinnerValues();
            }
            this.mSwitchGoals.setEnabled(true);
            return;
        }
        this.mSwitchGoals.setEnabled(false);
    }

    private void hideButtonsIfVisible() {
        if (this.mButtonsContainer.isShown()) {
            hideButtons();
        }
    }

    private void executeGoalsDisabling(boolean isInitialSetup) {
        if (!isInitialSetup) {
            saveGoalsPreference();
            this.mGoalsPresenter.disableGoals();
        }
    }

    private void saveGoalsPreference() {
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putBoolean(Preferences.GOALS_ENABLED, this.mGoalsEnabled);
        editor.commit();
    }

    private void initCustomToast() {
        View toastLayout = getActivity().getLayoutInflater().inflate(R.layout.custom_regular_toast, (ViewGroup) getActivity().findViewById(R.id.custom_toast_layout_root));
        this.mGoalsToast = new Toast(getActivity().getApplicationContext());
        this.mGoalsToast.setGravity(55, 0, 0);
        this.mGoalsToast.setDuration(0);
        this.mGoalsToast.setView(toastLayout);
    }

    private void initAnimations() {
        this.mInButtonsAnimation = AnimationUtils.makeInChildBottomAnimation(getActivity());
        this.mInButtonsAnimation.setDuration(1000);
        this.mOutButtonsAnimation = AnimationUtils.makeOutAnimation(getActivity(), true);
        this.mOutButtonsAnimation.setDuration(1000);
        this.mOutButtonsAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                boolean unused = GoalsFragment.this.hideButtonsEnable = true;
            }

            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        boolean unused = GoalsFragment.this.hideButtonsEnable = false;
                    }
                }, 1000);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @OnClick({2131755293})
    public void cancelGoals() {
        if (!this.hideButtonsEnable) {
            resetSpinnerValues();
            hideButtons();
        }
    }

    private void resetSpinnerValues() {
        if (this.mCurrentCaloriePerWorkoutGoal != null && this.mCurrentWorkoutsPerWeekGoal != null && this.mCurrentTimePerWorkoutGoal != null) {
            int calorieSpinnerPosition = this.mSpinnerCaloriesPerWorkoutAdapter.getPosition(this.mCurrentCaloriePerWorkoutGoal.getmGoalValue());
            int workoutNumberSpinnerPosition = this.mSpinnerWorkoutsPerWeekAdapter.getPosition(this.mCurrentWorkoutsPerWeekGoal.getmGoalValue());
            int workoutTimeSpinnerPosition = this.mSpinnerTimePerWorkoutAdapter.getPosition(this.mCurrentTimePerWorkoutGoal.getmGoalValue());
            this.mSpinnerCaloriesPerWorkout.setSelection(calorieSpinnerPosition);
            this.mSpinnerWorkoutsPerWeek.setSelection(workoutNumberSpinnerPosition);
            this.mSpinnerTimePerWorkout.setSelection(workoutTimeSpinnerPosition);
        }
    }

    @OnClick({2131755294})
    public void saveGoals() {
        if (!this.hideButtonsEnable) {
            hideButtons();
            this.mShowGoalsSavedToast = true;
            executeSaveGoalsOperation();
        }
    }

    private void executeSaveGoalsOperation() {
        this.mGoalsPresenter.saveGoals(this.mSpinnerCaloriesPerWorkout.getSelectedItem().toString(), this.mSpinnerWorkoutsPerWeek.getSelectedItem().toString(), this.mSpinnerTimePerWorkout.getSelectedItem().toString());
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void hideButtons() {
        this.mButtonsContainer.setVisibility(4);
        this.mButtonsContainer.startAnimation(this.mOutButtonsAnimation);
    }

    /* access modifiers changed from: private */
    public void showButtons() {
        this.mButtonsContainer.setVisibility(0);
        this.mButtonsContainer.startAnimation(this.mInButtonsAnimation);
    }

    public void showGoalsSavedToast(TrainingGoal calorieGoal, TrainingGoal numberWorkoutsGoal, TrainingGoal timeGoal) {
        if (this.mShowGoalsSavedToast) {
            this.mGoalsToast.show();
        }
        updateCurrentGoals(calorieGoal, numberWorkoutsGoal, timeGoal);
    }

    private void updateCurrentGoals(TrainingGoal calorieGoal, TrainingGoal numberWorkoutsGoal, TrainingGoal timeGoal) {
        this.mCurrentCaloriePerWorkoutGoal = calorieGoal;
        this.mCurrentWorkoutsPerWeekGoal = numberWorkoutsGoal;
        this.mCurrentTimePerWorkoutGoal = timeGoal;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
