package com.z8q.service;

import com.google.gson.Gson;
import com.z8q.interfaces.ClientIO;
import com.z8q.models.Card;
import com.z8q.models.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClientWriteOperations implements ClientIO {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CLIENTPATH = "src/main/resources/ClientList.txt";
    private static final String CARDPATH = "src/main/resources/CardList.txt";
    private Long clientId = 0L;


    @Override
    public void getClientById(Client client) {

    }

    @Override
    public void getAll() {
        try {
            //String content = Files.lines(Paths.get("src/main/resources/ClientList.txt")).reduce("", String::concat);
            Scanner sc = new Scanner(new File(CLIENTPATH));
            String content = sc.nextLine();
            Gson gson = new Gson();
            List<Client> printClientList = gson.fromJson(content, ArrayList.class);
            for (int i = 0; i < printClientList.size(); i++) {
                System.out.println(printClientList.get(i));
            }
        } catch (IOException e) {
            LOGGER.error("Wrong path to file or Wrong JSON syntax");
            e.printStackTrace();
        }
    }

    @Override
    public void save(Client client) {
        try {
            LOGGER.info("Client with id {} was added to file", client.getId());

            //String contentClients = Files.lines(Paths.get(CLIENTPATH)).reduce("", String::concat);
            Scanner sc = new Scanner(new File(CLIENTPATH));
            String contentClients = sc.nextLine();
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

            //String contentClients = Files.lines(Paths.get(CLIENTPATH)).reduce("", String::concat);
            Scanner sc = new Scanner(new File(CLIENTPATH));
            String contentClients = sc.nextLine();
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

    @Override
    public void createClientObject(String lastnameInput, String firstnameInput, String middlenameInput, String birthDateInput) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDateInput);
        } catch (ParseException e) {
            LOGGER.error("Date format error");
            e.printStackTrace();
        }

        try {
//            String path = "src/main/resources/ClientList.txt";
//            String contentClients = Files.lines(Paths.get(path)).reduce("", String::concat);
            Scanner sc = new Scanner(new File(CLIENTPATH));
            String contentClients = sc.nextLine();
            Gson gsonClients = new Gson();
            List<Client> clientArray = gsonClients.fromJson(contentClients, ArrayList.class);
            if (clientArray == null) {
                clientId = 1L;
            } else {
                clientId = (long) (clientArray.size() + 1);
            }
        } catch (IOException e) {
            LOGGER.error("Error while creating a Client");
            e.printStackTrace();
        }

        Client client = new Client.Builder()
                .withId(clientId)
                .withLastName(lastnameInput)
                .withFirstName(firstnameInput)
                .withMiddleName(middlenameInput)
                .withBirthDate(date)
                .withClientCards(Collections.emptyList())
                .build();

        save(client);
        System.out.println("Клиент сохранен \n");
    }

    @Override
    public boolean createClientObjectWithUpdatedCardList(String idCardNumber, String clientId) {
        try {
            if ((idCardNumber.matches("[-+]?\\d+")) && (clientId.matches("[-+]?\\d+"))) {

//                String content = Files.lines(Paths.get("src/main/resources/ClientList.txt")).reduce("", String::concat);
                Scanner sc = new Scanner(new File(CLIENTPATH));
                String content = sc.nextLine();
                Gson gsonClients = new Gson();
                Client[] clientArray = gsonClients.fromJson(content, Client[].class);

                //String contentCards = Files.lines(Paths.get("src/main/resources/CardList.txt")).reduce("", String::concat);
                Scanner scCard = new Scanner(new File(CARDPATH));
                String contentCards = scCard.nextLine();
                Gson gsonCards = new Gson();
                Card[] cardArray = gsonCards.fromJson(contentCards, Card[].class);

                //List<Long> cardIds = new ArrayList<>();

                if (Integer.parseInt(clientId) <= clientArray.length) {
                    if (Integer.parseInt((idCardNumber)) <= cardArray.length) {
                        List<String> listOfCards = clientArray[Integer.parseInt(clientId) - 1].getClientCards();

                        for (Card card : cardArray) {
                            if (card.getId().intValue() == (Integer.parseInt(idCardNumber))) {
                                listOfCards.add(Long.toString(cardArray[Integer.parseInt(idCardNumber) - 1].getId()));
                                //cardIds.add(Long.toString(cardArray[Integer.parseInt(idCardNumber)-1].getId()));

                                Client clientAddElementToList = new Client.Builder()
                                        .withId(clientArray[Integer.parseInt(clientId) - 1].getId())
                                        .withLastName(clientArray[Integer.parseInt(clientId) - 1].getLastName())
                                        .withFirstName(clientArray[Integer.parseInt(clientId) - 1].getFirstName())
                                        .withMiddleName(clientArray[Integer.parseInt(clientId) - 1].getMiddleName())
                                        .withBirthDate(clientArray[Integer.parseInt(clientId) - 1].getBirthDate())
                                        .withClientCards(listOfCards)
                                        .build();

                                linkCardToClient(clientAddElementToList, card.getId().intValue());
                                return true;
                            }
                        }
                        return false;
                    } else {
                        LOGGER.warn("There is no card with id {}", idCardNumber);
                        return false;
                    }
                } else {
                    LOGGER.warn("There is no client with id {}", clientId);
                    return false;
                }
            } else {
                LOGGER.warn("Error with arguments while linking Card to Client");
                return false;
            }
        } catch(IOException e){
            LOGGER.error("Intput error while linking card to client");
            e.printStackTrace();
            return false;
        }
    }
}
