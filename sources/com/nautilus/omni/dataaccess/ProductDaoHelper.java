package com.nautilus.omni.dataaccess;

import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.Product;
import java.sql.SQLException;

public class ProductDaoHelper {
    private Dao<Product, Integer> mProductDao;

    public ProductDaoHelper(Dao<Product, Integer> productDaoHelper) {
        this.mProductDao = productDaoHelper;
    }

    public Product getProduct() {
        try {
            return this.mProductDao.queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getProductModel() {
        try {
            return this.mProductDao.queryBuilder().queryForFirst().getmProductModelName();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
