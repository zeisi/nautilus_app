package com.nautilus.omni.databasemanager;

import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.Week;
import java.sql.SQLException;
import java.util.List;

public class DataBaseMigrationVersion2 {
    private Dao<Week, Integer> mWeekDao;
    private List<Week> mWeekList;

    public DataBaseMigrationVersion2(Dao<Week, Integer> weekDao) {
        this.mWeekDao = weekDao;
    }

    public void migrateDataToDatabaseVersion2() {
        try {
            this.mWeekDao.executeRaw("ALTER TABLE 'week' ADD COLUMN total_workouts_power INTEGER", new String[0]);
            this.mWeekList = this.mWeekDao.queryForAll();
            for (Week week : this.mWeekList) {
                week.setmTotalWorkoutsPower(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
