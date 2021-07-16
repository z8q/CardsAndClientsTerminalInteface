package com.z8q.postgredb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CardsAndClientsTablesCreation {

    private static final Logger LOGGER = LogManager.getLogger(CardsAndClientsTablesCreation.class);

    public static void createTable(String path) throws SQLException {
        LOGGER.info("Attempt to create table {}", path.substring(path.lastIndexOf("/")+1));

        String makeQueryString = readSQLQuery(path);

        Connection connection = ConnectFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(makeQueryString);
        preparedStatement.execute();
    }

    private static String readSQLQuery(String path) {
        String createTableTemp = null;
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            createTableTemp = sb.toString();
        } catch (IOException e) {
            LOGGER.error("Error while reading SQLQuery", e);
        }
        return createTableTemp;
    }
}
