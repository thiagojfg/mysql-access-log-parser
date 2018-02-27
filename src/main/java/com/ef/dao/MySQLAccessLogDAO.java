package com.ef.dao;

import com.ef.model.AccessLog;
import java.util.ArrayList;
import java.util.List;

public class MySQLAccessLogDAO {

    private static List<AccessLog> LOGS = new ArrayList<>();

    public void persist(List<AccessLog> logs) {
        LOGS = logs;
    }
}
