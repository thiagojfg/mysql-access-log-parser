package com.ef.dao;

import com.ef.model.AccessLog;
import com.ef.model.AccessPerIP;
import com.ef.model.Duration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccessLogDAO {

    private final Connection connection;

    public AccessLogDAO(Connection connection) {
        this.connection = connection;
    }

    public void clear() {

        try (PreparedStatement preparedStatement = connection.prepareStatement("truncate table logs")) {

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            throw new RuntimeException("Error deleting records on table logs", ex);

        }
    }

    public void persist(List<AccessLog> logs) {

        String query = "insert into logs (timestamp, ip_address, http_status, http_method, user_agent) values (?, ?, ?, ?, ?)";

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

    public List<AccessPerIP> searchIps(LocalDateTime startDate, Duration duration, Integer threshold) {

        StringBuilder builder = new StringBuilder();

        builder.append("    select ip_address, ");
        builder.append("           count(*) as quantity ");
        builder.append("      from logs ");
        builder.append("     where timestamp between ? and ? ");
        builder.append("  group by ip_address ");
        builder.append("    having count(*) > ? ");

        try (PreparedStatement stmt = connection.prepareStatement(builder.toString())) {

            stmt.setTimestamp(1, Timestamp.valueOf(startDate));

            switch (duration) {
                case DAILY:
                    stmt.setTimestamp(2, Timestamp.valueOf(startDate.plusDays(1)));
                    break;
                case HOURLY:
                    stmt.setTimestamp(2, Timestamp.valueOf(startDate.plusHours(1)));
                    break;
                default:
                    throw new RuntimeException("Cannot handle duration");
            }

            stmt.setInt(3, threshold);

            ResultSet resultSet = stmt.executeQuery();

            List<AccessPerIP> access = new ArrayList<>();

            while (resultSet.next()) {
                access.add(new AccessPerIP(resultSet.getString("ip_address"), resultSet.getLong("quantity")));
            }

            return access;

        } catch (SQLException ex) {
            throw new RuntimeException("Error searching records on table log", ex);
        }
    }
}
