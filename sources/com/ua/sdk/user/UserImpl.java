package com.ua.sdk.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.ImageUrl;
import com.ua.sdk.LocalDate;
import com.ua.sdk.MeasurementSystem;
import com.ua.sdk.UaLog;
import com.ua.sdk.authentication.OAuth2Credentials;
import com.ua.sdk.authentication.OAuth2CredentialsImpl;
import com.ua.sdk.friendship.FriendshipListRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.ImageUrlImpl;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.location.Location;
import com.ua.sdk.location.LocationImpl;
import com.ua.sdk.page.follow.PageFollow;
import com.ua.sdk.page.follow.PageFollowListRef;
import com.ua.sdk.privacy.Privacy;
import com.ua.sdk.privacy.PrivacyHelper;
import com.ua.sdk.user.stats.UserStatsRef;
import java.util.Date;
import java.util.List;

public class UserImpl extends ApiTransferObject implements User, Parcelable {
    public static Parcelable.Creator<UserImpl> CREATOR = new Parcelable.Creator<UserImpl>() {
        public UserImpl createFromParcel(Parcel source) {
            return new UserImpl(source);
        }

        public UserImpl[] newArray(int size) {
            return new UserImpl[size];
        }
    };
    protected static final String NAME_ACTIVITY_FEED = "activity_feed";
    protected static final String NAME_BODY_MASS = "bodymass";
    protected static final String NAME_EMAIL_SEARCH = "email_search";
    protected static final String NAME_FOOD_LOG = "food_log";
    protected static final String NAME_PROFILE = "profile";
    protected static final String NAME_ROUTE = "route";
    protected static final String NAME_SLEEP = "sleep";
    protected static final String NAME_WORKOUT = "workout";
    protected static final String REF_DEACTIVATION = "deactivation";
    protected static final String REF_DOCUMENTATION = "documentation";
    protected static final String REF_FRIENDSHIPS = "friendships";
    protected static final String REF_IMAGE = "image";
    protected static final String REF_PRIVACY = "privacy";
    protected static final String REF_STATS = "stats";
    protected static final String REF_WORKOUTS = "workouts";
    private transient Privacy activityFeedPrivacy;
    private LocalDate birthdate;
    private transient Privacy bodyMassPrivacy;
    private UserCommunication communication;
    private Date dateJoined;
    private MeasurementSystem displayMeasurementSystem;
    private String displayName;
    private String email;
    private transient Privacy emailSearchPrivacy;
    private String firstName;
    private transient EntityListRef<PageFollow> followingRef;
    private transient Privacy foodLogPrivacy;
    private transient FriendshipListRef friendships;
    private Gender gender;
    private String goalStatement;
    private Double height;
    private String hobbies;
    private String id;
    private String introduction;
    private String lastInitial;
    private Date lastLogin;
    private String lastName;
    private Location location;
    private UserObjectState myState;
    private OAuth2Credentials oAuth2Credentials;
    private transient String password;
    private transient Privacy profilePrivacy;
    private String profileStatement;
    private transient Privacy routePrivacy;
    private UserSharing sharing;
    private transient Privacy sleepPrivacy;
    private String timeZone;
    private ImageUrlImpl userProfilePhoto;
    private String username;
    private Double weight;
    private transient Privacy workoutPrivacy;

    public UserImpl() {
        this.oAuth2Credentials = new OAuth2CredentialsImpl();
        this.goalStatement = "";
        this.communication = new UserCommunicationImpl();
        this.sharing = new UserSharingImpl();
        this.location = new LocationImpl();
        this.userProfilePhoto = new ImageUrlImpl();
        this.myState = UserObjectState.FULL;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username2) {
        this.username = username2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public OAuth2Credentials getOauth2Credentials() {
        return this.oAuth2Credentials;
    }

    public void setOauth2Credentials(OAuth2Credentials oAuth2Credentials2) {
        this.oAuth2Credentials = oAuth2Credentials2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName2) {
        this.firstName = firstName2;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName2) {
        this.lastName = lastName2;
    }

    public String getLastInitial() {
        return this.lastInitial;
    }

    public void setLastInitial(String lastInitial2) {
        this.lastInitial = lastInitial2;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName2) {
        this.displayName = displayName2;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDate birthdate2) {
        this.birthdate = birthdate2;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender2) {
        this.gender = gender2;
    }

    public Double getHeight() {
        return this.height;
    }

    public void setHeight(Double height2) {
        this.height = height2;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight2) {
        this.weight = weight2;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone2) {
        this.timeZone = timeZone2;
    }

    public Date getDateJoined() {
        return this.dateJoined;
    }

    public void setDateJoined(Date dateJoined2) {
        this.dateJoined = dateJoined2;
    }

    public Date getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(Date lastLogin2) {
        this.lastLogin = lastLogin2;
    }

    public MeasurementSystem getDisplayMeasurementSystem() {
        return this.displayMeasurementSystem;
    }

    public void setDisplayMeasurementSystem(MeasurementSystem displayMeasurementSystem2) {
        this.displayMeasurementSystem = displayMeasurementSystem2;
    }

    public UserCommunication getCommunication() {
        return this.communication;
    }

    public void setCommunication(UserCommunication communication2) {
        this.communication = communication2;
    }

    public UserSharing getSharing() {
        return this.sharing;
    }

    public void setSharing(UserSharing sharing2) {
        this.sharing = sharing2;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location2) {
        this.location = location2;
    }

    public void setProfilePrivacy(Privacy.Level profilePrivacy2) {
        this.profilePrivacy = PrivacyHelper.getPrivacy(profilePrivacy2);
        updatePrivacyLink("profile", profilePrivacy2);
    }

    public void setWorkoutPrivacy(Privacy.Level workoutPrivacy2) {
        this.workoutPrivacy = PrivacyHelper.getPrivacy(workoutPrivacy2);
        updatePrivacyLink("workout", workoutPrivacy2);
    }

    public void setActivityFeedPrivacy(Privacy.Level activityFeedPrivacy2) {
        this.activityFeedPrivacy = PrivacyHelper.getPrivacy(activityFeedPrivacy2);
        updatePrivacyLink(NAME_ACTIVITY_FEED, activityFeedPrivacy2);
    }

    public void setFoodLogPrivacy(Privacy.Level foodLogPrivacy2) {
        this.foodLogPrivacy = PrivacyHelper.getPrivacy(foodLogPrivacy2);
        updatePrivacyLink(NAME_FOOD_LOG, foodLogPrivacy2);
    }

    public void setEmailSearchPrivacy(Privacy.Level emailSearchPrivacy2) {
        this.emailSearchPrivacy = PrivacyHelper.getPrivacy(emailSearchPrivacy2);
        updatePrivacyLink(NAME_EMAIL_SEARCH, emailSearchPrivacy2);
    }

    public void setRoutePrivacy(Privacy.Level routePrivacy2) {
        this.routePrivacy = PrivacyHelper.getPrivacy(routePrivacy2);
        updatePrivacyLink(NAME_ROUTE, routePrivacy2);
    }

    public void setSleepPrivacy(Privacy.Level sleepPrivacy2) {
        this.sleepPrivacy = PrivacyHelper.getPrivacy(sleepPrivacy2);
        updatePrivacyLink("sleep", sleepPrivacy2);
    }

    public void setBodyMassPrivacy(Privacy.Level bodyMassPrivacy2) {
        this.bodyMassPrivacy = PrivacyHelper.getPrivacy(bodyMassPrivacy2);
        updatePrivacyLink(NAME_BODY_MASS, bodyMassPrivacy2);
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public void setIntroduction(String introduction2) {
        this.introduction = introduction2;
    }

    public String getHobbies() {
        return this.hobbies;
    }

    public void setHobbies(String hobbies2) {
        this.hobbies = hobbies2;
    }

    public String getGoalStatement() {
        return this.goalStatement;
    }

    public void setGoalStatement(String goalStatement2) {
        this.goalStatement = goalStatement2;
    }

    public String getProfileStatement() {
        return this.profileStatement;
    }

    public void setProfileStatement(String profileStatement2) {
        this.profileStatement = profileStatement2;
    }

    public EntityRef getRef() {
        return new LinkEntityRef(this.id, this.mLocalId, getHref());
    }

    public ImageUrlImpl getUserProfilePhoto() {
        return this.userProfilePhoto;
    }

    public void setUserProfilePhoto(ImageUrl userProfilePhoto2) {
        this.userProfilePhoto = (ImageUrlImpl) userProfilePhoto2;
    }

    public FriendshipListRef getFriendships() {
        return FriendshipListRef.getBuilder().setHref(getLink("friendships").getHref()).build();
    }

    public UserStatsRef getStatsByDay() {
        return UserStatsRef.getBuilder().setUser(getRef()).setAggregatePeriodUserStats(UserStatsRef.AggregatePeriodUserStats.DAY).build();
    }

    public UserStatsRef getStatsByWeek() {
        return UserStatsRef.getBuilder().setUser(getRef()).setAggregatePeriodUserStats(UserStatsRef.AggregatePeriodUserStats.WEEK).build();
    }

    public UserStatsRef getStatsByMonth() {
        return UserStatsRef.getBuilder().setUser(getRef()).setAggregatePeriodUserStats(UserStatsRef.AggregatePeriodUserStats.MONTH).build();
    }

    public UserStatsRef getStatsByYear() {
        return UserStatsRef.getBuilder().setUser(getRef()).setAggregatePeriodUserStats(UserStatsRef.AggregatePeriodUserStats.YEAR).build();
    }

    public UserStatsRef getStatsByLifetime() {
        return UserStatsRef.getBuilder().setUser(getRef()).setAggregatePeriodUserStats(UserStatsRef.AggregatePeriodUserStats.DAY).build();
    }

    private Privacy getPrivacy(String name) {
        Link link = getLink(REF_PRIVACY, name);
        if (link == null) {
            return null;
        }
        try {
            return PrivacyHelper.getPrivacyFromId(Integer.parseInt(link.getId()));
        } catch (NumberFormatException e) {
            UaLog.error("Unable to get privacy.", (Throwable) e);
            return null;
        }
    }

    private void updatePrivacyLink(String name, Privacy.Level level) {
        List<Link> links = getLinks(REF_PRIVACY);
        if (links != null) {
            Link updatedPrivacy = PrivacyHelper.toLink(level, name);
            boolean addLink = true;
            for (int i = 0; i < links.size(); i++) {
                if (links.get(i).getName().equals(name)) {
                    links.set(i, updatedPrivacy);
                    addLink = false;
                }
            }
            if (addLink) {
                links.add(updatedPrivacy);
            }
        }
    }

    public Privacy getProfilePrivacy() {
        if (this.profilePrivacy == null) {
            this.profilePrivacy = getPrivacy("profile");
        }
        return this.profilePrivacy;
    }

    public Privacy getWorkoutPrivacy() {
        if (this.workoutPrivacy == null) {
            this.workoutPrivacy = getPrivacy("workout");
        }
        return this.workoutPrivacy;
    }

    public Privacy getActivityFeedPrivacy() {
        if (this.activityFeedPrivacy == null) {
            this.activityFeedPrivacy = getPrivacy(NAME_ACTIVITY_FEED);
        }
        return this.activityFeedPrivacy;
    }

    public Privacy getFoodLogPrivacy() {
        if (this.foodLogPrivacy == null) {
            this.foodLogPrivacy = getPrivacy(NAME_FOOD_LOG);
        }
        return this.foodLogPrivacy;
    }

    public Privacy getEmailSearchPrivacy() {
        if (this.emailSearchPrivacy == null) {
            this.emailSearchPrivacy = getPrivacy(NAME_EMAIL_SEARCH);
        }
        return this.emailSearchPrivacy;
    }

    public Privacy getRoutePrivacy() {
        if (this.routePrivacy == null) {
            this.routePrivacy = getPrivacy(NAME_ROUTE);
        }
        return this.routePrivacy;
    }

    public Privacy getBodyMassPrivacy() {
        if (this.bodyMassPrivacy == null) {
            this.bodyMassPrivacy = getPrivacy(NAME_BODY_MASS);
        }
        return this.bodyMassPrivacy;
    }

    public Privacy getSleepPrivacy() {
        if (this.sleepPrivacy == null) {
            this.sleepPrivacy = getPrivacy("sleep");
        }
        return this.sleepPrivacy;
    }

    public EntityListRef<PageFollow> getFollowingRef() {
        if (this.followingRef == null) {
            this.followingRef = PageFollowListRef.getBuilder().setUserId(this.id).build();
        }
        return this.followingRef;
    }

    public UserObjectState getObjectState() {
        return this.myState;
    }

    public void setObjectState(UserObjectState state) {
        this.myState = state;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        long j;
        int ordinal;
        long j2 = -1;
        int i = -1;
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeParcelable(this.oAuth2Credentials, 0);
        dest.writeString(this.password);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.lastInitial);
        dest.writeString(this.displayName);
        dest.writeString(this.introduction);
        dest.writeString(this.hobbies);
        dest.writeString(this.goalStatement);
        dest.writeString(this.profileStatement);
        dest.writeParcelable(this.birthdate, 0);
        dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
        dest.writeValue(this.height);
        dest.writeValue(this.weight);
        dest.writeString(this.timeZone);
        if (this.dateJoined != null) {
            j = this.dateJoined.getTime();
        } else {
            j = -1;
        }
        dest.writeLong(j);
        if (this.lastLogin != null) {
            j2 = this.lastLogin.getTime();
        }
        dest.writeLong(j2);
        if (this.displayMeasurementSystem == null) {
            ordinal = -1;
        } else {
            ordinal = this.displayMeasurementSystem.ordinal();
        }
        dest.writeInt(ordinal);
        dest.writeParcelable(this.communication, 0);
        dest.writeParcelable(this.sharing, 0);
        dest.writeParcelable(this.location, 0);
        dest.writeParcelable(this.userProfilePhoto, 0);
        dest.writeParcelable(this.profilePrivacy, flags);
        dest.writeParcelable(this.workoutPrivacy, flags);
        dest.writeParcelable(this.activityFeedPrivacy, flags);
        dest.writeParcelable(this.foodLogPrivacy, flags);
        dest.writeParcelable(this.emailSearchPrivacy, flags);
        dest.writeParcelable(this.routePrivacy, flags);
        dest.writeParcelable(this.sleepPrivacy, flags);
        dest.writeParcelable(this.bodyMassPrivacy, flags);
        dest.writeParcelable(this.friendships, 0);
        if (this.myState != null) {
            i = this.myState.ordinal();
        }
        dest.writeInt(i);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private UserImpl(Parcel in) {
        super(in);
        MeasurementSystem measurementSystem;
        UserObjectState userObjectState = null;
        this.oAuth2Credentials = new OAuth2CredentialsImpl();
        this.goalStatement = "";
        this.communication = new UserCommunicationImpl();
        this.sharing = new UserSharingImpl();
        this.location = new LocationImpl();
        this.userProfilePhoto = new ImageUrlImpl();
        this.myState = UserObjectState.FULL;
        this.id = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.oAuth2Credentials = (OAuth2Credentials) in.readParcelable(OAuth2Credentials.class.getClassLoader());
        this.password = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.lastInitial = in.readString();
        this.displayName = in.readString();
        this.introduction = in.readString();
        this.hobbies = in.readString();
        this.goalStatement = in.readString();
        this.profileStatement = in.readString();
        this.birthdate = (LocalDate) in.readParcelable(LocalDate.class.getClassLoader());
        int tmpGender = in.readInt();
        this.gender = tmpGender == -1 ? null : Gender.values()[tmpGender];
        this.height = (Double) in.readValue(Double.class.getClassLoader());
        this.weight = (Double) in.readValue(Double.class.getClassLoader());
        this.timeZone = in.readString();
        long tmpDateJoined = in.readLong();
        this.dateJoined = tmpDateJoined == -1 ? null : new Date(tmpDateJoined);
        long tmpLastLogin = in.readLong();
        this.lastLogin = tmpLastLogin == -1 ? null : new Date(tmpLastLogin);
        int tmpDisplayMeasurementSystem = in.readInt();
        if (tmpDisplayMeasurementSystem == -1) {
            measurementSystem = null;
        } else {
            measurementSystem = MeasurementSystem.values()[tmpDisplayMeasurementSystem];
        }
        this.displayMeasurementSystem = measurementSystem;
        this.communication = (UserCommunication) in.readParcelable(UserCommunication.class.getClassLoader());
        this.sharing = (UserSharing) in.readParcelable(UserSharing.class.getClassLoader());
        this.location = (Location) in.readParcelable(Location.class.getClassLoader());
        this.userProfilePhoto = (ImageUrlImpl) in.readParcelable(ImageUrl.class.getClassLoader());
        this.profilePrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.workoutPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.activityFeedPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.foodLogPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.emailSearchPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.routePrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.sleepPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.bodyMassPrivacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
        this.friendships = (FriendshipListRef) in.readParcelable(FriendshipListRef.class.getClassLoader());
        int tmpMyState = in.readInt();
        this.myState = tmpMyState != -1 ? UserObjectState.values()[tmpMyState] : userObjectState;
    }
}
