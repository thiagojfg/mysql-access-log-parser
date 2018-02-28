package com.ef.model;

public enum Duration {
    HOURLY("hourly"), DAILY("daily");

    private final String value;

    private Duration(String value) {
        this.value = value;
    }

    public static final Duration parse(String value) {

        for (Duration duration : values()) {
            if (duration.value.equals(value)) {
                return duration;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }
}
