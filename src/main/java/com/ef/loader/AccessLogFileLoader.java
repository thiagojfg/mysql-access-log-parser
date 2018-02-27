package com.ef.loader;

import com.ef.model.AccessLog;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AccessLogFileLoader {

    private final File file;

    public AccessLogFileLoader(File file) {
        this.file = file;
    }

    public List<AccessLog> load() throws IOException {

        List<AccessLog> logs = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            stream.forEach((s) -> logs.add(AccessLog.create(s)));
        }

        return logs;
    }
}
