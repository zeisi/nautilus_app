package com.nautilus.omni.dataaccess.awards;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.AwardType;

public class AwardsUpdater {
    private Dao<Award, Integer> mAwardDao;
    private Dao<AwardType, Integer> mAwardTypeDao;
    private Context mContext;

    public AwardsUpdater(Context context, Dao<AwardType, Integer> awardTypesDao, Dao<Award, Integer> awardDao) {
        this.mAwardTypeDao = awardTypesDao;
        this.mAwardDao = awardDao;
        this.mContext = context;
    }

    public void executeAwardUpdates() {
    }
}
