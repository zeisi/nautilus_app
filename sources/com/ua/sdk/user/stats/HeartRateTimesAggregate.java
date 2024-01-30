package com.ua.sdk.user.stats;

import android.os.Parcelable;

public interface HeartRateTimesAggregate extends Parcelable {
    Double getTimeInZoneFive();

    Double getTimeInZoneFour();

    Double getTimeInZoneOne();

    Double getTimeInZoneThree();

    Double getTimeInZoneTwo();
}
