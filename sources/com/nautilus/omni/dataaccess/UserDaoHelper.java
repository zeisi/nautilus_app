package com.nautilus.omni.dataaccess;

import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.User;
import java.sql.SQLException;

public class UserDaoHelper {
    private Dao<User, Integer> mUserDao;

    public UserDaoHelper(Dao<User, Integer> userDao) {
        this.mUserDao = userDao;
    }

    public User getCurrentUser(int userIndex) {
        try {
            return this.mUserDao.queryBuilder().where().eq(User.USER_INDEX, Integer.valueOf(userIndex)).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
