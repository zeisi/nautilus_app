package com.nautilus.underarmourconnection.errorhandling;

import org.json.JSONException;
import org.json.JSONObject;

public class ExceptionUtil {
    public static Object validateGenericObject(Object objectValue, Class<?> type) throws JSONException {
        if (type.isInstance(objectValue)) {
            return objectValue;
        }
        throw new JSONException(UnderArmourErrorHandler.WRONG_PARAMETER_TYPE);
    }

    public static boolean validateIfParametersMissing(boolean isRequiredData, JSONObject jsonObject, String key) throws JSONException {
        if ((isRequiredData && jsonObject.has(key)) || !isRequiredData) {
            return false;
        }
        throw new JSONException(UnderArmourErrorHandler.PARAMETER_MISSING);
    }

    public static boolean validateIfParametersMissing(JSONObject jsonObject, String keyValue, String keyUnit) throws JSONException {
        if ((jsonObject.has(keyValue) && jsonObject.has(keyUnit)) || (!jsonObject.has(keyValue) && !jsonObject.has(keyUnit))) {
            return false;
        }
        throw new JSONException(UnderArmourErrorHandler.PARAMETER_MISSING);
    }

    public static Object getException(JSONException exception, boolean isInteger) throws JSONException {
        if (exception.getMessage().equals(UnderArmourErrorHandler.WRONG_PARAMETER_TYPE)) {
            throw exception;
        } else if (exception.getMessage().equals(UnderArmourErrorHandler.WRONG_UNIT_TYPE)) {
            throw exception;
        } else if (exception.getMessage().equals(UnderArmourErrorHandler.PARAMETER_MISSING)) {
            throw exception;
        } else if (isInteger) {
            return 0;
        } else {
            return Double.valueOf(0.0d);
        }
    }
}
