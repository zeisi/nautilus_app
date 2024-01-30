package com.ua.sdk.route;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RouteTO extends ApiTransferObject {
    @SerializedName("city")
    String city;
    @SerializedName("climbs")
    List<ClimbImpl> climbs;
    @SerializedName("country")
    String country;
    @SerializedName("created_datetime")
    Date createdDate;
    @SerializedName("data_source")
    String dataSource;
    @SerializedName("description")
    String description;
    @SerializedName("distance")
    Double distanceMeters;
    @SerializedName("max_elevation")
    Double maxElevation;
    @SerializedName("min_elevation")
    Double minElevation;
    @SerializedName("name")
    String name;
    @SerializedName("postal_code")
    String postalCode;
    @SerializedName("points")
    List<PointImpl> routePoints;
    @SerializedName("start_point_type")
    String startPointType;
    @SerializedName("starting_location")
    StartingLocation startingLocation;
    @SerializedName("state")
    String state;
    @SerializedName("total_ascent")
    Double totalAscent;
    @SerializedName("total_descent")
    Double totalDescent;
    @SerializedName("updated_datetime")
    Date updatedDate;

    public static RouteImpl fromTransferObject(RouteTO from) {
        if (from == null) {
            return null;
        }
        RouteImpl answer = new RouteImpl();
        answer.setCity(from.city);
        answer.setCountry(from.country);
        answer.setState(from.state);
        answer.setPostalCode(from.postalCode);
        answer.setDistanceMeters(from.distanceMeters);
        answer.setName(from.name);
        answer.setDescription(from.description);
        answer.setDataSource(from.dataSource);
        answer.setCreatedDate(from.createdDate);
        answer.setUpdatedDate(from.updatedDate);
        answer.setTotalAscent(from.totalAscent);
        answer.setTotalDescent(from.totalDescent);
        answer.setMinElevation(from.minElevation);
        answer.setMaxElevation(from.maxElevation);
        answer.setStartPointType(from.startPointType);
        answer.setStartingLocation(from.startingLocation);
        if (from.routePoints != null) {
            answer.routePoints.addAll(from.routePoints);
        }
        if (from.climbs != null) {
            answer.climbs.addAll(from.climbs);
        }
        for (String key : from.getLinkKeys()) {
            answer.setLinksForRelation(key, from.getLinks(key));
        }
        return answer;
    }

    public static RouteTO toTransferObject(Route entity) {
        if (entity == null) {
            return null;
        }
        RouteImpl route = (RouteImpl) entity;
        RouteTO to = new RouteTO();
        to.city = route.getCity();
        to.country = route.getCountry();
        to.state = route.getState();
        to.postalCode = route.getPostalCode();
        to.distanceMeters = route.getDistanceMeters();
        to.name = route.getName();
        to.description = route.getDescription();
        to.dataSource = route.getDataSource();
        to.createdDate = route.getCreatedDate();
        to.updatedDate = route.getUpdatedDate();
        to.totalAscent = route.getTotalAscent();
        to.totalDescent = route.getTotalDescent();
        to.minElevation = route.getMinElevation();
        to.maxElevation = route.getMaxElevation();
        to.startPointType = route.getStartPointType();
        to.startingLocation = route.getStartingLocation();
        if (route.routePoints != null) {
            to.routePoints = new ArrayList();
            Iterator i$ = route.routePoints.iterator();
            while (i$.hasNext()) {
                to.routePoints.add((PointImpl) i$.next());
            }
        }
        if (route.climbs != null) {
            to.climbs = new ArrayList();
            Iterator i$2 = route.climbs.iterator();
            while (i$2.hasNext()) {
                to.climbs.add((ClimbImpl) i$2.next());
            }
        }
        to.setLinkMap(route.getLinkMap());
        return to;
    }
}
