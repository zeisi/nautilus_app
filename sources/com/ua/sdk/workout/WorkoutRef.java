package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.LinkEntityRef;

public class WorkoutRef extends LinkEntityRef<Workout> {
    public static final Parcelable.Creator<WorkoutRef> CREATOR = new Parcelable.Creator<WorkoutRef>() {
        public WorkoutRef createFromParcel(Parcel in) {
            return new WorkoutRef(in);
        }

        public WorkoutRef[] newArray(int size) {
            return new WorkoutRef[size];
        }
    };

    public WorkoutRef(String href) {
        super(href);
    }

    public WorkoutRef(String id, String href) {
        super(id, href);
    }

    public WorkoutRef(String id, long localId, String href) {
        super(id, localId, href);
    }

    public WorkoutRef(String id, long localId, String href, int options) {
        super(id, localId, href, options);
    }

    private WorkoutRef(FieldBuilder init) {
        super(init.id, init.getHref());
    }

    private WorkoutRef(Builder init) {
        super(init.id, init.getHref());
    }

    public static FieldBuilder getFieldBuilder(WorkoutRef ref) {
        return new FieldBuilder();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class FieldBuilder extends BaseReferenceBuilder {
        /* access modifiers changed from: private */
        public String id;

        private FieldBuilder(WorkoutRef ref) {
            super(ref.getHref());
            this.id = ref.getId();
        }

        public FieldBuilder setTimeSeriesField(boolean fieldSet) {
            if (fieldSet) {
                setParam("field_set", "time_series");
            } else {
                removeParam("field_set");
            }
            return this;
        }

        public WorkoutRef build() {
            return new WorkoutRef(this);
        }
    }

    public static class Builder extends BaseReferenceBuilder {
        protected String id;

        public Builder() {
            super("/v7.0/workout/{id}/");
        }

        public Builder setId(String id2) {
            this.id = id2;
            setParam("id", id2);
            return this;
        }

        public WorkoutRef build() {
            if (this.id != null) {
                return new WorkoutRef(this);
            }
            throw new IllegalArgumentException("Id must be set to build workout reference");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    private WorkoutRef(Parcel in) {
        super(in);
    }
}
