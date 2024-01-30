package com.nautilus.omni.appmodules.awards.view;

import com.nautilus.omni.model.dto.Award;
import java.util.List;

public interface AwardsFragmentContract {
    void loadAwardDetailInfo(List<Award> list, int i);

    void setAwards(List<Award> list, int i);
}
