package com.ef.model;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccessLogTest {

    @Test
    public void createAccessLog() throws ParseException {

        LocalDateTime dateTime = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0, 11, 763000000);

        String line = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        AccessLog accessLog = AccessLog.create(line);

        assertNotNull(accessLog);
        assertEquals(dateTime, accessLog.getTimestamp());
        assertEquals("192.168.234.82", accessLog.getIpAddress());
        assertEquals("\"GET / HTTP/1.1\"", accessLog.getHttpMethod());
        assertEquals("200", accessLog.getHttpStatus());
        assertEquals("\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"", accessLog.getUserAgent());
    }
}
