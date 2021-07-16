package com.z8q.impl;

import com.z8q.dto.ClientDTO;
import com.z8q.interfaces.ClientInput;
import com.z8q.interfaces.ClientOutput;
import com.z8q.model.Client;
import com.z8q.postgredb.CardsAndClientsTablesCreation;
import com.z8q.postgredb.ConnectFactory;
import com.z8q.properties.MyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PostgreClientInputImpl implements ClientInput, ClientOutput {

    private static final Logger LOGGER = LogManager.getLogger(PostgreCardInputImpl.class);
    private static final String PATH_TO_CREATE_CLIENTS_TABLE = "postgre-db/src/main/resources/Clients.sql";
    private static final SimpleDateFormat DATETEMP = new SimpleDateFormat("dd/MM/yyyy");

    private static final String GET_CLIENT_BY_ID = "SELECT * FROM clients WHERE id = ?;";
    private static final String GET_ALL_CLIENTS = "SELECT * FROM clients;";
    private static final String INSERT_CLIENT = "INSERT INTO clients " +
            "(lastname, firstname, middlename, date_of_birth) VALUES (?, ?, ?, ?);";
    private static final String LINK_CARD_TO_CLIENT = "UPDATE cards SET client_id = ? where id = " +
            "(SELECT id FROM cards WHERE id = ?);";

    @Override
    public Client getClientById(Long clientIndex) {
        Client client = null;
        try (Connection connection = ConnectFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENT_BY_ID)) {
            preparedStatement.setLong(1, clientIndex);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long rsClientdId = rs.getLong("id");
                String rsLastname = rs.getString("lastname");
                String rsFirstname = rs.getString("firstname");
                String middlename = rs.getString("middlename");
                Date rsDateOfBirth = rs.getDate("date_of_birth");

                client = new Client.Builder()
                        .withId(rsClientdId)
                        .withLastName(rsLastname)
                        .withFirstName(rsFirstname)
                        .withMiddleName(middlename)
                        .withBirthDate(rsDateOfBirth)
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.error("Can't read the Card table");
        }
        return client;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clientList = new ArrayList<>();
        try (Connection connection = ConnectFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CLIENTS)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long rsClientdId = rs.getLong("id");
                String rsLastname = rs.getString("lastname");
                String rsFirstname = rs.getString("firstname");
                String middlename = rs.getString("middlename");
                Date rsDateOfBirth = rs.getDate("date_of_birth");

                Client client = new Client.Builder()
                        .withId(rsClientdId)
                        .withLastName(rsLastname)
                        .withFirstName(rsFirstname)
                        .withMiddleName(middlename)
                        .withBirthDate(rsDateOfBirth)
                        .build();
                clientList.add(client);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't read the Card table");
        }
        return clientList;
    }

    @Override
    public MyStatus save(Client client) {
        MyStatus status = new MyStatus();

        try (Connection connection = ConnectFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENT))
        {

            preparedStatement.setString(1, client.getLastName());
            preparedStatement.setString(2, client.getFirstName());
            preparedStatement.setString(3, client.getMiddleName());

            java.util.Date utilDate = client.getBirthDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            preparedStatement.setDate(4, sqlDate);

            preparedStatement.executeUpdate();

            LOGGER.info("Data was inserted into clients table");
            status.setStatus(true);
        } catch (SQLException e) {
            LOGGER.error("Data wasn't inserted into clients table");
            status.setStatus(false);
        }
        return status;
    }

    @Override
    public void createClientObject(ClientDTO clientDTO) {
        MyStatus status = new MyStatus();

        startCreationOfClientTableIfNotExists();

        Date date = convertStringToDate(clientDTO);

        Client client = new Client.Builder()
                .withLastName(clientDTO.getLastname())
                .withFirstName(clientDTO.getFirstname())
                .withMiddleName(clientDTO.getMiddlename())
                .withBirthDate(date)
                .build();

        MyStatus saveCard = save(client);
        if(saveCard.isStatus()) {
            status.setStatus(true);
        } else {
            status.setStatus(false);
            status.setMessage("Error on createCardObject stage");
            LOGGER.error("Error on createCardObject stage");
        }
    }

    protected void startCreationOfClientTableIfNotExists() {
        try {
            CardsAndClientsTablesCreation.createTable(PATH_TO_CREATE_CLIENTS_TABLE);
        } catch (SQLException throwables) {
            LOGGER.warn("Table {} already exists",
                    PATH_TO_CREATE_CLIENTS_TABLE.substring(PATH_TO_CREATE_CLIENTS_TABLE.lastIndexOf("/")+1));
        }
    }

    private Date convertStringToDate(ClientDTO clientDTO) {
        Date dateTemp = null;
        try {
            dateTemp = DATETEMP.parse(clientDTO.getDate());
        } catch (ParseException e) {
            LOGGER.error("Date format error",e);
        }
        return dateTemp;
    }

    @Override
    public MyStatus createClientObjectWithUpdatedCardList(String cardId, String clientId) {
        MyStatus status = new MyStatus();
        try (Connection connection = ConnectFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(LINK_CARD_TO_CLIENT)) {
            preparedStatement.setLong(1, Long.parseLong(clientId));
            preparedStatement.setLong(2, Long.parseLong(cardId));

            preparedStatement.executeUpdate();
            status.setStatus(true);
        } catch (SQLException throwables) {
            LOGGER.error("Error while linking card to client");
            status.setStatus(false);
        }
        return status;
    }
}
