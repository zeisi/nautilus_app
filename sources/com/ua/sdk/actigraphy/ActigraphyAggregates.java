package com.ua.sdk.actigraphy;

public interface ActigraphyAggregates {
    AggregateValueImpl getActiveTime();

    AggregateValueImpl getBodyMass();

    AggregateValueImpl getDistance();

    AggregateValueImpl getEnergyBurned();

    AggregateValueImpl getSleep();

    AggregateValueImpl getSteps();
}
