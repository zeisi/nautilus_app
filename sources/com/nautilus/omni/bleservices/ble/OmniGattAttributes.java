package com.nautilus.omni.bleservices.ble;

import java.util.HashMap;

public class OmniGattAttributes {
    public static final String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static final String DEVICE_INFORMATION = "0000180a-0000-1000-8000-00805f9b34fb";
    public static final String ELLIPTICAL_E628_INTERNATIONAL_SERVICE = "7df7a3f7-f013-492f-a58e-68a8f078ab96";
    public static final String GENERIC_ACCESS = "00001800-0000-1000-8000-00805f9b34fb";
    public static final String GENERIC_ATTRIBUTES = "00001801-0000-1000-8000-00805f9b34fb";
    public static final String MY14_BIKE_ELLIPTICAL_SERVICE = "ac8f2400-9804-11e3-b25b-0002a5d5c51b";
    public static final String MY14_TREADMILL_SERVICE = "4b2387a0-be8e-11e3-8039-0002a5d5c51b";
    public static final String MY16_BIKE_ELLIPTICAL_SERVICE = "219fbae0-9df6-11e5-a87d-0002a5d5c51b";
    public static final String MY16_RECUMBENT_BIKE_INTERNATIONAL_SERVICE = "386c91ac-d22c-4b23-9325-e39a3c456003";
    public static final String MY16_TREADMILL_INTERNATIONAL_SERVICE = "19df5b20-31c7-11e6-a336-0002a5d5c51b";
    public static final String MY16_TREADMILL_SERVICE = "4f61e700-9df6-11e5-b820-0002a5d5c51b";
    public static final String MY17_BIKE_B618_SERVICE = "44f8d44f-7e03-4baf-9cc1-bd5a9c7a076b";
    public static final String MY17_ELLIPTICAL_E616_SERVICE = "3a1a1d3f-3d83-4c43-aa7b-81664ed75ec8";
    public static final String MY17_ELLIPTICAL_E618_SERVICE = "3d455463-07ab-4bce-a678-a33c433a5973";
    public static final String MY17_TREADMILL_T616_SERVICE = "89e14b5e-a841-48ad-b580-935f4545fffc";
    public static final String MY17_TREADMILL_T618_SERVICE = "f12f71ab-c228-45f2-98a2-6654f9adbe78";
    public static final String MY17_UPRIGHT_BIKE_UR616_SERVICE = "f755c9cf-e1fc-4ecd-8d90-f2d7ebf56b81";
    public static final String OMNI_ACK_RECORD = "35ddd0a0-9803-11e3-9a8b-0002a5d5c51b";
    public static final String OMNI_COMMAND_RECORD = "1717b3c0-9803-11e3-90e1-0002a5d5c51b";
    public static final String OMNI_DATA_RECORD = "4ed124e0-9803-11e3-b14c-0002a5d5c51b";
    public static final String OMNI_DEVICE_NAME = "00002a00-0000-1000-8000-00805f9b34fb";
    public static final String OMNI_EVENT_RECORD = "5c7d82a0-9803-11e3-8a6c-0002a5d5c51b";
    public static final String OMNI_FIRMWARE_REV = "00002a26-0000-1000-8000-00805f9b34fb";
    public static final String OMNI_HARDWARE_REV = "00002a27-0000-1000-8000-00805f9b34fb";
    public static final String OMNI_MANUFACTURER_NAME = "00002a29-0000-1000-8000-00805f9b34fb";
    public static final String OMNI_MODEL_NUMBER = "00002a24-0000-1000-8000-00805f9b34fb";
    public static final String OMNI_SOFTWARE_REV = "00002a28-0000-1000-8000-00805f9b34fb";
    public static final String OMNI_STATUS_RECORD = "4e349c00-999e-11e3-b341-0002a5d5c51b";
    public static final String OMNI_UNIQUE_ID = "e3f9af20-2674-11e3-879e-0002a5d5c51b";
    public static final String UPRIGHT_BIKE_UR628_INTERNATIONAL_SERVICE = "b1f93401-d4bd-4839-b7d7-b7434e9656cc";
    private static HashMap<String, String> attributes = new HashMap<>();

    static {
        attributes.put(GENERIC_ACCESS, "Generic Access");
        attributes.put(DEVICE_INFORMATION, "Device Information");
        attributes.put(GENERIC_ATTRIBUTES, "Generic Attributes");
        attributes.put(MY14_BIKE_ELLIPTICAL_SERVICE, "MY14 Bike-Elliptical Service");
        attributes.put(MY14_TREADMILL_SERVICE, "MY14 Treadmill Service");
        attributes.put(MY17_TREADMILL_T618_SERVICE, "MY17 T618Treadmill Service");
        attributes.put(MY17_ELLIPTICAL_E618_SERVICE, "My17 E618 Elliptical Service");
        attributes.put(MY17_BIKE_B618_SERVICE, "MY17 B618 Bike Service");
        attributes.put(MY17_TREADMILL_T616_SERVICE, "MY17 T616 Treadmill Service");
        attributes.put(MY17_ELLIPTICAL_E616_SERVICE, "MY17 E616 Elliptical Service");
        attributes.put(MY17_UPRIGHT_BIKE_UR616_SERVICE, "MY17 Upright Bike Service");
        attributes.put(OMNI_COMMAND_RECORD, "OMNI Command Record");
        attributes.put(OMNI_ACK_RECORD, "OMNI ACK Record");
        attributes.put(OMNI_DATA_RECORD, "OMNI Data Record");
        attributes.put(OMNI_EVENT_RECORD, "OMNI Event Record");
        attributes.put(OMNI_STATUS_RECORD, "OMNI Status Record");
        attributes.put(OMNI_UNIQUE_ID, "OMNI Uniquie ID");
        attributes.put(OMNI_DEVICE_NAME, "OMNI Device Name");
        attributes.put(OMNI_MODEL_NUMBER, "OMNI Model Number");
        attributes.put(OMNI_FIRMWARE_REV, "OMNI FW Rev");
        attributes.put(OMNI_HARDWARE_REV, "OMNI HW Rev");
        attributes.put(OMNI_SOFTWARE_REV, "OMNI SW Rev");
        attributes.put(OMNI_MANUFACTURER_NAME, "OMNI Manufacturer Name");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
