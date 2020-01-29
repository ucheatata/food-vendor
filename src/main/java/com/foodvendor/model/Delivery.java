package com.foodvendor.model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public enum Delivery {

    DELIVERY_TRUE(true, Double.parseDouble(new DecimalFormat("#0.00").format(distance()))),
    DELIVERY_FALSE(false, 0);

    private static final double officeLongitude = 7.491302;
    private static final double officeLatitude = 9.072264;
    private static final double vendorLongitude = 7.70821;
    private static final double vendorLatitude = 8.53895;
    private final boolean deliveryStatus;
    private final double deliveryDistance;
    private static final Map<Boolean, Delivery> DELIVERY_MAP = new HashMap<>();

    private Delivery(boolean delivery, double distance){
        deliveryStatus = delivery;
        deliveryDistance = distance;
    }

    static {
        for (Delivery delivery: values()) {
            DELIVERY_MAP.put(delivery.deliveryStatus, delivery);
        }
    }

    public static Delivery GetDeliveryStatus (boolean status) {
        return DELIVERY_MAP.get(status);
    }

    public static double getOfficeLongitude() {
        return officeLongitude;
    }

    public static double getOfficeLatitude() {
        return officeLatitude;
    }

    public static double getVendorLongitude() {
        return vendorLongitude;
    }

    public static double getVendorLatitude() {
        return vendorLatitude;
    }

    public boolean isDeliveryStatus() {
        return deliveryStatus;
    }

    public double getDeliveryDistance() {
        return deliveryDistance;
    }

    private static double distance()
    {
        return Math.sqrt(Math.pow((getOfficeLongitude()-getVendorLongitude()),2) + Math.pow((getOfficeLatitude()-getVendorLatitude()),2));
    }
}
