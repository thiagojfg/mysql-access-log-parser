package com.ef;

import com.ef.dao.AccessLogDAO;
import com.ef.dao.BlockedIpDAO;
import com.ef.dao.database.ConnectionProvider;
import com.ef.loader.AccessLogFileLoader;
import com.ef.model.AccessPerIP;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Parser {

    public static void main(String[] args) throws ConfigFailedException, IOException, SQLException {

        Config config = Config.parse(args);

        try (Connection connection = ConnectionProvider.getConnection()) {

            AccessLogDAO accessLogDAO = new AccessLogDAO(connection);

            BlockedIpDAO blockedIpDAO = new BlockedIpDAO(connection);

            if (config.getAccessLog() != null) {

                AccessLogFileLoader fileLoader = new AccessLogFileLoader(config.getAccessLog());

                accessLogDAO.clear();
                blockedIpDAO.clear();

                accessLogDAO.persist(fileLoader.load());
            }

            List<AccessPerIP> accessPerIPs = accessLogDAO.searchIps(config.getStartDate(), config.getDuration(), config.getThreshold());

            blockedIpDAO.turnIpsBlocked(accessPerIPs, config.getDuration());

            accessPerIPs.forEach(System.out::println);

        } catch (Exception ex) {

            throw new RuntimeException("Error running parser", ex);

        }
    }
}
