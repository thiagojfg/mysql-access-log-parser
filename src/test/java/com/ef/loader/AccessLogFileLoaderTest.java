package com.ef.loader;

import com.ef.model.AccessLog;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccessLogFileLoaderTest {

    @Test
    public void loadAccessLogFile() throws IOException {

        AccessLogFileLoader accessLogFilePersister = new AccessLogFileLoader(new File("src/test/resources/access.log"));

        List<AccessLog> logs = accessLogFilePersister.load();

        assertNotNull(logs);
        assertFalse(logs.isEmpty());
    }
}
