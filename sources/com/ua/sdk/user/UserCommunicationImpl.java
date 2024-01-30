package com.ua.sdk.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class UserCommunicationImpl implements UserCommunication, Parcelable {
    public static Parcelable.Creator<UserCommunicationImpl> CREATOR = new Parcelable.Creator<UserCommunicationImpl>() {
        public UserCommunicationImpl createFromParcel(Parcel source) {
            return new UserCommunicationImpl(source);
        }

        public UserCommunicationImpl[] newArray(int size) {
            return new UserCommunicationImpl[size];
        }
    };
    @SerializedName("newsletter")
    Boolean newsletter;
    @SerializedName("promotions")
    Boolean promotions;
    @SerializedName("system_messages")
    Boolean systemMessages;

    public UserCommunicationImpl() {
    }

    public boolean isPromotions() {
        return this.promotions != null && this.promotions.booleanValue();
    }

    public Boolean getPromotions() {
        return this.promotions;
    }

    public void setPromotions(Boolean promotions2) {
        this.promotions = promotions2;
    }

    public boolean isNewsletter() {
        return this.newsletter != null && this.newsletter.booleanValue();
    }

    public Boolean getNewsletter() {
        return this.newsletter;
    }

    public void setNewsletter(Boolean newsletters) {
        this.newsletter = newsletters;
    }

    public boolean isSystemMessages() {
        return this.systemMessages != null && this.systemMessages.booleanValue();
    }

    public Boolean getSystemMessages() {
        return this.systemMessages;
    }

    public void setSystemMessages(Boolean systemMessages2) {
        this.systemMessages = systemMessages2;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Boolean newletters;
        private Boolean promotions;
        private Boolean systemMessages;

        public Builder setPromotions(Boolean promotions2) {
            this.promotions = promotions2;
            return this;
        }

        public Builder setNewletters(Boolean newletters2) {
            this.newletters = newletters2;
            return this;
        }

        public Builder setSystemMessages(Boolean systemMessages2) {
            this.systemMessages = systemMessages2;
            return this;
        }

        public UserCommunicationImpl build() {
            UserCommunicationImpl answer = new UserCommunicationImpl();
            answer.setPromotions(this.promotions);
            answer.setNewsletter(this.newletters);
            answer.setSystemMessages(this.systemMessages);
            return answer;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.promotions);
        dest.writeValue(this.newsletter);
        dest.writeValue(this.systemMessages);
    }

    private UserCommunicationImpl(Parcel in) {
        this.promotions = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.newsletter = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.systemMessages = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }
}
