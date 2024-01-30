package com.nautilus.omni.dataaccess;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.OmniData;
import java.sql.SQLException;

public class OmniTrainerDaoHelper {
    public static final String TAG = OmniTrainerDaoHelper.class.getSimpleName();
    private Dao<OmniData, Integer> mOmniTrainerDao;

    public interface OnResponseOmniTrainerDaoHelper {
        void errorCreateOmniData();

        void onCreateSuccess(OmniData omniData);
    }

    public OmniTrainerDaoHelper(Dao<OmniData, Integer> mOmniTrainerDao2) {
        this.mOmniTrainerDao = mOmniTrainerDao2;
    }

    public void saveOmniTrainerInDataBase(OmniData omniData) {
        try {
            if (this.mOmniTrainerDao.countOf() == 0) {
                this.mOmniTrainerDao.create(omniData);
                return;
            }
            omniData.setmId(this.mOmniTrainerDao.queryBuilder().queryForFirst().getmId());
            this.mOmniTrainerDao.update(omniData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OmniData getFirstOmniData() {
        try {
            return this.mOmniTrainerDao.queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "DEBUG --- Error get first omnidata");
            return null;
        }
    }

    public void createOmniData(OmniData omniData, OnResponseOmniTrainerDaoHelper response) {
        try {
            this.mOmniTrainerDao.create(omniData);
            response.onCreateSuccess(omniData);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "DEBUG --- Error creating new omnidata");
            response.errorCreateOmniData();
        }
    }

    public void updateOmniData(OmniData omniData, OnResponseOmniTrainerDaoHelper response) {
        try {
            this.mOmniTrainerDao.update(omniData);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "DEBUG --- Error updating omnidata");
            response.errorCreateOmniData();
        }
    }

    public void deleteOmniData(OnResponseOmniTrainerDaoHelper response) {
        try {
            this.mOmniTrainerDao.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "DEBUG --- Error updating omnidata");
            response.errorCreateOmniData();
        }
    }
}
