package com.nautilus.omni.appmodules.journal.presenter.day.workoutlist;

import com.nautilus.omni.appmodules.journal.adapters.JournalDayAdapter;
import com.nautilus.omni.model.dto.Achievement;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class DayListBuilderInteractor {
    private List<Object> mDataList = new ArrayList();
    private DayInteractor mDayInteractor;

    @Inject
    public DayListBuilderInteractor(DayInteractor dayInteractor) {
        this.mDayInteractor = dayInteractor;
    }

    public void setDataList(List<Object> dataList) {
        this.mDataList = new ArrayList(dataList);
    }

    public List<Object> getNewSyncedData(boolean newWorkoutsLoaded, List<Object> newSyncedData) {
        if (newWorkoutsLoaded) {
            if (newSyncedData.size() > 0) {
                List<Object> previousData = new ArrayList<>(this.mDataList);
                this.mDataList = new ArrayList(newSyncedData);
                if (previousData.size() > 0) {
                    mergeOlderDataList(previousData, false);
                }
            } else {
                this.mDayInteractor.getTotalWorkoutsList();
                this.mDataList = new ArrayList(this.mDayInteractor.buildWorkoutDataList());
            }
        }
        return this.mDataList;
    }

    public List<Object> getOlderData(List<Object> olderDataList) {
        if (olderDataList.size() > 0) {
            mergeOlderDataList(olderDataList, true);
        }
        return this.mDataList;
    }

    private void mergeOlderDataList(List<Object> olderDataList, boolean isLoadingOlderData) {
        if (dayAlreadyHasWorkouts((DateTime) olderDataList.get(0))) {
            checkAndRelocateAchievements(olderDataList, isLoadingOlderData);
            mergeNewData(olderDataList);
            return;
        }
        this.mDataList.addAll(olderDataList);
    }

    private boolean dayAlreadyHasWorkouts(DateTime date) {
        for (Object element : this.mDataList) {
            if ((element instanceof DateTime) && ((DateTime) element).dayOfYear().equals(date.dayOfYear()) && ((DateTime) element).year().equals(date.year())) {
                return true;
            }
        }
        return false;
    }

    private void mergeNewData(List<Object> olderDataList) {
        olderDataList.remove(0);
        for (Object currentElement : olderDataList) {
            if (currentElement instanceof DateTime) {
                this.mDataList.addAll(olderDataList.subList(olderDataList.indexOf(currentElement), olderDataList.size()));
                return;
            }
            this.mDataList.add(currentElement);
        }
    }

    private void checkAndRelocateAchievements(List<Object> olderDataList, boolean isLoadingOlderData) {
        if (isLoadingOlderData) {
            removeAchievements(this.mDataList);
            return;
        }
        removeAchievementsAtTheTopOfTheList(olderDataList);
        relocateNewAchievements(olderDataList);
        removeAchievements(this.mDataList);
    }

    private void removeAchievementsAtTheTopOfTheList(List<Object> dataList) {
        int counter = 1;
        while (counter < dataList.size()) {
            int counter2 = executeRemovalAtTheTopOfTheList(dataList, counter);
            if (!(dataList.get(counter2) instanceof DateTime)) {
                counter = counter2 + 1;
            } else {
                return;
            }
        }
    }

    private int executeRemovalAtTheTopOfTheList(List<Object> dataList, int counter) {
        if (!(dataList.get(counter) instanceof Achievement)) {
            return counter;
        }
        dataList.remove(counter);
        if (!isDivider(dataList.get(counter))) {
            return counter;
        }
        dataList.remove(counter);
        return counter - 1;
    }

    private void relocateNewAchievements(List<Object> olderDataList) {
        int counter = 1;
        while (counter < this.mDataList.size() && !(this.mDataList.get(counter) instanceof DateTime)) {
            if (this.mDataList.get(counter) instanceof Achievement) {
                insertAchievement(olderDataList, this.mDataList.get(counter));
            }
            counter++;
        }
    }

    private void insertAchievement(List<Object> dataList, Object achievement) {
        int counter = 1;
        while (counter < dataList.size()) {
            if (dataList.get(counter) instanceof DateTime) {
                insertAchievementInTheMiddleOfTheList(dataList, achievement, counter);
                return;
            } else if (counter + 1 == dataList.size()) {
                dataList.add(achievement);
                dataList.add(JournalDayAdapter.DIVIDER_ID);
                return;
            } else {
                counter++;
            }
        }
    }

    private void insertAchievementInTheMiddleOfTheList(List<Object> dataList, Object achievement, int counter) {
        if (dataList.get(counter - 1) instanceof Achievement) {
            dataList.add(counter, JournalDayAdapter.DIVIDER_ID);
            dataList.add(counter + 1, achievement);
            return;
        }
        dataList.add(counter, achievement);
    }

    private boolean isDivider(Object listObject) {
        return listObject instanceof String;
    }

    private void removeAchievements(List<Object> dataList) {
        int counter = dataList.size() - 1;
        while (counter > 0 && !(dataList.get(counter) instanceof DateTime)) {
            if (dataList.get(counter) instanceof Achievement) {
                dataList.remove(counter);
                dataList.remove(counter);
            }
            counter--;
        }
    }
}
