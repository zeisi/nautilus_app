package com.ua.sdk.page;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.EntityRef;
import com.ua.sdk.ImageUrl;
import com.ua.sdk.UaLog;
import com.ua.sdk.actigraphy.Actigraphy;
import com.ua.sdk.actigraphy.ActigraphyTransferObject;
import com.ua.sdk.activitystory.ActivityStory;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.ImageUrlImpl;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.internal.LinkListRef;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.location.Location;
import com.ua.sdk.page.association.PageAssociation;
import com.ua.sdk.page.follow.PageFollow;
import com.ua.sdk.user.User;
import com.ua.sdk.workout.Workout;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class PageImpl extends ApiTransferObject implements Page, Parcelable {
    public static Parcelable.Creator<PageImpl> CREATOR = new Parcelable.Creator<PageImpl>() {
        public PageImpl createFromParcel(Parcel source) {
            return new PageImpl(source);
        }

        public PageImpl[] newArray(int size) {
            return new PageImpl[size];
        }
    };
    protected static final String REF_PAGE_TYPE = "privacy";
    private EntityListRef<Actigraphy> actigraphyRef;
    private EntityListRef<ActivityStory> activityFeedRef;
    private String alias;
    private ImageUrl coverPhoto;
    private EntityListRef<ActivityStory> featuredFeedRef;
    private Integer followerCount;
    private EntityListRef<PageFollow> followerRef;
    private Integer followingCount;
    private EntityListRef<PageFollow> followingRef;
    private Integer fromPageAssociationCount;
    private EntityListRef<PageAssociation> fromPageAssociationRef;
    private String headline;
    private Location location;
    private String pageDescription;
    private EntityRef<PageFollow> pageFollowRef;
    private EntityRef<Page> pageRef;
    private PageSetting pageSetting;
    private EntityRef<PageType> pageTypeRef;
    private EntityRef<PageFollow> pageUnfollowRef;
    private ImageUrl profilePhoto;
    private String title;
    private EntityListRef<PageAssociation> toPageAssocationRef;
    private Integer toPageAssociationCount;
    private URI url;
    private EntityRef<User> userRef;
    private URI website;
    private EntityListRef<Workout> workoutsRef;

    PageImpl() {
        this.profilePhoto = new ImageUrlImpl();
        this.coverPhoto = new ImageUrlImpl();
    }

    PageImpl(Page page) {
        this.profilePhoto = new ImageUrlImpl();
        this.coverPhoto = new ImageUrlImpl();
        Precondition.isNotNull(page);
        this.alias = page.getAlias();
        this.title = page.getTitle();
        this.pageDescription = page.getDescription();
        this.url = page.getUrl();
        this.website = page.getWebsite();
        this.location = page.getLocation();
        this.userRef = page.getUserRef();
        this.pageRef = page.getRef();
        this.pageTypeRef = page.getPageTypeRef();
        this.pageFollowRef = page.getPageFollowRef();
        this.pageUnfollowRef = page.getPageUnfollowRef();
        this.workoutsRef = page.getWorkoutsRef();
        this.actigraphyRef = page.getActigraphyRef();
        this.fromPageAssociationRef = page.getFromPageAssociationsRef();
        this.toPageAssocationRef = page.getToPageAssociationsRef();
        this.followerRef = page.getFollowersRef();
        this.activityFeedRef = page.getActivityFeedRef();
        this.featuredFeedRef = page.getFeaturedFeedRef();
        this.followerCount = page.getFollowerCount();
        this.fromPageAssociationCount = page.getFromPageAssociationCount();
        this.toPageAssociationCount = page.getToPageAssociationCount();
        this.profilePhoto = page.getProfilePhoto();
        this.coverPhoto = page.getCoverPhoto();
        this.headline = page.getHeadline();
        this.pageSetting = page.getPageSetting();
        this.followingCount = page.getFollowingCount();
        this.followingRef = page.getFollowingRef();
        if (page instanceof PageImpl) {
            copyLinkMap(((PageImpl) page).getLinkMap());
        }
    }

    public EntityRef<Page> getRef() {
        List<Link> links;
        if (this.pageRef == null && (links = getLinks("self")) != null) {
            this.pageRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.pageRef;
    }

    public URI getWebsite() {
        return this.website;
    }

    public String getDescription() {
        return this.pageDescription;
    }

    public String getTitle() {
        return this.title;
    }

    public URI getUrl() {
        return this.url;
    }

    public String getAlias() {
        return this.alias;
    }

    public Location getLocation() {
        return this.location;
    }

    public Integer getFollowerCount() {
        if (this.followerCount == null) {
            parseFollowersLink();
        }
        return this.followerCount;
    }

    public Integer getFollowingCount() {
        if (this.followingCount == null) {
            parseFollowersLink();
        }
        return this.followingCount;
    }

    public String getHeadline() {
        return this.headline;
    }

    public PageSetting getPageSetting() {
        return this.pageSetting;
    }

    public Integer getFromPageAssociationCount() {
        if (this.fromPageAssociationCount == null) {
            parseAssociationsLink();
        }
        return this.fromPageAssociationCount;
    }

    public Integer getToPageAssociationCount() {
        if (this.toPageAssociationCount == null) {
            parseAssociationsLink();
        }
        return this.toPageAssociationCount;
    }

    public PageTypeEnum getPageType() {
        if (getPageTypeRef() != null) {
            return PageTypeEnum.getById(this.pageTypeRef.getId());
        }
        return null;
    }

    public EntityRef<User> getUserRef() {
        List<Link> links;
        if (this.userRef == null && (links = getLinks("user")) != null) {
            this.userRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.userRef;
    }

    public EntityRef<PageType> getPageTypeRef() {
        List<Link> links;
        if (this.pageTypeRef == null && (links = getLinks("page_type")) != null) {
            this.pageTypeRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.pageTypeRef;
    }

    public EntityRef<PageFollow> getPageFollowRef() {
        List<Link> links;
        if (this.pageFollowRef == null && (links = getLinks("follow")) != null) {
            this.pageFollowRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.pageFollowRef;
    }

    public EntityRef<PageFollow> getPageUnfollowRef() {
        List<Link> links;
        if (this.pageUnfollowRef == null && (links = getLinks("unfollow")) != null) {
            this.pageUnfollowRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.pageUnfollowRef;
    }

    public EntityListRef<Workout> getWorkoutsRef() {
        List<Link> links;
        if (this.workoutsRef == null && (links = getLinks(com.nautilus.omni.model.dto.User.WORKOUTS)) != null) {
            this.workoutsRef = new LinkListRef(links.get(0).getHref());
        }
        return this.workoutsRef;
    }

    public EntityListRef<Actigraphy> getActigraphyRef() {
        List<Link> links;
        if (this.actigraphyRef == null && (links = getLinks(ActigraphyTransferObject.KEY_ACTIGRAPHY)) != null) {
            this.actigraphyRef = new LinkListRef(links.get(0).getHref());
        }
        return this.actigraphyRef;
    }

    public EntityListRef<PageAssociation> getFromPageAssociationsRef() {
        if (this.fromPageAssociationRef == null) {
            parseAssociationsLink();
        }
        return this.fromPageAssociationRef;
    }

    public EntityListRef<PageAssociation> getToPageAssociationsRef() {
        if (this.toPageAssocationRef == null) {
            parseAssociationsLink();
        }
        return this.toPageAssocationRef;
    }

    public EntityListRef<PageFollow> getFollowersRef() {
        if (this.followerRef == null) {
            parseFollowersLink();
        }
        return this.followerRef;
    }

    public EntityListRef<PageFollow> getFollowingRef() {
        if (this.followingRef == null) {
            parseFollowersLink();
        }
        return this.followingRef;
    }

    public EntityListRef<ActivityStory> getActivityFeedRef() {
        Link link;
        if (this.activityFeedRef == null && (link = getLink("activity_feed", "activity_feed")) != null) {
            this.activityFeedRef = new LinkListRef(link.getHref());
        }
        return this.activityFeedRef;
    }

    public EntityListRef<ActivityStory> getFeaturedFeedRef() {
        Link link;
        if (this.featuredFeedRef == null && (link = getLink("activity_feed", "featured")) != null) {
            this.featuredFeedRef = new LinkListRef(link.getHref());
        }
        return this.featuredFeedRef;
    }

    private void parseFollowersLink() {
        List<Link> links = getLinks("followers");
        if (links != null) {
            for (Link link : links) {
                if (link.getName().equals("followers")) {
                    this.followerRef = new LinkListRef(link.getHref());
                    this.followerCount = link.getCount();
                } else if (link.getName().equals("following")) {
                    this.followingRef = new LinkListRef(link.getHref());
                    this.followingCount = link.getCount();
                }
            }
        }
    }

    private void parseAssociationsLink() {
        List<Link> links = getLinks("associations");
        if (links != null) {
            for (Link link : links) {
                if (link.getName().equals("from")) {
                    this.fromPageAssociationRef = new LinkListRef(link.getHref());
                    this.fromPageAssociationCount = link.getCount();
                } else if (link.getName().equals("to")) {
                    this.toPageAssocationRef = new LinkListRef(link.getHref());
                    this.toPageAssociationCount = link.getCount();
                }
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void setAlias(String alias2) {
        this.alias = alias2;
    }

    /* access modifiers changed from: package-private */
    public void setTitle(String title2) {
        this.title = title2;
    }

    /* access modifiers changed from: package-private */
    public void setPageDescription(String pageDescription2) {
        this.pageDescription = pageDescription2;
    }

    /* access modifiers changed from: package-private */
    public void setUrl(URI url2) {
        this.url = url2;
    }

    /* access modifiers changed from: package-private */
    public void setWebsite(URI website2) {
        this.website = website2;
    }

    /* access modifiers changed from: package-private */
    public void setLocation(Location location2) {
        this.location = location2;
    }

    /* access modifiers changed from: package-private */
    public void setUserRef(EntityRef<User> userRef2) {
        this.userRef = userRef2;
    }

    /* access modifiers changed from: package-private */
    public void setPageRef(EntityRef<Page> pageRef2) {
        this.pageRef = pageRef2;
    }

    /* access modifiers changed from: package-private */
    public void setPageTypeRef(EntityRef<PageType> pageTypeRef2) {
        this.pageTypeRef = pageTypeRef2;
    }

    /* access modifiers changed from: package-private */
    public void setPageFollowRef(EntityRef<PageFollow> pageFollowRef2) {
        this.pageFollowRef = pageFollowRef2;
    }

    /* access modifiers changed from: package-private */
    public void setPageUnfollowRef(EntityRef<PageFollow> pageUnfollowRef2) {
        this.pageUnfollowRef = pageUnfollowRef2;
    }

    /* access modifiers changed from: package-private */
    public void setWorkoutsRef(EntityListRef<Workout> workoutsRef2) {
        this.workoutsRef = workoutsRef2;
    }

    /* access modifiers changed from: package-private */
    public void setActigraphyRef(EntityListRef<Actigraphy> actigraphyRef2) {
        this.actigraphyRef = actigraphyRef2;
    }

    /* access modifiers changed from: package-private */
    public void setFromPageAssociationRef(EntityListRef<PageAssociation> associationRef) {
        this.fromPageAssociationRef = associationRef;
    }

    /* access modifiers changed from: package-private */
    public void setToPageAssociationRef(EntityListRef<PageAssociation> associationRef) {
        this.toPageAssocationRef = associationRef;
    }

    /* access modifiers changed from: package-private */
    public void setFollowerRef(EntityListRef<PageFollow> followerRef2) {
        this.followerRef = followerRef2;
    }

    /* access modifiers changed from: package-private */
    public void setFollowingRef(EntityListRef<PageFollow> followingRef2) {
        this.followingRef = followingRef2;
    }

    /* access modifiers changed from: package-private */
    public void setActivityFeedRef(EntityListRef<ActivityStory> activityFeedRef2) {
        this.activityFeedRef = activityFeedRef2;
    }

    /* access modifiers changed from: package-private */
    public void setFeaturedFeedRef(EntityListRef<ActivityStory> featuredFeedRef2) {
        this.featuredFeedRef = featuredFeedRef2;
    }

    /* access modifiers changed from: package-private */
    public void setFollowerCount(Integer followerCount2) {
        this.followerCount = followerCount2;
    }

    /* access modifiers changed from: package-private */
    public void setFollowingCount(Integer followingCount2) {
        this.followingCount = followingCount2;
    }

    /* access modifiers changed from: package-private */
    public void setFromPageAssociationCount(Integer fromPageAssociationCount2) {
        this.fromPageAssociationCount = fromPageAssociationCount2;
    }

    /* access modifiers changed from: package-private */
    public void setToPageAssociationCount(Integer toPageAssociationCount2) {
        this.toPageAssociationCount = toPageAssociationCount2;
    }

    /* access modifiers changed from: package-private */
    public void setHeadline(String headline2) {
        this.headline = headline2;
    }

    /* access modifiers changed from: package-private */
    public void setPageSetting(PageSetting pageSetting2) {
        this.pageSetting = pageSetting2;
    }

    public ImageUrl getProfilePhoto() {
        return this.profilePhoto;
    }

    public void setProfilePhoto(ImageUrlImpl profilePhoto2) {
        this.profilePhoto = profilePhoto2;
    }

    public ImageUrl getCoverPhoto() {
        return this.coverPhoto;
    }

    public void setCoverPhoto(ImageUrlImpl coverPhoto2) {
        this.coverPhoto = coverPhoto2;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.alias);
        dest.writeString(this.title);
        dest.writeString(this.pageDescription);
        if (this.url == null) {
            dest.writeString("");
        } else {
            dest.writeString(this.url.toString());
        }
        if (this.website == null) {
            dest.writeString("");
        } else {
            dest.writeString(this.website.toString());
        }
        dest.writeParcelable(this.location, 0);
        dest.writeParcelable(this.userRef, 0);
        dest.writeParcelable(this.pageRef, 0);
        dest.writeParcelable(this.pageTypeRef, 0);
        dest.writeParcelable(this.pageFollowRef, 0);
        dest.writeParcelable(this.pageUnfollowRef, 0);
        dest.writeParcelable(this.workoutsRef, 0);
        dest.writeParcelable(this.actigraphyRef, 0);
        dest.writeParcelable(this.fromPageAssociationRef, 0);
        dest.writeParcelable(this.toPageAssocationRef, 0);
        dest.writeParcelable(this.followerRef, 0);
        dest.writeParcelable(this.activityFeedRef, 0);
        dest.writeParcelable(this.featuredFeedRef, 0);
        dest.writeValue(this.followerCount);
        dest.writeValue(this.fromPageAssociationCount);
        dest.writeValue(this.toPageAssociationCount);
        dest.writeParcelable(this.coverPhoto, 0);
        dest.writeParcelable(this.profilePhoto, 0);
        dest.writeString(this.headline);
        dest.writeParcelable(this.pageSetting, 0);
        dest.writeParcelable(this.followingRef, 0);
        dest.writeValue(this.followingCount);
    }

    private PageImpl(Parcel in) {
        super(in);
        this.profilePhoto = new ImageUrlImpl();
        this.coverPhoto = new ImageUrlImpl();
        this.alias = in.readString();
        this.title = in.readString();
        this.pageDescription = in.readString();
        String inUrl = in.readString();
        if (inUrl.equals("")) {
            this.url = null;
        } else {
            try {
                this.url = new URI(inUrl);
            } catch (URISyntaxException e) {
                UaLog.error("Error unparceling Page URL: " + inUrl, (Throwable) e);
            }
        }
        String inWebsite = in.readString();
        if (inWebsite.equals("")) {
            this.website = null;
        } else {
            try {
                this.website = new URI(inWebsite);
            } catch (URISyntaxException e2) {
                UaLog.error("Error unparceling Page website: " + inWebsite, (Throwable) e2);
            }
        }
        this.location = (Location) in.readParcelable(Location.class.getClassLoader());
        this.userRef = (EntityRef) in.readParcelable(LinkEntityRef.class.getClassLoader());
        this.pageRef = (EntityRef) in.readParcelable(LinkEntityRef.class.getClassLoader());
        this.pageTypeRef = (EntityRef) in.readParcelable(LinkEntityRef.class.getClassLoader());
        this.pageFollowRef = (EntityRef) in.readParcelable(LinkEntityRef.class.getClassLoader());
        this.pageUnfollowRef = (EntityRef) in.readParcelable(LinkEntityRef.class.getClassLoader());
        this.workoutsRef = (EntityListRef) in.readParcelable(LinkListRef.class.getClassLoader());
        this.actigraphyRef = (EntityListRef) in.readParcelable(LinkListRef.class.getClassLoader());
        this.fromPageAssociationRef = (EntityListRef) in.readParcelable(LinkListRef.class.getClassLoader());
        this.toPageAssocationRef = (EntityListRef) in.readParcelable(LinkListRef.class.getClassLoader());
        this.followerRef = (EntityListRef) in.readParcelable(LinkListRef.class.getClassLoader());
        this.activityFeedRef = (EntityListRef) in.readParcelable(LinkListRef.class.getClassLoader());
        this.featuredFeedRef = (EntityListRef) in.readParcelable(LinkListRef.class.getClassLoader());
        this.followerCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fromPageAssociationCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.toPageAssociationCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.coverPhoto = (ImageUrl) in.readParcelable(ImageUrl.class.getClassLoader());
        this.profilePhoto = (ImageUrl) in.readParcelable(ImageUrl.class.getClassLoader());
        this.headline = in.readString();
        this.pageSetting = (PageSetting) in.readParcelable(PageSetting.class.getClassLoader());
        this.followingRef = (EntityListRef) in.readParcelable(LinkListRef.class.getClassLoader());
        this.followingCount = (Integer) in.readValue(Integer.class.getClassLoader());
    }
}
