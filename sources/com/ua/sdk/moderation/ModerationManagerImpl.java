package com.ua.sdk.moderation;

import com.ua.sdk.CreateCallback;
import com.ua.sdk.EntityRef;
import com.ua.sdk.Request;
import com.ua.sdk.UaException;

public class ModerationManagerImpl implements ModerationManager {
    private final ModerationActionManager actionManager;

    public ModerationManagerImpl(ModerationActionManager actionManager2) {
        this.actionManager = actionManager2;
    }

    public ModerationAction flagEntity(EntityRef ref) throws UaException {
        return this.actionManager.flagEntity(ref);
    }

    public Request flagEntity(EntityRef ref, CreateCallback<ModerationAction> callback) {
        return this.actionManager.flagEntity(ref, callback);
    }

    public ModerationAction unflagEntity(EntityRef ref) throws UaException {
        return this.actionManager.unflagEntity(ref);
    }

    public Request unflagEntity(EntityRef ref, CreateCallback<ModerationAction> callback) {
        return this.actionManager.unflagEntity(ref, callback);
    }
}
