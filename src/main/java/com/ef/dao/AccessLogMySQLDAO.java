package com.ef.dao;

import com.ef.model.AccessLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessLogMySQLDAO {

    private final Connection connection;

    public AccessLogMySQLDAO(Connection connection) {
        this.connection = connection;
    }

    public void clear() {

        try (PreparedStatement preparedStatement = connection.prepareStatement("truncate table log")) {

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            throw new RuntimeException("Error deleting records on table log", ex);

        }
    }

    public void persist(List<AccessLog> logs) {

        String query = "insert into log (timestamp, ip_address, http_status, http_method, user_agent) values (?, ?, ?, ?, ?)";

        logs.forEach((log) -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setTimestamp(1, Timestamp.valueOf(log.getTimestamp()));
                preparedStatement.setString(2, log.getIpAddress());
                preparedStatement.setString(3, log.getHttpStatus());
                preparedStatement.setString(4, log.getHttpMethod());
                preparedStatement.setString(5, log.getUserAgent());

                preparedStatement.executeUpdate();

            } catch (SQLException ex) {

                throw new RuntimeException("Error inserting records on table log", ex);

            }
        });
    }
}
