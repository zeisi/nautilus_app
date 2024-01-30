package com.nautilus.omni.appmodules.settings.mainsection.interactor;

import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.User;

public interface SettingsInteractorContract {
    User getCurrentUser(int i);

    Product getProduct();
}
