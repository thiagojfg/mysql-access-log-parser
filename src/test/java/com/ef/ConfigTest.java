package com.ef;

import com.ef.model.Duration;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigTest {

    @Test
    public void parseWithFullParams() throws ConfigFailedException {

        Config config = Config.parse(getValidArgs());

        assertNotNull(config);
        assertNotNull(config.getAccessLog());
        assertNotNull(config.getStartDate());
        assertEquals(Duration.HOURLY, config.getDuration());
        assertEquals(Long.valueOf(100), Long.valueOf(config.getThreshold()));
    }

    public static String[] getValidArgs() {

        String[] args = new String[4];

        args[0] = "--accesslog=" + new File("src/test/resources/access.log").getAbsolutePath();
        args[1] = "--startDate=2017-01-01.13:00:00";
        args[2] = "--duration=daily";
        args[3] = "--threshold=100";

        return args;
    }

    @Test(expected = ConfigFailedException.class)
    public void parseWithInvalidPath() throws ConfigFailedException {

        String[] args = getValidArgs();

        args[0] = "--accesslog=aaaa";

        Config.parse(args);
    }

    @Test(expected = ConfigFailedException.class)
    public void parseWithInvalidDate() throws ConfigFailedException {

        String[] args = getValidArgs();

        args[1] = "--startDate=aaaa";

        Config.parse(args);
    }

    @Test(expected = ConfigFailedException.class)
    public void parseWithInvalidDuration() throws ConfigFailedException {

        String[] args = getValidArgs();

        args[2] = "--duration=yearly";

        Config.parse(args);
    }

    @Test(expected = ConfigFailedException.class)
    public void parseWithInvalidThreshold() throws ConfigFailedException {

        String[] args = getValidArgs();

        args[3] = "--threshold=0";

        Config.parse(args);
    }
}
