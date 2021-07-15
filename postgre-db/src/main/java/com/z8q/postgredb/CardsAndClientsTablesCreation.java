package com.z8q.postgredb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.z8q.dto.CardDTO;
import com.z8q.impl.PostgreCardInputImpl;
import com.z8q.interfaces.PostgreCardInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CardsAndClientsTablesCreation {

    private static final Logger LOGGER = LogManager.getLogger(CardsAndClientsTablesCreation.class);
    private static final String PATH_TO_CREATE_CARDS_TABLE = "postgre-db/src/main/resources/Cards.sql";
    private static final String PATH_TO_CREATE_CLIENTS_TABLES = "postgre-db/src/main/resources/Clients.sql";
    private String createTable;

    public void createTable(String path) throws SQLException, IOException {
        LOGGER.info("Attempt to create table {}", path.substring(path.lastIndexOf("/")+1));

        readSQLQuery(path);

        try (Connection connection = ConnectFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createTable)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Error on creating table stage");
            throw e;
        }
    }

    private void readSQLQuery(String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            createTable = sb.toString();
        } catch (IOException e) {
            LOGGER.error("Error while reading SQLQuery", e);
            throw e;
        }
    }

//    public static void main(String[] args) {
//        CardsAndClientsTablesCreation t1 = new CardsAndClientsTablesCreation();
//        PostgreCardInput p1 = new PostgreCardInputImpl();
//        CardDTO cdto = new CardDTO("1111333355557777", "1",
//                "yes", "4444");
//        try {
//            t1.createTable(PATH_TO_CREATE_CARDS_TABLE);
//            t1.createTable(PATH_TO_CREATE_CLIENTS_TABLES);
//            p1.createCardObject(cdto);
//        } catch (SQLException | IOException e) {
//            LOGGER.error("Error while creating new tables", e);
//            System.exit(0);
//        }
//    }
}
