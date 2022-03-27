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
    private static final String CLIENTPATH = "file-db/src/main/resources/ClientList.txt";
    private static final String CARDPATH = "file-db/src/main/resources/CardList.txt";
    private static final SimpleDateFormat DATETEMP = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Client getClientById(Long clientIndex) {
        List<Client> clientArray = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(CLIENTPATH));
            String contentClients = sc.nextLine();
            Gson gsonCards = new Gson();
            clientArray = gsonCards.fromJson(contentClients, ArrayList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return clientArray.get(clientIndex.intValue());
    }

    @Override
    public List<Client> getAll() {
        List<Client> printClientList = new ArrayList<>();
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
            LOGGER.error("Error was occurred while saving Client with id {}", client.getId(), e);
            status.setStatus(false);
            status.setMessage("Error on Client save stage");
            return status;
        }
    }
    private List<Client> saveTxtIntoList(Gson gsonClients) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(CLIENTPATH));
        List<Client> clientArray = null;
        while(sc.hasNext()) {
            String contentClients = sc.nextLine();
            clientArray = gsonClients.fromJson(contentClients, ArrayList.class);
        }
        return clientArray;
    }
    private String addElementToList(List<Client> clientArray, Client client, Gson gsonClients) {
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

    private void linkCardToClient(Client client, int cardId) {
        MyStatus status = new MyStatus();
        try {
            LOGGER.info("Card with id {} was linked to Client with id {} and added to file", cardId, client.getId());
            Gson gson = new Gson();

            List<Client> clientList = fromJSONToList(gson);

            String contentClientsNew;
            if (clientList != null) {
                clientList.set((client.getId().intValue()-1), client);
                contentClientsNew = gson.toJson(clientList);
            } else {
                List<Client> tempClientArray = new ArrayList<>();
                tempClientArray.add(client);
                contentClientsNew = gson.toJson(tempClientArray);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTPATH));
            writer.write(contentClientsNew);
            writer.close();

            status.setStatus(true);
        } catch (IOException e) {
            LOGGER.error("Card with id {} wasn't linked to Client with id {}", cardId, client.getId());
            e.printStackTrace();
            status.setStatus(false);
            status.setMessage("Error appears on ClientWriteOperations - linkCardToClient stage");
        }
    }

    @Override
    public void createClientObject(ClientDTO clientDTO) {
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
            System.out.println("Client was saved. \n");
        } else {
            status.setStatus(false);
            status.setMessage("Something went wrong. Client wasn't saved. \n");
        }
    }

    private Date convertStringToDate(ClientDTO clientDTO) {
        Date dateTemp = null;
        try {
            dateTemp = DATETEMP.parse(clientDTO.getDate());
        } catch (ParseException e) {
            LOGGER.error("Date format error");
            e.printStackTrace();
        }
        return dateTemp;
    }

    private List<Client> fromJSONToList(Gson gson) {
        String contentClients = null;
        try {
            Scanner sc = new Scanner(new File(CLIENTPATH));
            while (sc.hasNext()) {
                contentClients = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Error while reading file ClientList.txt");
            e.printStackTrace();
        }
        return gson.fromJson(contentClients, ArrayList.class);
    }

    private Long defineClientId(List<Client> clientArray) {
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
        Gson gson = new Gson();

        Client[] clientArray = fromJSONToArray(gson);
        Card[] cardArray = fromJSONToArrayCard(gson);

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
        return status;
    }

    private Client[] fromJSONToArray(Gson gson) {
        Client[] clientArray = new Client[0];
        try {
            Scanner sc = new Scanner(new File(CLIENTPATH));
            String content = null;
            while (sc.hasNext()) {
                content = sc.nextLine();
            }
            clientArray = gson.fromJson(content, Client[].class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return clientArray;
    }

    private Card[] fromJSONToArrayCard(Gson gson) {
        Card[] cardArray = new Card[0];
        try {
            Scanner sc = new Scanner(new File(CARDPATH));
            String content = null;
            while (sc.hasNext()) {
                content = sc.nextLine();
            }
            cardArray = gson.fromJson(content, Card[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cardArray;
    }
}
