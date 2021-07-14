package com.z8q.io;

import com.google.gson.Gson;
import com.z8q.dto.ClientDTO;
import com.z8q.interfaces.ClientInput;
import com.z8q.interfaces.ClientOutput;
import com.z8q.model.Card;
import com.z8q.model.Client;
import com.z8q.properties.MyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClientInputImpl implements ClientInput, ClientOutput {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CLIENTPATH = "file-database-api/src/main/resources/ClientList.txt";
    private static final String CARDPATH = "file-database-api/src/main/resources/CardList.txt";

    @Override
    public Client getClientById(Long clientIndex) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(CLIENTPATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String contentClients = sc.nextLine();
        Gson gsonCards = new Gson();
        List<Client> clientArray = gsonCards.fromJson(contentClients, ArrayList.class);
        return clientArray.get(clientIndex.intValue());
    }

    @Override
    public List<Client> getAll() {
        List<Client> printClientList = null;
        try {
            Scanner sc = new Scanner(new File(CLIENTPATH));
            String content = sc.nextLine();
            Gson gson = new Gson();
            printClientList = gson.fromJson(content, ArrayList.class);
        } catch (IOException e) {
            LOGGER.error("Wrong path to file or Wrong JSON syntax");
            e.printStackTrace();
        }
        return printClientList;
    }

    @Override
    public MyStatus save(Client client) {
        MyStatus status = new MyStatus();
        try {
            LOGGER.info("Client with id {} was added to file", client.getId());
            Gson gsonClients = new Gson();
            List<Client> clientArray = saveTxtIntoList(gsonClients);
            String contentClientsNew = addElementToList(clientArray, client, gsonClients);

            BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTPATH));
            writer.write(contentClientsNew);
            writer.close();

            status.setStatus(true);
            return status;
        } catch (IOException e) {
            LOGGER.error("Error was occurred while saving Client with id {}", client.getId());
            e.printStackTrace();
            status.setStatus(false);
            status.setMessage("Error on Client save stage");
            return status;
        }
    }
    public List<Client> saveTxtIntoList(Gson gsonClients) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(CLIENTPATH));
        List<Client> clientArray = null;
        while(sc.hasNext()) {
            String contentClients = sc.nextLine();
            clientArray = gsonClients.fromJson(contentClients, ArrayList.class);
        }
        return clientArray;
    }
    public String addElementToList(List<Client> clientArray, Client client, Gson gsonClients) {
        String contentClientsNew;
        if (clientArray != null) {
            clientArray.add(client);
            contentClientsNew = gsonClients.toJson(clientArray);
        } else {
            List<Client> tempClientArray = new ArrayList<>();
            tempClientArray.add(client);
            contentClientsNew = gsonClients.toJson(tempClientArray);
        }
        return contentClientsNew;
    }
    @Override
    public MyStatus linkCardToClient(Client client, int cardId) {
        MyStatus status = new MyStatus();
        try {
            LOGGER.info("Card with id {} was linked to Client with id {} and added to file", cardId, client.getId());

            Scanner sc = new Scanner(new File(CLIENTPATH));
            String contentClients = null;
            while (sc.hasNext()) {
                contentClients = sc.nextLine();
            }
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
            status.setStatus(true);
            return status;
        } catch (IOException e) {
            LOGGER.error("Card with id {} wasn't linked to Client with id {}", cardId, client.getId());
            e.printStackTrace();
            status.setStatus(false);
            status.setMessage("Error appears on ClientWriteOperations - linkCardToClient stage");
            return status;
        }
    }

    @Override
    public MyStatus createClientObject(ClientDTO clientDTO) {
        MyStatus status = new MyStatus();
        Gson gsonClients = new Gson();

        Date date = convertStringToDate(clientDTO);
        List<Client> clientArray = fromJSONToList(gsonClients);
        Long clientId = defineClientId(clientArray);

        Client client = new Client.Builder()
                .withId(clientId)
                .withLastName(clientDTO.getLastname())
                .withFirstName(clientDTO.getFirstname())
                .withMiddleName(clientDTO.getMiddlename())
                .withBirthDate(date)
                .withClientCards(Collections.emptyList())
                .build();
        if(save(client).isStatus()) {
            status.setStatus(true);
            System.out.println("Клиент сохранен \n");
        } else {
            status.setStatus(false);
        }
        return status;
    }

    public Date convertStringToDate(ClientDTO clientDTO) {
        Date dateTemp = null;
        try {
            dateTemp = new SimpleDateFormat("dd/MM/yyyy").parse(clientDTO.getDate());
        } catch (ParseException e) {
            LOGGER.error("Date format error");
            e.printStackTrace();
        }
        return dateTemp;
    }

    public List<Client> fromJSONToList(Gson gsonClients) {
        String contentClients = null;
        try {
            Scanner sc = new Scanner(new File(CLIENTPATH));
            while (sc.hasNext()) {
                contentClients = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("Error while reading file ClientList.txt");
        }
        return gsonClients.fromJson(contentClients, ArrayList.class);
    }

    public Long defineClientId(List<Client> clientArray) {
        Long clientIdTemp;
        if (clientArray == null) {
            clientIdTemp = 1L;
        } else {
            clientIdTemp = (long) (clientArray.size() + 1);
        }
        return clientIdTemp;
    }
    @Override
    public MyStatus createClientObjectWithUpdatedCardList(String cardId, String clientId) {
        MyStatus status = new MyStatus();
        try {
            Scanner sc = new Scanner(new File(CLIENTPATH));
            String content = null;
            while (sc.hasNext()) {
                content = sc.nextLine();
            }
            Gson gsonClients = new Gson();
            Client[] clientArray = gsonClients.fromJson(content, Client[].class);

            Scanner scCard = new Scanner(new File(CARDPATH));
            String contentCards = scCard.nextLine();
            Gson gsonCards = new Gson();
            Card[] cardArray = gsonCards.fromJson(contentCards, Card[].class);

            if (Integer.parseInt(clientId) <= clientArray.length) {
                if (Integer.parseInt((cardId)) <= cardArray.length) {
                    List<String> listOfCards = clientArray[Integer.parseInt(clientId) - 1].getClientCards();

                    for (Card card : cardArray) {
                        if (card.getId().intValue() == (Integer.parseInt(cardId))) {
                            listOfCards.add(Long.toString(cardArray[Integer.parseInt(cardId) - 1].getId()));

                            Client clientAddElementToList = new Client.Builder()
                                    .withId(clientArray[Integer.parseInt(clientId) - 1].getId())
                                    .withLastName(clientArray[Integer.parseInt(clientId) - 1].getLastName())
                                    .withFirstName(clientArray[Integer.parseInt(clientId) - 1].getFirstName())
                                    .withMiddleName(clientArray[Integer.parseInt(clientId) - 1].getMiddleName())
                                    .withBirthDate(clientArray[Integer.parseInt(clientId) - 1].getBirthDate())
                                    .withClientCards(listOfCards)
                                    .build();

                            linkCardToClient(clientAddElementToList, card.getId().intValue());
                            status.setStatus(true);
                        }
                    }
                } else {
                    LOGGER.warn("There is no card with id {}", cardId);
                    status.setStatus(false);
                }
            } else {
                LOGGER.warn("There is no client with id {}", clientId);
                status.setStatus(false);
            }
        } catch(IOException e){
            LOGGER.error("Intput error while linking card to client");
            e.printStackTrace();
            status.setStatus(false);
        }
        return status;
    }
}
