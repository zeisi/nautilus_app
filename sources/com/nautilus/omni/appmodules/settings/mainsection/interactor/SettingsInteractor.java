package com.nautilus.omni.appmodules.settings.mainsection.interactor;

import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.User;

public class SettingsInteractor implements SettingsInteractorContract {
    private final ProductDaoHelper productDaoHelper;
    private final UserDaoHelper userDaoHelper;

    public SettingsInteractor(UserDaoHelper userDaoHelper2, ProductDaoHelper productDaoHelper2) {
        this.userDaoHelper = userDaoHelper2;
        this.productDaoHelper = productDaoHelper2;
    }

    public User getCurrentUser(int userIndex) {
        return this.userDaoHelper.getCurrentUser(userIndex);
    }

    public Product getProduct() {
        return this.productDaoHelper.getProduct();
    }
}
