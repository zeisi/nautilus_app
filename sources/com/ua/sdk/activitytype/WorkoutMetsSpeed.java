package com.ua.sdk.activitytype;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.UaLog;
import com.ua.sdk.util.Convert;
import com.ua.sdk.util.Utility;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class WorkoutMetsSpeed implements Parcelable {
    public static Parcelable.Creator<WorkoutMetsSpeed> CREATOR = new Parcelable.Creator<WorkoutMetsSpeed>() {
        public WorkoutMetsSpeed createFromParcel(Parcel source) {
            return new WorkoutMetsSpeed(source);
        }

        public WorkoutMetsSpeed[] newArray(int size) {
            return new WorkoutMetsSpeed[size];
        }
    };
    private Double mMetersPerSec;
    private Double mMets;
    private Double mMph;

    public WorkoutMetsSpeed() {
    }

    public WorkoutMetsSpeed(Double mps, Double mets) {
        Double d;
        Double d2;
        Double d3 = null;
        if (mps != null) {
            d = Convert.meterPerSecToMilePerHour(mps);
        } else {
            d = null;
        }
        this.mMph = d;
        if (mps != null) {
            d2 = Double.valueOf(mps.doubleValue());
        } else {
            d2 = null;
        }
        this.mMetersPerSec = d2;
        this.mMets = mets != null ? Double.valueOf(mets.doubleValue()) : d3;
    }

    public Double getMets() {
        if (this.mMets != null) {
            return Double.valueOf(this.mMets.doubleValue());
        }
        return null;
    }

    public Double getSpeed() {
        if (this.mMetersPerSec != null) {
            return Double.valueOf(this.mMetersPerSec.doubleValue());
        }
        return null;
    }

    public Double getSpeedMilesPerHour() {
        if (this.mMph != null) {
            return Double.valueOf(this.mMph.doubleValue());
        }
        return null;
    }

    public boolean isValid() {
        return (this.mMph == null || this.mMets == null) ? false : true;
    }

    public void setMets(Double mets) {
        this.mMets = mets != null ? Double.valueOf(mets.doubleValue()) : null;
    }

    public void setSpeed(Double mph) {
        Double d;
        Double d2 = null;
        if (mph != null) {
            d = Double.valueOf(mph.doubleValue());
        } else {
            d = null;
        }
        this.mMph = d;
        if (mph != null) {
            d2 = Convert.milePerHourToMeterPerSecond(mph);
        }
        this.mMetersPerSec = d2;
    }

    public static List<WorkoutMetsSpeed> parseSpeedList(String metsSpeed) {
        List<WorkoutMetsSpeed> list = new ArrayList<>();
        if (!Utility.isEmpty(metsSpeed)) {
            try {
                Object obj = new JSONTokener(metsSpeed).nextValue();
                if (obj instanceof JSONObject) {
                    JSONObject jsonObj = (JSONObject) obj;
                    Iterator<String> keys = jsonObj.keys();
                    if (keys.hasNext()) {
                        do {
                            String key = keys.next();
                            Double mets = Double.valueOf(Double.parseDouble(key));
                            Double mps = Double.valueOf(Double.parseDouble(jsonObj.getString(key)));
                            if (!(mps == null || mets == null)) {
                                WorkoutMetsSpeed add = new WorkoutMetsSpeed(mps, mets);
                                if (add.isValid()) {
                                    list.add(add);
                                }
                            }
                        } while (keys.hasNext());
                    }
                }
                Collections.sort(list, new Comparable());
            } catch (JSONException e) {
                UaLog.error("Malformed JSON", (Throwable) e);
                list.clear();
            } catch (NumberFormatException e2) {
                UaLog.error("Expected Number Value : %s\n, %s", metsSpeed, e2);
                list.clear();
            }
        }
        return list;
    }

    public static class Comparable implements Comparator<WorkoutMetsSpeed> {
        public int compare(WorkoutMetsSpeed ms1, WorkoutMetsSpeed ms2) {
            Double s1 = ms1.getSpeedMilesPerHour();
            Double s2 = ms2.getSpeedMilesPerHour();
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s2 == null) {
                return 1;
            }
            if (s1 == null) {
                return -1;
            }
            if (s1.doubleValue() > s2.doubleValue()) {
                return 1;
            }
            if (s1 != s2) {
                return -1;
            }
            return 0;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mMph);
        dest.writeValue(this.mMetersPerSec);
        dest.writeValue(this.mMets);
    }

    private WorkoutMetsSpeed(Parcel in) {
        this.mMph = (Double) in.readValue(Double.class.getClassLoader());
        this.mMetersPerSec = (Double) in.readValue(Double.class.getClassLoader());
        this.mMets = (Double) in.readValue(Double.class.getClassLoader());
    }
}
