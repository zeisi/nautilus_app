package com.ua.sdk.activitystory;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.Source;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import java.util.Date;

public class ActivityStoryImpl extends ApiTransferObject implements ActivityStory {
    public static Parcelable.Creator<ActivityStoryImpl> CREATOR = new Parcelable.Creator<ActivityStoryImpl>() {
        public ActivityStoryImpl createFromParcel(Parcel source) {
            return new ActivityStoryImpl(source);
        }

        public ActivityStoryImpl[] newArray(int size) {
            return new ActivityStoryImpl[size];
        }
    };
    @SerializedName("actor")
    ActivityStoryActor mActor;
    @SerializedName("attachments")
    Attachments mAttachments;
    @SerializedName("comments")
    ActivityStoryReplySummaryImpl mCommentSummary;
    @SerializedName("id")
    String mId;
    @SerializedName("likes")
    ActivityStoryReplySummaryImpl mLikeSummary;
    @SerializedName("object")
    ActivityStoryObject mObject;
    @SerializedName("published")
    Date mPublishedTime;
    @SerializedName("reposts")
    ActivityStoryRepostSummaryImpl mRepostSummary;
    @SerializedName("sharing")
    SocialSettings mSharingSettngs;
    @SerializedName("target")
    ActivityStoryTarget mTarget;
    @SerializedName("template")
    ActivityStoryTemplateImpl mTemplate;
    @SerializedName("verb")
    ActivityStoryVerb mVerb;
    @SerializedName("source")
    Source source;

    public String getId() {
        return this.mId;
    }

    public ActivityStoryActor getActor() {
        return this.mActor;
    }

    public ActivityStoryObject getObject() {
        return this.mObject;
    }

    public ActivityStoryVerb getVerb() {
        return this.mVerb;
    }

    public ActivityStoryTemplate getTemplate() {
        return this.mTemplate;
    }

    public Date getPublished() {
        return this.mPublishedTime;
    }

    public EntityRef<ActivityStory> getTargetRef() {
        if (this.mTarget != null) {
            return new LinkEntityRef(this.mTarget.getId(), "");
        }
        return null;
    }

    public ActivityStoryReplySummary getCommentsSummary() {
        return this.mCommentSummary;
    }

    public ActivityStoryReplySummary getLikesSummary() {
        return this.mLikeSummary;
    }

    public ActivityStoryRepostSummary getRepostSummary() {
        return this.mRepostSummary;
    }

    public ActivityStoryListRef getCommmentsRef() {
        return ActivityStoryListRef.getBuilder().setId(getRef().getId()).setReplyType(ActivityStoryReplyType.COMMENTS).build();
    }

    public ActivityStoryListRef getLikesRef() {
        return ActivityStoryListRef.getBuilder().setId(getRef().getId()).setReplyType(ActivityStoryReplyType.LIKES).build();
    }

    public ActivityStoryListRef getRepostsRef() {
        return ActivityStoryListRef.getBuilder().setId(getRef().getId()).setReplyType(ActivityStoryReplyType.REPOSTS).build();
    }

    public boolean isLikedByCurrentUser() {
        if (this.mLikeSummary != null) {
            return this.mLikeSummary.isReplied();
        }
        return false;
    }

    public boolean isCommentedByCurrentUser() {
        if (this.mCommentSummary != null) {
            return this.mCommentSummary.isReplied();
        }
        return false;
    }

    public boolean isRepostedByCurrentUser() {
        if (this.mRepostSummary != null) {
            return this.mRepostSummary.isReposted();
        }
        return false;
    }

    public int getLikeCount() {
        if (this.mLikeSummary == null) {
            return 0;
        }
        return this.mLikeSummary.getTotalCount();
    }

    public int getCommentCount() {
        if (this.mCommentSummary == null) {
            return 0;
        }
        return this.mCommentSummary.getTotalCount();
    }

    public int getRepostCount() {
        if (this.mRepostSummary == null) {
            return 0;
        }
        return this.mRepostSummary.getTotalCount();
    }

    public int getAttachmentCount() {
        if (this.mAttachments == null) {
            return 0;
        }
        return this.mAttachments.getCount();
    }

    public Attachment getAttachment(int index) throws IndexOutOfBoundsException {
        if (this.mAttachments != null) {
            return this.mAttachments.getAttachment(index);
        }
        throw new IndexOutOfBoundsException("Activity Story does not have any attachments.");
    }

    public SocialSettings getSocialSettings() {
        return this.mSharingSettngs;
    }

    public Source getSource() {
        return this.source;
    }

    public EntityRef<ActivityStory> getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new LinkEntityRef(self.getId(), self.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mId);
        dest.writeParcelable(this.mActor, 0);
        dest.writeInt(this.mVerb == null ? -1 : this.mVerb.ordinal());
        dest.writeParcelable(this.mObject, 0);
        dest.writeLong(this.mPublishedTime != null ? this.mPublishedTime.getTime() : -1);
        dest.writeParcelable(this.mTemplate, flags);
        dest.writeParcelable(this.mTarget, flags);
        dest.writeParcelable(this.mCommentSummary, flags);
        dest.writeParcelable(this.mLikeSummary, flags);
        dest.writeParcelable(this.mRepostSummary, flags);
        dest.writeParcelable(this.mAttachments, flags);
        dest.writeParcelable(this.mSharingSettngs, flags);
        dest.writeParcelable(this.source, flags);
    }

    public ActivityStoryImpl() {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private ActivityStoryImpl(Parcel in) {
        super(in);
        Date date = null;
        this.mId = in.readString();
        this.mActor = (ActivityStoryActor) in.readParcelable(ActivityStoryActor.class.getClassLoader());
        int tmpMVerb = in.readInt();
        this.mVerb = tmpMVerb == -1 ? null : ActivityStoryVerb.values()[tmpMVerb];
        this.mObject = (ActivityStoryObject) in.readParcelable(ActivityStoryObject.class.getClassLoader());
        long tmpMPublishedTime = in.readLong();
        this.mPublishedTime = tmpMPublishedTime != -1 ? new Date(tmpMPublishedTime) : date;
        this.mTemplate = (ActivityStoryTemplateImpl) in.readParcelable(ActivityStoryTemplate.class.getClassLoader());
        this.mTarget = (ActivityStoryTarget) in.readParcelable(ActivityStoryTarget.class.getClassLoader());
        this.mCommentSummary = (ActivityStoryReplySummaryImpl) in.readParcelable(ActivityStoryReplySummaryImpl.class.getClassLoader());
        this.mLikeSummary = (ActivityStoryReplySummaryImpl) in.readParcelable(ActivityStoryReplySummaryImpl.class.getClassLoader());
        this.mRepostSummary = (ActivityStoryRepostSummaryImpl) in.readParcelable(ActivityStoryReplySummaryImpl.class.getClassLoader());
        this.mAttachments = (Attachments) in.readParcelable(Attachments.class.getClassLoader());
        this.mSharingSettngs = (SocialSettings) in.readParcelable(SocialSettings.class.getClassLoader());
        this.source = (Source) in.readParcelable(Source.class.getClassLoader());
    }
}
