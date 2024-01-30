package com.ua.sdk.bodymass;

import com.ua.sdk.internal.Precondition;
import java.util.Date;

public class BodyMassBuilder {
    private static final String RECORDER_DEFAULT = "client_manual_creation";
    String bmi;
    String dateTimeTimezone;
    Date dateTimeUtc;
    String fatMass;
    String fatPercent;
    String leanMass;
    String mass;
    String recorderType;
    String referenceKey;

    public BodyMassBuilder setDateTimeUtc(Date dateTimeUtc2) {
        this.dateTimeUtc = dateTimeUtc2;
        return this;
    }

    public BodyMassBuilder setDateTimeTimezone(String dateTimeTimezone2) {
        this.dateTimeTimezone = dateTimeTimezone2;
        return this;
    }

    public BodyMassBuilder setRecorderType(String recorderType2) {
        this.recorderType = recorderType2;
        return this;
    }

    public BodyMassBuilder setReferenceKey(String referenceKey2) {
        this.referenceKey = referenceKey2;
        return this;
    }

    public BodyMassBuilder setMass(String mass2) {
        this.mass = mass2;
        return this;
    }

    public BodyMassBuilder setBmi(String bmi2) {
        this.bmi = bmi2;
        return this;
    }

    public BodyMassBuilder setFatPercent(String fatPercent2) {
        this.fatPercent = fatPercent2;
        return this;
    }

    public BodyMassBuilder setLeanMass(String leanMass2) {
        this.leanMass = leanMass2;
        return this;
    }

    public BodyMassBuilder setFatMass(String fatMass2) {
        this.fatMass = fatMass2;
        return this;
    }

    public BodyMass build() {
        Precondition.isNotNull(this.dateTimeUtc, "Measurement DateTime");
        Precondition.isNotNull(this.dateTimeTimezone, "Measurement DateTime Timezone");
        Precondition.isNotNull(this.referenceKey, "Measurement Device's Reference Key");
        if (this.recorderType == null) {
            this.recorderType = RECORDER_DEFAULT;
        }
        BodyMass impl = new BodyMassImpl();
        impl.setDateTimeUtc(this.dateTimeUtc);
        impl.setDateTimeTimezone(this.dateTimeTimezone);
        impl.setRecorderType(this.recorderType);
        impl.setReferenceKey(this.referenceKey);
        impl.setMass(this.mass);
        impl.setBmi(this.bmi);
        impl.setFatPercent(this.fatPercent);
        impl.setLeanMass(this.leanMass);
        impl.setFatMass(this.fatMass);
        return impl;
    }
}
