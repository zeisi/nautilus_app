package com.ua.sdk;

import com.ua.sdk.Resource;
import java.util.List;

public interface MultipleCreateCallback<T extends Resource> {
    void onSynced(List<T> list, UaException uaException);
}
