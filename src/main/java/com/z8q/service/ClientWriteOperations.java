package com.z8q.service;

import com.google.gson.Gson;
import com.z8q.interfaces.ClientInterface;
import com.z8q.models.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClientWriteOperations implements ClientInterface {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CLIENTPATH = "src/main/resources/ClientList.txt";

    @Override
    public void getClientById(Client client) {

    }

    @Override
    public void getAll(Client client) {

    }

    @Override
    public void save(Client client) {
        try {
            LOGGER.info("Client with id {} was added to file", client.getId());

            String contentClients = Files.lines(Paths.get(CLIENTPATH)).reduce("", String::concat);
            Gson gsonCards = new Gson();
            List<Client> clientArray = gsonCards.fromJson(contentClients, ArrayList.class);
            if (clientArray != null) {
                clientArray.add(client);
                String contentClientsNew = gsonCards.toJson(clientArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTPATH));
                writer.write(contentClientsNew);
                writer.close();
            } else {
                List<Client> tempClientArray = new ArrayList<>();
                tempClientArray.add(client);
                String humansString = gsonCards.toJson(tempClientArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTPATH));
                writer.write(humansString);
                writer.close();
            }
        } catch (IOException e) {
            LOGGER.error("Error was occurred while saving Client with id {}", client.getId());
            e.printStackTrace();
        }
    }

    @Override
    public void linkCardToClient(Client client, int cardId) {
        try {
            LOGGER.info("Card with id {} was linked to Client with id {} and added to file", cardId, client.getId());

            String contentClients = Files.lines(Paths.get(CLIENTPATH)).reduce("", String::concat);
            Gson gsonCards = new Gson();
            List<Client> clientArray = gsonCards.fromJson(contentClients, ArrayList.class);
            if (clientArray != null) {
                clientArray.set((client.getId().intValue()-1), client);
                String contentClientsNew = gsonCards.toJson(clientArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTPATH));
                writer.write(contentClientsNew);
                writer.close();
            } else {
                List<Client> tempClientArray = new ArrayList<>();
                tempClientArray.add(client);
                String humansString = gsonCards.toJson(tempClientArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTPATH));
                writer.write(humansString);
                writer.close();
            }
        } catch (IOException e) {
            LOGGER.error("Card with id {} wasn't linked to Client with id {}", cardId, client.getId());
            e.printStackTrace();
        }
    }
}
