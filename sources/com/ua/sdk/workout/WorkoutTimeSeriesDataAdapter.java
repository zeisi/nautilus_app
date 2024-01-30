package com.ua.sdk.workout;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ua.sdk.datapoint.BaseDataTypes;
import java.io.IOException;
import java.util.Iterator;

public class WorkoutTimeSeriesDataAdapter extends TypeAdapter<WorkoutTimeSeriesImpl> {
    public void write(JsonWriter out, WorkoutTimeSeriesImpl value) throws IOException {
        if (value != null) {
            out.beginObject();
            if (value.workoutHeartRateEntryTimeSeries != null) {
                out.name("heartrate");
                out.beginArray();
                Iterator i$ = value.workoutHeartRateEntryTimeSeries.iterator();
                while (i$.hasNext()) {
                    WorkoutHeartRateEntry workoutHeartRateEntry = i$.next();
                    out.beginArray();
                    out.value(workoutHeartRateEntry.getOffset());
                    out.value((long) workoutHeartRateEntry.getBpm());
                    out.endArray();
                }
                out.endArray();
            }
            if (value.workoutSpeedEntryTimeSeries != null) {
                out.name(BaseDataTypes.ID_SPEED);
                out.beginArray();
                Iterator i$2 = value.workoutSpeedEntryTimeSeries.iterator();
                while (i$2.hasNext()) {
                    WorkoutSpeedEntry workoutSpeedEntry = i$2.next();
                    out.beginArray();
                    out.value(workoutSpeedEntry.getOffset());
                    out.value(workoutSpeedEntry.getInstantaneousSpeed());
                    out.endArray();
                }
                out.endArray();
            }
            if (value.workoutCadenceEntryTimeSeries != null) {
                out.name("cadence");
                out.beginArray();
                Iterator i$3 = value.workoutCadenceEntryTimeSeries.iterator();
                while (i$3.hasNext()) {
                    WorkoutCadenceEntry workoutCadenceEntry = i$3.next();
                    out.beginArray();
                    out.value(workoutCadenceEntry.getOffset());
                    out.value((long) workoutCadenceEntry.getInstantaneousCadence());
                    out.endArray();
                }
                out.endArray();
            }
            if (value.workoutPowerEntryTimeSeries != null) {
                out.name("power");
                out.beginArray();
                Iterator i$4 = value.workoutPowerEntryTimeSeries.iterator();
                while (i$4.hasNext()) {
                    WorkoutPowerEntry powerEntry = i$4.next();
                    out.beginArray();
                    out.value(powerEntry.getOffset());
                    out.value(powerEntry.getInstantaneousPower());
                    out.endArray();
                }
                out.endArray();
            }
            if (value.workoutTorqueEntryTimeSeries != null) {
                out.name("torque");
                out.beginArray();
                Iterator i$5 = value.workoutTorqueEntryTimeSeries.iterator();
                while (i$5.hasNext()) {
                    WorkoutTorqueEntry torqueEntry = i$5.next();
                    out.beginArray();
                    out.value(torqueEntry.getOffset());
                    out.value(torqueEntry.getInstantaneousTorque());
                    out.endArray();
                }
                out.endArray();
            }
            if (value.workoutDistanceTimeSeries != null) {
                out.name("distance");
                out.beginArray();
                Iterator i$6 = value.workoutDistanceTimeSeries.iterator();
                while (i$6.hasNext()) {
                    WorkoutDistanceEntry distanceEntry = i$6.next();
                    out.beginArray();
                    out.value(distanceEntry.getOffset());
                    out.value(distanceEntry.getDistance());
                    out.endArray();
                }
                out.endArray();
            }
            if (value.workoutStepsEntryTimeSeries != null) {
                out.name(BaseDataTypes.ID_STEPS);
                out.beginArray();
                Iterator i$7 = value.workoutStepsEntryTimeSeries.iterator();
                while (i$7.hasNext()) {
                    WorkoutStepsEntry stepsEntry = i$7.next();
                    out.beginArray();
                    out.value(stepsEntry.getOffset());
                    out.value((long) stepsEntry.getInstantaneousSteps());
                    out.endArray();
                }
                out.endArray();
            }
            if (value.workoutPositionEntryTimeSeries != null) {
                out.name("position");
                out.beginArray();
                Iterator i$8 = value.workoutPositionEntryTimeSeries.iterator();
                while (i$8.hasNext()) {
                    WorkoutPositionEntry positionEntry = i$8.next();
                    out.beginArray();
                    out.value(positionEntry.getOffset());
                    out.beginObject();
                    out.name(BaseDataTypes.ID_ELEVATION);
                    out.value((Number) positionEntry.getElevation());
                    out.name("lat");
                    out.value((Number) positionEntry.getLatitude());
                    out.name("lng");
                    out.value((Number) positionEntry.getLongitude());
                    out.endObject();
                    out.endArray();
                }
                out.endArray();
            }
            if (value.workoutStopTimeEntryTimeSeries != null) {
                out.name("timer_stop");
                out.beginArray();
                Iterator i$9 = value.workoutStopTimeEntryTimeSeries.iterator();
                while (i$9.hasNext()) {
                    WorkoutTimerStopEntry timerStopEntry = i$9.next();
                    out.beginArray();
                    out.value(timerStopEntry.getOffset());
                    out.value(timerStopEntry.getStoppedTime());
                    out.endArray();
                }
                out.endArray();
            }
            out.endObject();
            return;
        }
        out.nullValue();
    }

    public WorkoutTimeSeriesImpl read(JsonReader in) throws IOException {
        WorkoutTimeSeriesImpl timeSeriesData = new WorkoutTimeSeriesImpl();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("heartrate")) {
                TimeSeriesImpl<WorkoutHeartRateEntry> heartRateEntryTimeSeries = new TimeSeriesImpl<>();
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    heartRateEntryTimeSeries.add(new WorkoutHeartRateEntryImpl(Double.valueOf(in.nextDouble()), Integer.valueOf(in.nextInt())));
                    in.endArray();
                }
                in.endArray();
                timeSeriesData.workoutHeartRateEntryTimeSeries = heartRateEntryTimeSeries;
            } else if (name.equals(BaseDataTypes.ID_SPEED)) {
                TimeSeriesImpl<WorkoutSpeedEntry> speedEntryTimeSeries = new TimeSeriesImpl<>();
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    speedEntryTimeSeries.add(new WorkoutSpeedEntryImpl(Double.valueOf(in.nextDouble()), Double.valueOf(in.nextDouble())));
                    in.endArray();
                }
                in.endArray();
                timeSeriesData.workoutSpeedEntryTimeSeries = speedEntryTimeSeries;
            } else if (name.equals("cadence")) {
                TimeSeriesImpl<WorkoutCadenceEntry> cadenceEntryTimeSeries = new TimeSeriesImpl<>();
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    cadenceEntryTimeSeries.add(new WorkoutCadenceEntryImpl(Double.valueOf(in.nextDouble()), Integer.valueOf(in.nextInt())));
                    in.endArray();
                }
                in.endArray();
                timeSeriesData.workoutCadenceEntryTimeSeries = cadenceEntryTimeSeries;
            } else if (name.equals("power")) {
                TimeSeriesImpl<WorkoutPowerEntry> powerEntryTimeSeries = new TimeSeriesImpl<>();
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    powerEntryTimeSeries.add(new WorkoutPowerEntryImpl(Double.valueOf(in.nextDouble()), Double.valueOf(in.nextDouble())));
                    in.endArray();
                }
                in.endArray();
                timeSeriesData.workoutPowerEntryTimeSeries = powerEntryTimeSeries;
            } else if (name.equals("torque")) {
                TimeSeriesImpl<WorkoutTorqueEntry> torqueEntryTimeSeries = new TimeSeriesImpl<>();
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    torqueEntryTimeSeries.add(new WorkoutTorqueEntryImpl(Double.valueOf(in.nextDouble()), Double.valueOf(in.nextDouble())));
                    in.endArray();
                }
                in.endArray();
                timeSeriesData.workoutTorqueEntryTimeSeries = torqueEntryTimeSeries;
            } else if (name.equals("distance")) {
                TimeSeriesImpl<WorkoutDistanceEntry> distanceEntryTimeSeries = new TimeSeriesImpl<>();
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    distanceEntryTimeSeries.add(new WorkoutDistanceEntryImpl(Double.valueOf(in.nextDouble()), Double.valueOf(in.nextDouble())));
                    in.endArray();
                }
                in.endArray();
                timeSeriesData.workoutDistanceTimeSeries = distanceEntryTimeSeries;
            } else if (name.equals(BaseDataTypes.ID_STEPS)) {
                TimeSeriesImpl<WorkoutStepsEntry> stepsEntryTimeSeries = new TimeSeriesImpl<>();
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    stepsEntryTimeSeries.add(new WorkoutStepsEntryImpl(Double.valueOf(in.nextDouble()), Integer.valueOf(in.nextInt())));
                    in.endArray();
                }
                in.endArray();
                timeSeriesData.workoutStepsEntryTimeSeries = stepsEntryTimeSeries;
            } else if (name.equals("position")) {
                TimeSeriesImpl<WorkoutPositionEntry> positionEntryTimeSeries = new TimeSeriesImpl<>();
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    double offset = in.nextDouble();
                    Double elevation = null;
                    Double latitude = null;
                    Double longitude = null;
                    in.beginObject();
                    while (in.hasNext()) {
                        String name2 = in.nextName();
                        if (name2.equals(BaseDataTypes.ID_ELEVATION)) {
                            elevation = Double.valueOf(in.nextDouble());
                        } else if (name2.equals("lat")) {
                            latitude = Double.valueOf(in.nextDouble());
                        } else if (name2.equals("lng")) {
                            longitude = Double.valueOf(in.nextDouble());
                        } else {
                            in.skipValue();
                        }
                    }
                    in.endObject();
                    positionEntryTimeSeries.add(new WorkoutPositionEntryImpl(Double.valueOf(offset), elevation, latitude, longitude));
                    in.endArray();
                }
                in.endArray();
                timeSeriesData.workoutPositionEntryTimeSeries = positionEntryTimeSeries;
            } else if (name.equals("timer_stop")) {
                TimeSeriesImpl<WorkoutTimerStopEntry> timerStopEntryTimeSeries = new TimeSeriesImpl<>();
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray();
                    timerStopEntryTimeSeries.add(new WorkoutTimerStopEntryImpl(Double.valueOf(in.nextDouble()), Double.valueOf(in.nextDouble())));
                    in.endArray();
                }
                in.endArray();
                timeSeriesData.workoutStopTimeEntryTimeSeries = timerStopEntryTimeSeries;
            } else {
                in.skipValue();
            }
        }
        in.endObject();
        return timeSeriesData;
    }
}
