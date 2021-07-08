package com.z8q.service;

import com.google.gson.Gson;
import com.z8q.dto.Card;
import com.z8q.dto.Client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriteOperations {

    public void writeCardInfo(Card card) {
        String path = "src/main/resources/CardList.txt";

        try {
            String contentCards = Files.lines(Paths.get(path)).reduce("", String::concat);
            Gson gsonCards = new Gson();
            List<Card> cardArray = gsonCards.fromJson(contentCards, ArrayList.class);
            if (cardArray != null) {
                cardArray.add(card);
                String contentCardsNew = gsonCards.toJson(cardArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(contentCardsNew);
                writer.close();
            } else {
                List<Card> tempCardArray = new ArrayList<>();
                tempCardArray.add(card);
                String humansString = gsonCards.toJson(tempCardArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(humansString);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeClientInfo(Client client) {

        String path = "src/main/resources/ClientList.txt";

        try {
            String contentClients = Files.lines(Paths.get(path)).reduce("", String::concat);
            Gson gsonCards = new Gson();
            List<Client> clientArray = gsonCards.fromJson(contentClients, ArrayList.class);
            if (clientArray != null) {
                clientArray.add(client);
                String contentClientsNew = gsonCards.toJson(clientArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(contentClientsNew);
                writer.close();
            } else {
                List<Client> tempClientArray = new ArrayList<>();
                tempClientArray.add(client);
                String humansString = gsonCards.toJson(tempClientArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(humansString);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUpdatedClientInfo(Client client) {

        String path = "src/main/resources/ClientList.txt";

        try {
            String contentClients = Files.lines(Paths.get(path)).reduce("", String::concat);
            Gson gsonCards = new Gson();
            List<Client> clientArray = gsonCards.fromJson(contentClients, ArrayList.class);
            if (clientArray != null) {
                clientArray.set((client.getId().intValue()-1), client);
                String contentClientsNew = gsonCards.toJson(clientArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(contentClientsNew);
                writer.close();
            } else {
                List<Client> tempClientArray = new ArrayList<>();
                tempClientArray.add(client);
                String humansString = gsonCards.toJson(tempClientArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write(humansString);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
