package com.ua.sdk.user.stats;

import android.os.Parcelable;
import com.ua.sdk.LocalDate;

public interface AggregatePeriod extends Parcelable {
    LocalDate getEndDate();

    LocalDate getStartDate();
}
