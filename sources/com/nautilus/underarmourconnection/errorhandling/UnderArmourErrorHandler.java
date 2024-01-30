package com.nautilus.underarmourconnection.errorhandling;

import android.content.Context;
import com.nautilus.underarmourconnection.R;
import com.ua.sdk.UaException;
import org.json.JSONException;

public class UnderArmourErrorHandler {
    public static final String ERROR_SAVING_WORKOUT = "ERROR_SAVING_WORKOUT";
    public static final String PARAMETER_MISSING = "PARAMETER_MISSING";
    public static final String WORKOUT_ALREADY_EXISTS = "WORKOUT_ALREADY_EXISTS";
    public static final String WRONG_PARAMETER_TYPE = "WRONG_PARAMETER_TYPE";
    public static final String WRONG_UNIT_TYPE = "WRONG_UNIT_TYPE";

    public static UnderArmourError getUnderArmourError(Context context, UaException uaException) {
        if (uaException.getCode().equals(UaException.Code.TIMEOUT)) {
            return new UnderArmourError(context.getString(R.string.error_timeout_message), context.getString(R.string.error_timeout_recommendation));
        }
        if (uaException.getCode().equals(UaException.Code.NETWORK)) {
            return new UnderArmourError(context.getString(R.string.error_network_message), context.getString(R.string.error_network_recommendation));
        }
        if (uaException.getCode().equals(UaException.Code.PERMISSION)) {
            return new UnderArmourError(context.getString(R.string.error_permission_message), context.getString(R.string.error_permission_recommendation));
        }
        if (uaException.getCode().equals(UaException.Code.CANCELED)) {
            return new UnderArmourError(context.getString(R.string.error_canceled_message), context.getString(R.string.error_canceled_recommendation));
        }
        if (uaException.getCode().equals(UaException.Code.NOT_FOUND)) {
            return new UnderArmourError(context.getString(R.string.error_not_found_message), context.getString(R.string.error_not_found_recommendation));
        }
        if (uaException.getCode().equals(UaException.Code.BAD_FORMAT)) {
            return new UnderArmourError(context.getString(R.string.error_bad_format_message), context.getString(R.string.error_bad_format_recommendation));
        }
        if (uaException.getCode().equals(UaException.Code.NOT_AUTHENTICATED)) {
            return new UnderArmourError(context.getString(R.string.error_not_authenticated_message), context.getString(R.string.error_not_authenticated_recommendation));
        }
        if (uaException.getCode().equals(UaException.Code.NOT_CONNECTED)) {
            return new UnderArmourError(context.getString(R.string.error_not_connected_message), context.getString(R.string.error_not_connected_recommendation));
        }
        if (uaException.getCode().equals(UaException.Code.NETWORK_ON_MAIN_THREAD)) {
            return new UnderArmourError(context.getString(R.string.error_network_main_thread_message), context.getString(R.string.error_network_main_thread_recommendation));
        }
        return new UnderArmourError(context.getString(R.string.error_unknown_message), context.getString(R.string.error_unknown_recommendation));
    }

    public static UnderArmourError getParametersError(Context context, JSONException jsonException) {
        if (jsonException.getMessage().equals(WRONG_PARAMETER_TYPE)) {
            return new UnderArmourError(context.getString(R.string.error_wrong_parameter_type_message), context.getString(R.string.error_wrong_parameter_type_recommendation));
        }
        if (jsonException.getMessage().equals(WRONG_UNIT_TYPE)) {
            return new UnderArmourError(context.getString(R.string.error_wrong_unit_type_message), context.getString(R.string.error_wrong_unit_type_recommendation));
        }
        return new UnderArmourError(context.getString(R.string.error_parameters_missing_message), context.getString(R.string.error_parameters_missing_recommendation));
    }

    public static UnderArmourError getDatabaseError(Context context, String errorType) {
        if (errorType.equals(ERROR_SAVING_WORKOUT)) {
            return new UnderArmourError(context.getString(R.string.error_database_message), context.getString(R.string.error_save_workout_recommendation));
        }
        return new UnderArmourError(context.getString(R.string.error_database_message), context.getString(R.string.error_workout_already_exists_recommendation));
    }
}
