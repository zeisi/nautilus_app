package com.ua.sdk.sleep;

import com.ua.sdk.internal.LinkEntityRef;

public class SleepRef extends LinkEntityRef<SleepMetric> {
    public SleepRef(String href) {
        super(href);
    }

    public SleepRef(String id, String href) {
        super(id, href);
    }

    public SleepRef(String id, long localId, String href) {
        super(id, localId, href);
    }
}
