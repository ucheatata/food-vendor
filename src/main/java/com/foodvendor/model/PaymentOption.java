package com.foodvendor.model;

import java.util.HashMap;
import java.util.Map;

public enum PaymentOption {
    CASH(10),
    CARD(11);

    private int value;
    private static Map map = new HashMap<>();

    /**
     * Constructor sets gender value to int values (10 for male and 11 for female)
     *
     * @param value
     */
    private PaymentOption(int value) {
        this.value = value;
    }

    static {
        for (PaymentOption paymentOption : PaymentOption.values()) {
            map.put(paymentOption.value, paymentOption);
        }
    }

    public static PaymentOption valueOf(int gender) {
        return (PaymentOption) map.get(gender);
    }

    public int getValue() {
        return value;
    }
}
