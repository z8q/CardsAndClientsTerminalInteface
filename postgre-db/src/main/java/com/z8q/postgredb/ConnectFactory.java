package com.z8q.postgredb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectFactory {
    public static final String jdbcURL = "jdbc:postgresql://localhost:5432/cards_clients_01";
    public static final String jdbcUsername = "sm";
    public static final String jdbcPassword = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
}
