package com.verticalbargraphiclibrary;

import com.nautilus.omni.util.Constants;
import java.text.DecimalFormat;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class Util {
    public static String convertSecondsToDuration(long seconds, boolean showSeconds) {
        Period period = new Duration(1000 * seconds).toPeriod(PeriodType.yearDayTime());
        StringBuilder stringBuilder = new StringBuilder();
        if (String.valueOf(period.getHours()).length() == 1) {
            stringBuilder.append("0");
        }
        stringBuilder.append(period.getHours()).append(Constants.COLON_SEPARATOR);
        if (String.valueOf(period.getMinutes()).length() == 1) {
            stringBuilder.append("0");
        }
        if (showSeconds) {
            stringBuilder.append(period.getMinutes()).append(Constants.COLON_SEPARATOR);
            if (String.valueOf(period.getSeconds()).length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(period.getSeconds());
        } else {
            stringBuilder.append(period.getMinutes());
        }
        return stringBuilder.toString();
    }

    public static String convertDoubleToTwoDecimals(double value) {
        return new DecimalFormat("0.00").format(value);
    }

    public static String convertDoubleToOneDecimal(double value) {
        return new DecimalFormat("0.0").format(value);
    }
}
