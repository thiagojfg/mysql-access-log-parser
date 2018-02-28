/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ef.dao;

import com.ef.dao.database.ConnectionProvider;
import com.ef.loader.AccessLogFileLoader;
import com.ef.model.AccessLog;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Thiago
 */
public class AccessLogMySQLDAOTest {

    private static Connection connection;

    private AccessLogMySQLDAO accessLogMySQLDAO;

    private AccessLogFileLoader accessLogFileLoader;

    @BeforeClass
    public static void setUpClass() throws SQLException, IOException {
        connection = ConnectionProvider.getConnection();
    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.close();
    }

    @Before
    public void setUp() {
        accessLogFileLoader = new AccessLogFileLoader(new File("src/test/resources/access.log"));
        accessLogMySQLDAO = new AccessLogMySQLDAO(connection);
    }

    @After
    public void tearDown() {
        accessLogMySQLDAO.clear();
    }

    @Test
    public void testPersist() throws IOException {

        List<AccessLog> logs = accessLogFileLoader.load();

        accessLogMySQLDAO.persist(logs);
    }
}
