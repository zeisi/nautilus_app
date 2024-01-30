package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.BaseReferenceBuilder;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.user.User;

public class ActivityStoryListRef implements EntityListRef<ActivityStory> {
    public static Parcelable.Creator<ActivityStoryListRef> CREATOR = new Parcelable.Creator<ActivityStoryListRef>() {
        public ActivityStoryListRef createFromParcel(Parcel source) {
            return new ActivityStoryListRef(source);
        }

        public ActivityStoryListRef[] newArray(int size) {
            return new ActivityStoryListRef[size];
        }
    };
    public final String mHref;

    private ActivityStoryListRef(Builder builder) {
        Precondition.isNotNull(builder);
        this.mHref = builder.getHref();
    }

    public String getHref() {
        return this.mHref;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getId() {
        return null;
    }

    public static class Builder extends BaseReferenceBuilder {
        private ActivityStoryType feedType;
        private ActivityStoryView feedView;
        private String id;
        private ActivityStoryReplyType replyType;

        private Builder() {
            super("/v7.0/activity_story/");
        }

        public Builder setActivityStoryType(ActivityStoryType type) {
            this.feedType = type;
            return this;
        }

        public Builder setId(String id2) {
            this.id = id2;
            return this;
        }

        public Builder setActivityStoryView(ActivityStoryView view) {
            this.feedView = view;
            return this;
        }

        public Builder setReplyType(ActivityStoryReplyType replyType2) {
            this.feedType = ActivityStoryType.REPLY;
            this.replyType = replyType2;
            return this;
        }

        @Deprecated
        public Builder setParentRef(EntityRef<ActivityStory> parentRef) {
            if (parentRef != null) {
                this.id = parentRef.getId();
                this.feedType = ActivityStoryType.REPLY;
            }
            return this;
        }

        @Deprecated
        public Builder setUser(EntityRef<User> userRef) {
            if (userRef != null) {
                this.id = userRef.getId();
                this.feedType = ActivityStoryType.USER;
            }
            return this;
        }

        @Deprecated
        public Builder setMeFilter(boolean isMeFilterEnabled) {
            if (isMeFilterEnabled) {
                this.feedView = ActivityStoryView.USER_ME;
            }
            return this;
        }

        public ActivityStoryListRef build() {
            Precondition.isNotNull(this.feedType, "ActivityStoryType cannot be null");
            if (this.feedType == ActivityStoryType.WORKOUT) {
                setParam("object_type", this.feedType.toString());
            } else if (this.feedType != ActivityStoryType.REPLY) {
                setParam("feed_type", this.feedType.toString());
            } else {
                Precondition.isNotNull(this.replyType, "ReplyType cannot be null if ActivityStoryType is ReplyType");
                setParam("reply_type", this.replyType.toString());
            }
            if (this.feedType.isIdRequired()) {
                Precondition.isNotNull(this.id, "Story id cannot be null for this ActivityStoryType");
                if (this.feedType == ActivityStoryType.REPLY) {
                    setParam("parent_story_id", this.id);
                } else if (this.feedType == ActivityStoryType.WORKOUT) {
                    setParam("object_id", this.id);
                } else {
                    setParam("feed_id", this.id);
                }
            }
            if (this.feedView != null) {
                if (this.feedView.getActivityStoryType() == this.feedType) {
                    setParam("feed_view", this.feedView.toString());
                } else {
                    throw new IllegalStateException(String.format("Feed view (%S) is not valid for Feed type (%S)", new Object[]{this.feedView, this.feedType}));
                }
            }
            return new ActivityStoryListRef(this);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mHref);
    }

    private ActivityStoryListRef(Parcel in) {
        this.mHref = in.readString();
    }
}
