package com.ua.sdk.workout;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityRef;
import com.ua.sdk.activitystory.Attachment;
import com.ua.sdk.activitystory.Attachments;
import com.ua.sdk.activitystory.SocialSettings;
import com.ua.sdk.activitytype.ActivityTypeRef;
import com.ua.sdk.gear.user.UserGearRef;
import com.ua.sdk.privacy.Privacy;
import com.ua.sdk.route.RouteRef;
import com.ua.sdk.user.User;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

public class WorkoutBuilderImpl implements WorkoutBuilder, Parcelable {
    public static final Parcelable.Creator<WorkoutBuilderImpl> CREATOR = new Parcelable.Creator<WorkoutBuilderImpl>() {
        public WorkoutBuilderImpl createFromParcel(Parcel source) {
            return new WorkoutBuilderImpl(source);
        }

        public WorkoutBuilderImpl[] newArray(int size) {
            return new WorkoutBuilderImpl[size];
        }
    };
    ActivityTypeRef activityTypeEntityRef;
    Attachments attachments;
    private boolean buildNewTimeSeries;
    Date createdTime;
    boolean hasTimeSeries;
    private boolean isUpdate;
    long localId;
    private double maxOffset;
    String name;
    String notes;
    Privacy.Level privacy;
    String referenceKey;
    RouteRef routeRef;
    SocialSettings socialSettings;
    String source;
    Date startTime;
    WorkoutTimeSeriesImpl timeSeries;
    TimeZone timeZone;
    Date updateTime;
    private boolean updatedCadence;
    private boolean updatedHeartRate;
    private boolean updatedPower;
    private boolean updatedSpeed;
    private boolean updatedTorque;
    UserGearRef userGearEntityRef;
    EntityRef<User> userRef;
    WorkoutAggregatesImpl workoutAggregates;
    TimeSeriesImpl<WorkoutCadenceEntry> workoutCadenceEntryTimeSeries;
    TimeSeriesImpl<WorkoutDistanceEntry> workoutDistanceTimeSeries;
    TimeSeriesImpl<WorkoutHeartRateEntry> workoutHeartRateEntryTimeSeries;
    TimeSeriesImpl<WorkoutPositionEntry> workoutPositionEntryTimeSeries;
    TimeSeriesImpl<WorkoutPowerEntry> workoutPowerEntryTimeSeries;
    WorkoutRef workoutRef;
    TimeSeriesImpl<WorkoutSpeedEntry> workoutSpeedEntryTimeSeries;
    TimeSeriesImpl<WorkoutStepsEntry> workoutStepsEntryTimeSeries;
    TimeSeriesImpl<WorkoutTimerStopEntry> workoutStopTimeEntryTimeSeries;
    TimeSeriesImpl<WorkoutTorqueEntry> workoutTorqueEntryTimeSeries;

    public WorkoutBuilderImpl() {
        this.maxOffset = 0.0d;
        this.buildNewTimeSeries = false;
        this.isUpdate = false;
        this.updatedHeartRate = false;
        this.updatedSpeed = false;
        this.updatedCadence = false;
        this.updatedPower = false;
        this.updatedTorque = false;
    }

    public WorkoutBuilderImpl(Workout workout, boolean invalidateTimeSeries) {
        this.maxOffset = 0.0d;
        this.buildNewTimeSeries = false;
        this.isUpdate = false;
        this.updatedHeartRate = false;
        this.updatedSpeed = false;
        this.updatedCadence = false;
        this.updatedPower = false;
        this.updatedTorque = false;
        this.startTime = workout.getStartTime();
        this.updateTime = workout.getUpdatedTime();
        this.createdTime = workout.getCreatedTime();
        this.name = workout.getName();
        this.notes = workout.getNotes();
        this.timeZone = workout.getTimeZone();
        this.source = workout.getSource();
        this.referenceKey = workout.getReferenceKey();
        this.workoutAggregates = (WorkoutAggregatesImpl) workout.getAggregates();
        this.activityTypeEntityRef = workout.getActivityTypeRef();
        this.userGearEntityRef = workout.getUserGearRef();
        this.privacy = workout.getPrivacy().getLevel();
        this.socialSettings = workout.getSocialSettings();
        this.workoutRef = (WorkoutRef) workout.getRef();
        this.routeRef = workout.getRouteRef();
        this.userRef = workout.getUserRef();
        if (!invalidateTimeSeries) {
            this.timeSeries = (WorkoutTimeSeriesImpl) workout.getTimeSeriesData();
            if (this.timeSeries != null) {
                this.workoutHeartRateEntryTimeSeries = ((WorkoutTimeSeriesImpl) workout.getTimeSeriesData()).workoutHeartRateEntryTimeSeries;
                this.workoutSpeedEntryTimeSeries = ((WorkoutTimeSeriesImpl) workout.getTimeSeriesData()).workoutSpeedEntryTimeSeries;
                this.workoutCadenceEntryTimeSeries = ((WorkoutTimeSeriesImpl) workout.getTimeSeriesData()).workoutCadenceEntryTimeSeries;
                this.workoutPowerEntryTimeSeries = ((WorkoutTimeSeriesImpl) workout.getTimeSeriesData()).workoutPowerEntryTimeSeries;
                this.workoutTorqueEntryTimeSeries = ((WorkoutTimeSeriesImpl) workout.getTimeSeriesData()).workoutTorqueEntryTimeSeries;
                this.workoutDistanceTimeSeries = ((WorkoutTimeSeriesImpl) workout.getTimeSeriesData()).workoutDistanceTimeSeries;
                this.workoutStepsEntryTimeSeries = ((WorkoutTimeSeriesImpl) workout.getTimeSeriesData()).workoutStepsEntryTimeSeries;
                this.workoutPositionEntryTimeSeries = ((WorkoutTimeSeriesImpl) workout.getTimeSeriesData()).workoutPositionEntryTimeSeries;
                this.workoutStopTimeEntryTimeSeries = ((WorkoutTimeSeriesImpl) workout.getTimeSeriesData()).workoutStopTimeEntryTimeSeries;
            }
        }
        if (!(this.workoutAggregates == null || this.workoutAggregates.getElapsedTimeTotal() == null)) {
            this.maxOffset = this.workoutAggregates.getElapsedTimeTotal().doubleValue();
        }
        this.isUpdate = true;
    }

    public WorkoutBuilder setLocalId(Long localId2) {
        this.localId = localId2.longValue();
        return this;
    }

    public WorkoutBuilder setStartTime(Date startTime2) {
        this.startTime = startTime2;
        return this;
    }

    public WorkoutBuilder setCreateTime(Date createTime) {
        this.createdTime = createTime;
        return this;
    }

    public WorkoutBuilder setUpdateTime(Date updateTime2) {
        this.updateTime = updateTime2;
        return this;
    }

    public WorkoutBuilder setName(String name2) {
        this.name = name2;
        return this;
    }

    public WorkoutBuilder setNotes(String notes2) {
        this.notes = notes2;
        return this;
    }

    public WorkoutBuilder setTimeZone(TimeZone timeZone2) {
        this.timeZone = timeZone2;
        return this;
    }

    public WorkoutBuilder setSource(String source2) {
        this.source = source2;
        return this;
    }

    public WorkoutBuilder setReferenceKey(String referenceKey2) {
        this.referenceKey = referenceKey2;
        return this;
    }

    public WorkoutBuilder setHasTimeSeries(Boolean hasTimeSeries2) {
        if (hasTimeSeries2 == null) {
            hasTimeSeries2 = Boolean.FALSE;
        }
        this.hasTimeSeries = hasTimeSeries2.booleanValue();
        return this;
    }

    public WorkoutBuilder setActivityType(ActivityTypeRef ref) {
        this.activityTypeEntityRef = ref;
        return this;
    }

    public WorkoutBuilder setUserGear(UserGearRef ref) {
        this.userGearEntityRef = ref;
        return this;
    }

    public WorkoutBuilder setPrivacy(Privacy.Level privacy2) {
        this.privacy = privacy2;
        return this;
    }

    public WorkoutBuilder setSocialSettings(SocialSettings socialSettings2) {
        this.socialSettings = socialSettings2;
        return this;
    }

    public WorkoutBuilder setRouteRef(RouteRef routeRef2) {
        this.routeRef = routeRef2;
        return this;
    }

    public WorkoutBuilder addAttachment(Attachment.Type attachmentType) {
        if (this.attachments == null) {
            this.attachments = new Attachments();
        }
        this.attachments.addAttachment(attachmentType);
        return this;
    }

    public WorkoutBuilder setTotalTime(Double secondsActive, Double secondsTotal) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.activeTimeTotal = secondsActive;
        this.workoutAggregates.elapsedTimeTotal = secondsTotal;
        return this;
    }

    public WorkoutBuilder setTotalDistance(Double meters) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.distanceTotal = meters;
        return this;
    }

    public WorkoutBuilder setMetabolicEnergyTotal(Double joules) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.metabolicEnergyTotal = joules;
        return this;
    }

    public WorkoutBuilder setWillPower(Double willPower) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.willPower = willPower;
        return this;
    }

    public WorkoutBuilder setStepsTotal(Integer stepsTotal) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.stepsTotal = stepsTotal;
        return this;
    }

    public WorkoutBuilder setHeartRateAggregates(Integer maxBpm, Integer minBpm, Integer avgBpm) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.heartRateMax = maxBpm;
        this.workoutAggregates.heartRateMin = minBpm;
        this.workoutAggregates.heartRateAvg = avgBpm;
        this.updatedHeartRate = true;
        return this;
    }

    public WorkoutBuilder setSpeedAggregates(Double maxSpeed, Double minSpeed, Double avgSpeed) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.speedMax = maxSpeed;
        this.workoutAggregates.speedMin = minSpeed;
        this.workoutAggregates.speedAvg = avgSpeed;
        this.updatedSpeed = true;
        return this;
    }

    public WorkoutBuilder setCadenceAggregates(Integer maxCadence, Integer minCadence, Integer avgCadence) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.cadenceMax = maxCadence;
        this.workoutAggregates.cadenceMin = minCadence;
        this.workoutAggregates.cadenceAvg = avgCadence;
        this.updatedCadence = true;
        return this;
    }

    public WorkoutBuilder setPowerAggregates(Double maxPower, Double minPower, Double avgPower) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.powerMax = maxPower;
        this.workoutAggregates.powerMin = minPower;
        this.workoutAggregates.powerAvg = avgPower;
        this.updatedPower = true;
        return this;
    }

    public WorkoutBuilder setTorqueAggregates(Double maxTorque, Double minTorque, Double avgTorque) {
        if (this.workoutAggregates == null) {
            this.workoutAggregates = new WorkoutAggregatesImpl();
        }
        this.workoutAggregates.torqueMax = maxTorque;
        this.workoutAggregates.torqueMin = minTorque;
        this.workoutAggregates.torqueAvg = avgTorque;
        this.updatedTorque = true;
        return this;
    }

    public WorkoutBuilder addHeartRateEvent(double offset, int bpm) {
        if (this.workoutHeartRateEntryTimeSeries == null) {
            this.workoutHeartRateEntryTimeSeries = new TimeSeriesImpl<>();
        }
        this.buildNewTimeSeries = true;
        if (this.isUpdate && !this.updatedHeartRate && this.workoutAggregates != null) {
            this.updatedHeartRate = true;
            this.workoutAggregates.heartRateAvg = null;
            this.workoutAggregates.heartRateMax = null;
            this.workoutAggregates.heartRateMin = null;
        }
        if (offset > this.maxOffset) {
            this.maxOffset = offset;
        }
        this.workoutHeartRateEntryTimeSeries.add(new WorkoutHeartRateEntryImpl(Double.valueOf(offset), Integer.valueOf(bpm)));
        return this;
    }

    public WorkoutBuilder addSpeedEvent(double offset, double speed) {
        if (this.workoutSpeedEntryTimeSeries == null) {
            this.workoutSpeedEntryTimeSeries = new TimeSeriesImpl<>();
        }
        this.buildNewTimeSeries = true;
        if (this.isUpdate && !this.updatedSpeed && this.workoutAggregates != null) {
            this.updatedSpeed = true;
            this.workoutAggregates.speedAvg = null;
            this.workoutAggregates.speedMax = null;
            this.workoutAggregates.speedMin = null;
        }
        if (offset > this.maxOffset) {
            this.maxOffset = offset;
        }
        this.workoutSpeedEntryTimeSeries.add(new WorkoutSpeedEntryImpl(Double.valueOf(offset), Double.valueOf(speed)));
        return this;
    }

    public WorkoutBuilder addCadenceEvent(double offset, int cadence) {
        if (this.workoutCadenceEntryTimeSeries == null) {
            this.workoutCadenceEntryTimeSeries = new TimeSeriesImpl<>();
        }
        this.buildNewTimeSeries = true;
        if (this.isUpdate && !this.updatedCadence && this.workoutAggregates != null) {
            this.updatedCadence = true;
            this.workoutAggregates.cadenceAvg = null;
            this.workoutAggregates.cadenceMax = null;
            this.workoutAggregates.cadenceMin = null;
        }
        if (offset > this.maxOffset) {
            this.maxOffset = offset;
        }
        this.workoutCadenceEntryTimeSeries.add(new WorkoutCadenceEntryImpl(Double.valueOf(offset), Integer.valueOf(cadence)));
        return this;
    }

    public WorkoutBuilder addPowerEvent(double offset, double power) {
        if (this.workoutPowerEntryTimeSeries == null) {
            this.workoutPowerEntryTimeSeries = new TimeSeriesImpl<>();
        }
        this.buildNewTimeSeries = true;
        if (this.isUpdate && !this.updatedPower && this.workoutAggregates != null) {
            this.updatedPower = true;
            this.workoutAggregates.powerAvg = null;
            this.workoutAggregates.powerMax = null;
            this.workoutAggregates.powerMin = null;
        }
        if (offset > this.maxOffset) {
            this.maxOffset = offset;
        }
        this.workoutPowerEntryTimeSeries.add(new WorkoutPowerEntryImpl(Double.valueOf(offset), Double.valueOf(power)));
        return this;
    }

    public WorkoutBuilder addTorqueEvent(double offset, double torque) {
        if (this.workoutTorqueEntryTimeSeries == null) {
            this.workoutTorqueEntryTimeSeries = new TimeSeriesImpl<>();
        }
        if (this.isUpdate && !this.updatedTorque && this.workoutAggregates != null) {
            this.updatedTorque = true;
            this.workoutAggregates.torqueAvg = null;
            this.workoutAggregates.torqueMax = null;
            this.workoutAggregates.torqueMin = null;
        }
        this.buildNewTimeSeries = true;
        if (offset > this.maxOffset) {
            this.maxOffset = offset;
        }
        this.workoutTorqueEntryTimeSeries.add(new WorkoutTorqueEntryImpl(Double.valueOf(offset), Double.valueOf(torque)));
        return this;
    }

    public WorkoutBuilder addDistanceEvent(double offset, double cumulativeDistance) {
        if (this.workoutDistanceTimeSeries == null) {
            this.workoutDistanceTimeSeries = new TimeSeriesImpl<>();
        }
        this.buildNewTimeSeries = true;
        if (offset > this.maxOffset) {
            this.maxOffset = offset;
        }
        this.workoutDistanceTimeSeries.add(new WorkoutDistanceEntryImpl(Double.valueOf(offset), Double.valueOf(cumulativeDistance)));
        return this;
    }

    public WorkoutBuilder addStepsEvent(double offset, int steps) {
        if (this.workoutStepsEntryTimeSeries == null) {
            this.workoutStepsEntryTimeSeries = new TimeSeriesImpl<>();
        }
        this.buildNewTimeSeries = true;
        if (offset > this.maxOffset) {
            this.maxOffset = offset;
        }
        this.workoutStepsEntryTimeSeries.add(new WorkoutStepsEntryImpl(Double.valueOf(offset), Integer.valueOf(steps)));
        return this;
    }

    public WorkoutBuilder addPositionEvent(double offset, Double elevation, Double latitude, Double longitude) {
        if (this.workoutPositionEntryTimeSeries == null) {
            this.workoutPositionEntryTimeSeries = new TimeSeriesImpl<>();
        }
        this.buildNewTimeSeries = true;
        if (offset > this.maxOffset) {
            this.maxOffset = offset;
        }
        this.workoutPositionEntryTimeSeries.add(new WorkoutPositionEntryImpl(Double.valueOf(offset), elevation, latitude, longitude));
        return this;
    }

    public WorkoutBuilder addTimerStopEvent(double offset, double stopTimeInSeconds) {
        if (this.workoutStopTimeEntryTimeSeries == null) {
            this.workoutStopTimeEntryTimeSeries = new TimeSeriesImpl<>();
        }
        this.buildNewTimeSeries = true;
        if (offset > this.maxOffset) {
            this.maxOffset = offset;
        }
        this.workoutStopTimeEntryTimeSeries.add(new WorkoutTimerStopEntryImpl(Double.valueOf(offset), Double.valueOf(stopTimeInSeconds)));
        return this;
    }

    private void createHeartRateAggregates() {
        int min = 0;
        int max = 0;
        int sum = 0;
        Iterator i$ = this.workoutHeartRateEntryTimeSeries.iterator();
        while (i$.hasNext()) {
            int bpm = i$.next().getBpm();
            if (bpm < min || min == 0) {
                min = bpm;
            }
            if (bpm > max) {
                max = bpm;
            }
            sum += bpm;
        }
        double avg = (double) (sum / this.workoutHeartRateEntryTimeSeries.getSize());
        this.workoutAggregates.heartRateMax = Integer.valueOf(max);
        this.workoutAggregates.heartRateMin = Integer.valueOf(min);
        this.workoutAggregates.heartRateAvg = Integer.valueOf((int) avg);
    }

    private void createSpeedAggregates() {
        double min = 0.0d;
        double max = 0.0d;
        double sum = 0.0d;
        Iterator i$ = this.workoutSpeedEntryTimeSeries.iterator();
        while (i$.hasNext()) {
            double speed = i$.next().getInstantaneousSpeed();
            if (speed < min || min == 0.0d) {
                min = speed;
            }
            if (speed > max) {
                max = speed;
            }
            sum += speed;
        }
        this.workoutAggregates.speedMax = Double.valueOf(max);
        this.workoutAggregates.speedMin = Double.valueOf(min);
        if (this.workoutAggregates.activeTimeTotal == null || this.workoutAggregates.distanceTotal == null) {
            double avg = sum / ((double) this.workoutSpeedEntryTimeSeries.getSize());
            this.workoutAggregates.speedAvg = Double.valueOf(avg);
            return;
        }
        this.workoutAggregates.speedAvg = Double.valueOf(this.workoutAggregates.distanceTotal.doubleValue() / this.workoutAggregates.activeTimeTotal.doubleValue());
    }

    private void createPowerAggregates() {
        double min = 0.0d;
        double max = 0.0d;
        double sum = 0.0d;
        Iterator i$ = this.workoutPowerEntryTimeSeries.iterator();
        while (i$.hasNext()) {
            double power = i$.next().getInstantaneousPower();
            if (power < min || min == 0.0d) {
                min = power;
            }
            if (power > max) {
                max = power;
            }
            sum += power;
        }
        double avg = sum / ((double) this.workoutPowerEntryTimeSeries.getSize());
        this.workoutAggregates.powerMax = Double.valueOf(max);
        this.workoutAggregates.powerMin = Double.valueOf(min);
        this.workoutAggregates.powerAvg = Double.valueOf(avg);
    }

    private void createCadenceAggregates() {
        int min = 0;
        int max = 0;
        int sum = 0;
        Iterator i$ = this.workoutCadenceEntryTimeSeries.iterator();
        while (i$.hasNext()) {
            int cadence = i$.next().getInstantaneousCadence();
            if (cadence < min || min == 0) {
                min = cadence;
            }
            if (cadence > max) {
                max = cadence;
            }
            sum += cadence;
        }
        int avg = sum / this.workoutCadenceEntryTimeSeries.getSize();
        this.workoutAggregates.cadenceMax = Integer.valueOf(max);
        this.workoutAggregates.cadenceMin = Integer.valueOf(min);
        this.workoutAggregates.cadenceAvg = Integer.valueOf(avg);
    }

    private void createTorqueAggregates() {
        double min = 0.0d;
        double max = 0.0d;
        double sum = 0.0d;
        Iterator i$ = this.workoutTorqueEntryTimeSeries.iterator();
        while (i$.hasNext()) {
            double torque = i$.next().getInstantaneousTorque();
            if (torque < min || min == 0.0d) {
                min = torque;
            }
            if (torque > max) {
                max = torque;
            }
            sum += torque;
        }
        double avg = sum / ((double) this.workoutTorqueEntryTimeSeries.getSize());
        this.workoutAggregates.torqueMax = Double.valueOf(max);
        this.workoutAggregates.torqueMin = Double.valueOf(min);
        this.workoutAggregates.torqueAvg = Double.valueOf(avg);
    }

    private void createDistanceAggregate() {
        this.workoutAggregates.distanceTotal = Double.valueOf(this.timeSeries.workoutDistanceTimeSeries.get(this.timeSeries.workoutDistanceTimeSeries.getSize() - 1).getDistance());
    }

    private void createTimeAggregates() {
        if (this.maxOffset > 0.0d) {
            this.workoutAggregates.elapsedTimeTotal = Double.valueOf(this.maxOffset);
            if (this.workoutStopTimeEntryTimeSeries != null) {
                double pausedTime = 0.0d;
                Iterator i$ = this.workoutStopTimeEntryTimeSeries.iterator();
                while (i$.hasNext()) {
                    pausedTime += i$.next().getStoppedTime();
                }
                this.workoutAggregates.activeTimeTotal = Double.valueOf(this.maxOffset - pausedTime);
                return;
            }
            this.workoutAggregates.activeTimeTotal = Double.valueOf(this.maxOffset);
        }
    }

    public Workout build() {
        if (this.name == null) {
            throw new IllegalArgumentException("name must be set.");
        } else if (this.startTime == null) {
            throw new IllegalArgumentException("startTime must be set.");
        } else if (this.createdTime == null) {
            throw new IllegalArgumentException("createdTime must be set.");
        } else if (this.timeZone == null) {
            throw new IllegalArgumentException("timeZone must be set.");
        } else if (this.privacy == null) {
            throw new IllegalArgumentException("privacy must be set.");
        } else if (this.activityTypeEntityRef == null) {
            throw new IllegalArgumentException("activity ref must be set.");
        } else {
            if (this.buildNewTimeSeries) {
                this.timeSeries = new WorkoutTimeSeriesImpl();
                if (this.workoutAggregates == null) {
                    this.workoutAggregates = new WorkoutAggregatesImpl();
                }
                if (this.workoutHeartRateEntryTimeSeries != null) {
                    this.workoutHeartRateEntryTimeSeries.sort();
                    this.timeSeries.workoutHeartRateEntryTimeSeries = this.workoutHeartRateEntryTimeSeries;
                    if (this.workoutAggregates.heartRateAvg == null) {
                        createHeartRateAggregates();
                    }
                }
                if (this.workoutCadenceEntryTimeSeries != null) {
                    this.workoutCadenceEntryTimeSeries.sort();
                    this.timeSeries.workoutCadenceEntryTimeSeries = this.workoutCadenceEntryTimeSeries;
                    if (this.workoutAggregates.cadenceAvg == null) {
                        createCadenceAggregates();
                    }
                }
                if (this.workoutPowerEntryTimeSeries != null) {
                    this.workoutPowerEntryTimeSeries.sort();
                    this.timeSeries.workoutPowerEntryTimeSeries = this.workoutPowerEntryTimeSeries;
                    if (this.workoutAggregates.powerAvg == null) {
                        createPowerAggregates();
                    }
                }
                if (this.workoutTorqueEntryTimeSeries != null) {
                    this.workoutTorqueEntryTimeSeries.sort();
                    this.timeSeries.workoutTorqueEntryTimeSeries = this.workoutTorqueEntryTimeSeries;
                    if (this.workoutAggregates.torqueAvg == null) {
                        createTorqueAggregates();
                    }
                }
                if (this.workoutDistanceTimeSeries != null) {
                    this.workoutDistanceTimeSeries.sort();
                    this.timeSeries.workoutDistanceTimeSeries = this.workoutDistanceTimeSeries;
                    createDistanceAggregate();
                }
                if (this.workoutStepsEntryTimeSeries != null) {
                    this.workoutStepsEntryTimeSeries.sort();
                    this.timeSeries.workoutStepsEntryTimeSeries = this.workoutStepsEntryTimeSeries;
                }
                if (this.workoutPositionEntryTimeSeries != null) {
                    this.workoutPositionEntryTimeSeries.sort();
                    this.timeSeries.workoutPositionEntryTimeSeries = this.workoutPositionEntryTimeSeries;
                }
                if (this.workoutStopTimeEntryTimeSeries != null) {
                    this.workoutStopTimeEntryTimeSeries.sort();
                    this.timeSeries.workoutStopTimeEntryTimeSeries = this.workoutStopTimeEntryTimeSeries;
                }
                createTimeAggregates();
                if (this.workoutSpeedEntryTimeSeries != null) {
                    this.workoutSpeedEntryTimeSeries.sort();
                    this.timeSeries.workoutSpeedEntryTimeSeries = this.workoutSpeedEntryTimeSeries;
                    if (this.workoutAggregates.speedAvg == null) {
                        createSpeedAggregates();
                    }
                }
                this.hasTimeSeries = true;
            }
            if (this.timeSeries != null || (this.workoutAggregates != null && this.workoutAggregates.activeTimeTotal != null)) {
                return new WorkoutImpl(this);
            }
            throw new IllegalArgumentException("a time series or total time must be provided.");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        long j;
        int ordinal;
        byte b;
        byte b2;
        byte b3;
        byte b4;
        byte b5;
        long j2 = -1;
        byte b6 = 1;
        dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
        if (this.updateTime != null) {
            j = this.updateTime.getTime();
        } else {
            j = -1;
        }
        dest.writeLong(j);
        if (this.createdTime != null) {
            j2 = this.createdTime.getTime();
        }
        dest.writeLong(j2);
        dest.writeString(this.name);
        dest.writeString(this.notes);
        dest.writeSerializable(this.timeZone);
        dest.writeString(this.source);
        dest.writeString(this.referenceKey);
        dest.writeParcelable(this.workoutAggregates, flags);
        dest.writeParcelable(this.timeSeries, flags);
        dest.writeParcelable(this.activityTypeEntityRef, flags);
        dest.writeParcelable(this.userGearEntityRef, flags);
        if (this.privacy == null) {
            ordinal = -1;
        } else {
            ordinal = this.privacy.ordinal();
        }
        dest.writeInt(ordinal);
        dest.writeParcelable(this.workoutHeartRateEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutSpeedEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutCadenceEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutPowerEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutTorqueEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutDistanceTimeSeries, flags);
        dest.writeParcelable(this.workoutStepsEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutPositionEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutStopTimeEntryTimeSeries, flags);
        dest.writeParcelable(this.workoutRef, flags);
        dest.writeParcelable(this.routeRef, flags);
        dest.writeParcelable(this.userRef, flags);
        dest.writeDouble(this.maxOffset);
        dest.writeByte(this.buildNewTimeSeries ? (byte) 1 : 0);
        if (this.isUpdate) {
            b = 1;
        } else {
            b = 0;
        }
        dest.writeByte(b);
        if (this.updatedHeartRate) {
            b2 = 1;
        } else {
            b2 = 0;
        }
        dest.writeByte(b2);
        if (this.updatedSpeed) {
            b3 = 1;
        } else {
            b3 = 0;
        }
        dest.writeByte(b3);
        if (this.updatedCadence) {
            b4 = 1;
        } else {
            b4 = 0;
        }
        dest.writeByte(b4);
        if (this.updatedPower) {
            b5 = 1;
        } else {
            b5 = 0;
        }
        dest.writeByte(b5);
        if (!this.updatedTorque) {
            b6 = 0;
        }
        dest.writeByte(b6);
        dest.writeParcelable(this.socialSettings, flags);
        dest.writeParcelable(this.attachments, flags);
    }

    private WorkoutBuilderImpl(Parcel in) {
        Date date;
        Privacy.Level level;
        this.maxOffset = 0.0d;
        this.buildNewTimeSeries = false;
        this.isUpdate = false;
        this.updatedHeartRate = false;
        this.updatedSpeed = false;
        this.updatedCadence = false;
        this.updatedPower = false;
        this.updatedTorque = false;
        long tmpStartTime = in.readLong();
        this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        long tmpUpdateTime = in.readLong();
        this.updateTime = tmpUpdateTime == -1 ? null : new Date(tmpUpdateTime);
        long tmpCreatedTime = in.readLong();
        if (tmpCreatedTime == -1) {
            date = null;
        } else {
            date = new Date(tmpCreatedTime);
        }
        this.createdTime = date;
        this.name = in.readString();
        this.notes = in.readString();
        this.timeZone = (TimeZone) in.readSerializable();
        this.source = in.readString();
        this.referenceKey = in.readString();
        this.workoutAggregates = (WorkoutAggregatesImpl) in.readParcelable(WorkoutAggregatesImpl.class.getClassLoader());
        this.timeSeries = (WorkoutTimeSeriesImpl) in.readParcelable(WorkoutTimeSeriesImpl.class.getClassLoader());
        this.activityTypeEntityRef = (ActivityTypeRef) in.readParcelable(ActivityTypeRef.class.getClassLoader());
        this.userGearEntityRef = (UserGearRef) in.readParcelable(UserGearRef.class.getClassLoader());
        int tmpPrivacy = in.readInt();
        if (tmpPrivacy == -1) {
            level = null;
        } else {
            level = Privacy.Level.values()[tmpPrivacy];
        }
        this.privacy = level;
        this.workoutHeartRateEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutSpeedEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutCadenceEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutPowerEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutTorqueEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutDistanceTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutStepsEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutPositionEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutStopTimeEntryTimeSeries = (TimeSeriesImpl) in.readParcelable(TimeSeriesImpl.class.getClassLoader());
        this.workoutRef = (WorkoutRef) in.readParcelable(WorkoutRef.class.getClassLoader());
        this.routeRef = (RouteRef) in.readParcelable(RouteRef.class.getClassLoader());
        this.userRef = (EntityRef) in.readParcelable(EntityRef.class.getClassLoader());
        this.maxOffset = in.readDouble();
        this.buildNewTimeSeries = in.readByte() != 0;
        this.isUpdate = in.readByte() != 0;
        this.updatedHeartRate = in.readByte() != 0;
        this.updatedSpeed = in.readByte() != 0;
        this.updatedCadence = in.readByte() != 0;
        this.updatedPower = in.readByte() != 0;
        this.updatedTorque = in.readByte() != 0;
        this.socialSettings = (SocialSettings) in.readParcelable(SocialSettings.class.getClassLoader());
        this.attachments = (Attachments) in.readParcelable(Attachments.class.getClassLoader());
    }
}
