package com.nautilus.omni.appmodules.connectionwizard.presenter;

import com.nautilus.omni.appmodules.connectionwizard.adapters.OmniTrainerListAdapter;
import com.nautilus.omni.model.dto.OmniData;
import java.util.ArrayList;

public interface DevicesListPresenterContract {
    void listEmpty();

    void pause();

    void resetCurrentUserPreference();

    void resume();

    void setAdapterView(OmniTrainerListAdapter.AdapterView adapterView);

    void setmOmniDataList(ArrayList<OmniData> arrayList);

    void updateDefaultMaxTrainer(OmniData omniData);

    void updateSyncInProgressPreference(boolean z);
}
