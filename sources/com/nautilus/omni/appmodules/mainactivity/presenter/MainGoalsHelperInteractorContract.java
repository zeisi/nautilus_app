package com.nautilus.omni.appmodules.mainactivity.presenter;

public interface MainGoalsHelperInteractorContract {

    public interface CurrentGoalsCheckListener {
        void onDefaultGoalsSaved();

        void onTimeGoalsUpdated();
    }

    void checkCurrentGoals(CurrentGoalsCheckListener currentGoalsCheckListener);
}
