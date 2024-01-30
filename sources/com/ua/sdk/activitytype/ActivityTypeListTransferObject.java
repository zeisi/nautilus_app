package com.ua.sdk.activitytype;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ActivityTypeListTransferObject extends ApiTransferObject {
    private static final String EMBEDDED_ACTIVITY_TYPES = "activity_types";
    @SerializedName("_embedded")
    Map<String, List<ActivityTypeTransferObject>> embedded;
    @SerializedName("total_count")
    Integer totalCount;

    public List<ActivityTypeTransferObject> getActivityTypes() {
        return this.embedded.get(EMBEDDED_ACTIVITY_TYPES);
    }

    public static List<ActivityTypeImpl> toImplList(ActivityTypeListTransferObject to) {
        List<ActivityTypeImpl> list = null;
        List<ActivityTypeTransferObject> toList = to.getActivityTypes();
        if (toList != null) {
            list = new ArrayList<>(toList.size());
            for (ActivityTypeTransferObject obj : toList) {
                list.add(ActivityTypeTransferObject.toImpl(obj));
            }
        }
        return list;
    }
}
