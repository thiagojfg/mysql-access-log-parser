package com.ef;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Config {

    private static final String ACCESS_LOG = "--accesslog";
    private static final String START_DATE = "--startDate";
    private static final String DURATION = "--duration";
    private static final String THRESHOLD = "--threshold";

    private static final String DATE_PATTERN = "YYYY-MM-DD'.'HH:mm:ss";

    private final File accessLog;
    private final Date startDate;
    private final Duration duration;
    private final Integer threshold;

    static final Config parse(String[] args) throws ConfigFailedException {

        File accessLog = null;
        Date startDate = null;
        Duration duration = null;
        Integer threshold = null;

        for (String arg : args) {

            String[] tuple = arg.split("=");

            if (ACCESS_LOG.equals(tuple[0])) {

                File file = new File(tuple[1]);

                if (file.exists()) {
                    accessLog = file;
                } else {
                    throw new ConfigFailedException("Access log doesn\'t exists");
                }

                continue;
            }

            if (START_DATE.equals(tuple[0])) {

                DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);

                try {
                    startDate = dateFormat.parse(tuple[1]);
                } catch (ParseException ex) {
                    throw new ConfigFailedException("Invalid format for startDate param", ex);
                }

                continue;
            }

            if (DURATION.equals(tuple[0])) {

                Duration parsed = Duration.parse(tuple[1]);

                if (parsed != null) {
                    duration = parsed;
                } else {
                    throw new ConfigFailedException("Param duration is not valid");
                }

                continue;
            }

            if (THRESHOLD.equals(tuple[0])) {

                Integer value = null;

                try {

                    value = Integer.valueOf(tuple[1]);

                } catch (NumberFormatException e) {

                    throw new ConfigFailedException("Invalid integer value for threshold");

                }

                if (value >= 1) {

                    threshold = value;

                } else {
                    throw new ConfigFailedException("Invalid value for threshold, must be greater than 0");
                }
            }
        }
        
        if(startDate == null){
            throw new ConfigFailedException("Missing --startDate param");
        }
        
        if(duration == null){
            throw new ConfigFailedException("Missing --duration param");
        }        
        
        if(threshold == null){
            throw new ConfigFailedException("Missing --threshold param");
        }

        return new Config(accessLog, startDate, duration, threshold);
    }

    private Config(File file, Date startDate, Duration duration, Integer threshold) {
        this.accessLog = file;
        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
    }

    File getAccessLog() {
        return accessLog;
    }

    Date getStartDate() {
        return startDate;
    }

    Duration getDuration() {
        return duration;
    }

    Integer getThreshold() {
        return threshold;
    }

    static enum Duration {
        HOURLY("hourly"), DAILY("daily");

        private final String value;

        private Duration(String value) {
            this.value = value;
        }

        static final Duration parse(String value) {

            for (Duration duration : values()) {
                if (duration.value.equals(value)) {
                    return duration;
                }
            }

            return null;
        }
    }
}
