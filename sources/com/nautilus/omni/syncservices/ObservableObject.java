package com.nautilus.omni.syncservices;

import java.util.Observable;

public class ObservableObject extends Observable {
    public void updateValue(ObjectParse objectParse) {
        synchronized (this) {
            setChanged();
            notifyObservers(objectParse);
        }
    }
}
