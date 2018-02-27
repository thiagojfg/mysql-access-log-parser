package com.ef.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccessLog {

    private static final String DELIMITER = "\\|";

    private final LocalDateTime timestamp;
    private final String ipAddress;
    private final String httpMethod;
    private final String httpStatus;
    private final String userAgent;

    public static final AccessLog create(String line) {

        String[] parts = line.split(DELIMITER);

        LocalDateTime timestamp = LocalDateTime.parse(parts[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        String ipAddress = parts[1];
        String httpMethod = parts[2];
        String httpStatus = parts[3];
        String userAgent = parts[4];

        return new AccessLog(timestamp, ipAddress, httpMethod, httpStatus, userAgent);
    }

    private AccessLog(LocalDateTime timestamp, String ipAddress, String httpMethod, String httpStatus, String userAgent) {
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
        this.httpMethod = httpMethod;
        this.httpStatus = httpStatus;
        this.userAgent = userAgent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
