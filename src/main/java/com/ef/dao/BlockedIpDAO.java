package com.ef.dao;

import com.ef.model.AccessPerIP;
import com.ef.model.Duration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BlockedIpDAO {

    private final Connection connection;

    public BlockedIpDAO(Connection connection) {
        this.connection = connection;
    }

    public void clear() {

        try (PreparedStatement preparedStatement = connection.prepareStatement("truncate table blocked_ips")) {

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            throw new RuntimeException("Error deleting records on table blocked_ips", ex);

        }
    }

    public void turnIpsBlocked(List<AccessPerIP> ips, Duration duration) {

        String query = "insert into blocked_ips (ip, message) values (?, ?)";

        ips.forEach((ip) -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, ip.getIp());
                preparedStatement.setString(2, "IP address has more than " + ip.getNumberOfAccess() + " access " + duration.getValue());

                preparedStatement.executeUpdate();

            } catch (SQLException ex) {

                throw new RuntimeException("Error inserting records on table blocked_ips", ex);

            }
        });
    }
}
