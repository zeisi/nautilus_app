package com.ua.sdk.route;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.UaLog;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;
import com.ua.sdk.privacy.Privacy;
import com.ua.sdk.privacy.PrivacyHelper;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteImpl extends ApiTransferObject implements Route, Parcelable {
    public static final Parcelable.Creator<RouteImpl> CREATOR = new Parcelable.Creator<RouteImpl>() {
        public RouteImpl createFromParcel(Parcel source) {
            return new RouteImpl(source);
        }

        public RouteImpl[] newArray(int size) {
            return new RouteImpl[size];
        }
    };
    EntityRef<ActivityType> activityTypeRef;
    String city;
    ArrayList<Climb> climbs;
    String country;
    Date createdDate;
    String dataSource;
    String description;
    Double distanceMeters;
    Double maxElevation;
    Double minElevation;
    String name;
    String postalCode;
    Privacy privacy;
    ArrayList<Point> routePoints;
    EntityRef<Route> routeRef;
    String startPointType;
    StartingLocation startingLocation;
    String state;
    String thumbnailLink;
    Double totalAscent;
    Double totalDescent;
    Date updatedDate;
    EntityRef<User> userRef;

    public RouteImpl() {
        this.routePoints = new ArrayList<>();
        this.climbs = new ArrayList<>();
    }

    protected RouteImpl(RouteBuilderImpl builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.routePoints = builder.points;
        this.startPointType = builder.startPointType;
        this.postalCode = builder.postalCode;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getState() {
        return this.state;
    }

    public StartingLocation getStartingLocation() {
        return this.startingLocation;
    }

    public Double getStartingLatitude() {
        return Double.valueOf(this.startingLocation.coordinates[0]);
    }

    public Double getStartingLongitude() {
        return Double.valueOf(this.startingLocation.coordinates[1]);
    }

    public String getStartPointType() {
        return this.startPointType;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Double getDistanceMeters() {
        return this.distanceMeters;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public Point getPointAt(int index) {
        return this.routePoints.get(index);
    }

    public Double getLatitudeAt(int index) {
        return this.routePoints.get(index).getLatitude();
    }

    public Double getLongitudeAt(int index) {
        return this.routePoints.get(index).getLongitude();
    }

    public Double getElevationAt(int index) {
        return this.routePoints.get(index).getElevation();
    }

    public Double getDistanceAt(int index) {
        return this.routePoints.get(index).getDistanceMeters();
    }

    public int getTotalPoints() {
        return this.routePoints.size();
    }

    public ArrayList<Climb> getClimbs() {
        return this.climbs;
    }

    public Double getTotalAscent() {
        return this.totalAscent;
    }

    public Double getTotalDescent() {
        return this.totalDescent;
    }

    public Double getMinElevation() {
        return this.minElevation;
    }

    public Double getMaxElevation() {
        return this.maxElevation;
    }

    public Privacy getPrivacy() {
        List<Link> links;
        if (this.privacy == null && (links = getLinks("privacy")) != null) {
            try {
                this.privacy = PrivacyHelper.getPrivacyFromId(Integer.parseInt(links.get(0).getId()));
            } catch (NumberFormatException e) {
                UaLog.error("Unable to get privacy.", (Throwable) e);
                return null;
            }
        }
        return this.privacy;
    }

    public void setCity(String city2) {
        this.city = city2;
    }

    public void setCountry(String country2) {
        this.country = country2;
    }

    public void setState(String state2) {
        this.state = state2;
    }

    public void setPostalCode(String postalCode2) {
        this.postalCode = postalCode2;
    }

    public void setDistanceMeters(Double distanceMeters2) {
        this.distanceMeters = distanceMeters2;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public void setDataSource(String dataSource2) {
        this.dataSource = dataSource2;
    }

    public void setCreatedDate(Date createdDate2) {
        this.createdDate = createdDate2;
    }

    public void setUpdatedDate(Date updatedDate2) {
        this.updatedDate = updatedDate2;
    }

    public void setTotalAscent(Double totalAscent2) {
        this.totalAscent = totalAscent2;
    }

    public void setTotalDescent(Double totalDescent2) {
        this.totalDescent = totalDescent2;
    }

    public void setMinElevation(Double minElevation2) {
        this.minElevation = minElevation2;
    }

    public void setMaxElevation(Double maxElevation2) {
        this.maxElevation = maxElevation2;
    }

    public void setPrivacy(Privacy privacy2) {
        this.privacy = privacy2;
    }

    public void setUserRef(EntityRef<User> userRef2) {
        this.userRef = userRef2;
    }

    public void setRouteRef(EntityRef<Route> routeRef2) {
        this.routeRef = routeRef2;
    }

    public void setActivityTypeRef(EntityRef<ActivityType> activityTypeRef2) {
        this.activityTypeRef = activityTypeRef2;
    }

    public void setStartPointType(String startPointType2) {
        this.startPointType = startPointType2;
    }

    public void setStartingLocation(StartingLocation startingLocation2) {
        this.startingLocation = startingLocation2;
    }

    public void setThumbnailLink(String thumbnailLink2) {
        this.thumbnailLink = thumbnailLink2;
    }

    public void setRoutePoints(ArrayList<Point> routePoints2) {
        this.routePoints = routePoints2;
    }

    public void setClimbs(ArrayList<Climb> climbs2) {
        this.climbs = climbs2;
    }

    public EntityRef<User> getUserRef() {
        List<Link> links;
        if (this.userRef == null && (links = getLinks("user")) != null) {
            this.userRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.userRef;
    }

    public EntityRef<ActivityType> getActivityTypeRef() {
        List<Link> links;
        if (this.activityTypeRef == null && (links = getLinks("activity_type")) != null) {
            this.activityTypeRef = new LinkEntityRef(links.get(0).getId(), links.get(0).getHref());
        }
        return this.activityTypeRef;
    }

    public String getThumbnailLink() {
        List<Link> links;
        if (this.thumbnailLink == null && (links = getLinks("thumbnail")) != null) {
            this.thumbnailLink = links.get(0).getHref();
        }
        return this.thumbnailLink;
    }

    public RouteRef getRef() {
        Link self = getLink("self");
        if (self == null) {
            return null;
        }
        return new RouteRef(self.getId(), self.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        long j = -1;
        super.writeToParcel(dest, flags);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.state);
        dest.writeString(this.postalCode);
        dest.writeValue(this.distanceMeters);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.dataSource);
        dest.writeLong(this.createdDate != null ? this.createdDate.getTime() : -1);
        if (this.updatedDate != null) {
            j = this.updatedDate.getTime();
        }
        dest.writeLong(j);
        dest.writeValue(this.totalAscent);
        dest.writeValue(this.totalDescent);
        dest.writeValue(this.minElevation);
        dest.writeValue(this.maxElevation);
        dest.writeString(this.thumbnailLink);
        dest.writeList(this.routePoints);
        dest.writeList(this.climbs);
        dest.writeLong(this.mLocalId);
        dest.writeString(this.startPointType);
        dest.writeParcelable(this.startingLocation, flags);
        dest.writeParcelable(this.userRef, flags);
        dest.writeParcelable(this.routeRef, flags);
        dest.writeParcelable(this.activityTypeRef, flags);
        dest.writeParcelable(this.privacy, flags);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private RouteImpl(Parcel in) {
        super(in);
        Date date = null;
        this.city = in.readString();
        this.country = in.readString();
        this.state = in.readString();
        this.postalCode = in.readString();
        this.distanceMeters = (Double) in.readValue(Double.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.dataSource = in.readString();
        long tmpCreatedDate = in.readLong();
        this.createdDate = tmpCreatedDate == -1 ? null : new Date(tmpCreatedDate);
        long tmpUpdatedDate = in.readLong();
        this.updatedDate = tmpUpdatedDate != -1 ? new Date(tmpUpdatedDate) : date;
        this.totalAscent = (Double) in.readValue(Double.class.getClassLoader());
        this.totalDescent = (Double) in.readValue(Double.class.getClassLoader());
        this.minElevation = (Double) in.readValue(Double.class.getClassLoader());
        this.maxElevation = (Double) in.readValue(Double.class.getClassLoader());
        this.thumbnailLink = in.readString();
        this.routePoints = in.readArrayList(PointImpl.class.getClassLoader());
        this.climbs = in.readArrayList(ClimbImpl.class.getClassLoader());
        this.mLocalId = in.readLong();
        this.startPointType = in.readString();
        this.startingLocation = (StartingLocation) in.readParcelable(StartingLocation.class.getClassLoader());
        this.userRef = (EntityRef) in.readParcelable(LinkEntityRef.class.getClassLoader());
        this.routeRef = (EntityRef) in.readParcelable(LinkEntityRef.class.getClassLoader());
        this.activityTypeRef = (EntityRef) in.readParcelable(LinkEntityRef.class.getClassLoader());
        this.privacy = (Privacy) in.readParcelable(Privacy.class.getClassLoader());
    }
}
