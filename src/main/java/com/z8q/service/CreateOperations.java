package com.z8q.service;

import com.google.gson.Gson;
import com.z8q.cardpropeties.FormFactor;
import com.z8q.models.Card;
import com.z8q.models.Client;
import com.z8q.menu.MenuLevels;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CreateOperations {

    private static final Logger LOGGER = LogManager.getLogger(CreateOperations.class);
    private Long cardId = 0L;
    private Long clientId = 0L;

    CardWriteOperations cardWriteOperations = new CardWriteOperations();
    ClientWriteOperations clientWriteOperations = new ClientWriteOperations();


    public void createCardObject(String cardNumber16DigitsInput, String formFactor, String isHasAChipArg, String pinInput) {
        try {
            String path = "src/main/resources/CardList.txt";
            String contentCards = Files.lines(Paths.get(path)).reduce("", String::concat);
            Gson gsonCards = new Gson();
            List<Card> cardArray = gsonCards.fromJson(contentCards, ArrayList.class);
            if (cardArray == null) {
                cardId = 1L;
            } else {
                cardId = (long) (cardArray.size() + 1);
            }
        } catch (IOException e) {
            LOGGER.error("Error while creating a Card");
            e.printStackTrace();
        }

        FormFactor formEnum;
        if (formFactor.equals("REAL")) {
            formEnum = FormFactor.REAL;
        } else {
            formEnum = FormFactor.VIRTUAL;
        }

        boolean hasChip = false;
        if (isHasAChipArg.equals("yes")) {
            hasChip = true;
        }

        Card card = new Card.Builder()
                .withId(cardId)
                .withCardNumberFirstFourDigits(cardNumber16DigitsInput.substring(0, 4))
                .withCardNumberSecondEightDigits(cardNumber16DigitsInput.substring(4, 12))
                .withCardNumberThirdFourDigits(cardNumber16DigitsInput.substring(12, 16))
                .withFormFactor(formEnum)
                .withHasAChip(hasChip)
                .withPinCode(pinInput)
                .build();

        cardWriteOperations.save(card);
        System.out.println("Карта сохранена \n");
    }

    public void createClientObject(String lastnameInput, String firstnameInput, String middlenameInput, String birthDateInput) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDateInput);
        } catch (ParseException e) {
            LOGGER.error("Date format error");
            e.printStackTrace();
        }

        try {
            String path = "src/main/resources/ClientList.txt";
            String contentClients = Files.lines(Paths.get(path)).reduce("", String::concat);
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

        clientWriteOperations.save(client);
        System.out.println("Клиент сохранен \n");
    }

    public void createClientObjectWithUpdatedCardList(String idCardNumber, String clientId) {
            try {

                if ((idCardNumber.matches("[-+]?\\d+")) && (clientId.matches("[-+]?\\d+"))) {

                    String content = Files.lines(Paths.get("src/main/resources/ClientList.txt")).reduce("", String::concat);
                    Gson gsonClients = new Gson();
                    Client[] clientArray = gsonClients.fromJson(content, Client[].class);

                    String contentCards = Files.lines(Paths.get("src/main/resources/CardList.txt")).reduce("", String::concat);
                    Gson gsonCards = new Gson();
                    Card[] cardArray = gsonCards.fromJson(contentCards, Card[].class);

                    if (Integer.parseInt(clientId) <= clientArray.length) {
                        if (Integer.parseInt((idCardNumber)) <= cardArray.length) {
                            List<Card> listOfCards = clientArray[Integer.parseInt(clientId) - 1].getClientCards();

                            for (Card card : cardArray) {
                                if (card.getId().intValue() == (Integer.parseInt(idCardNumber))) {
                                    listOfCards.add(cardArray[Integer.parseInt(idCardNumber) - 1]);

                                    Client clientAddElementToList = new Client.Builder()
                                            .withId(clientArray[Integer.parseInt(clientId) - 1].getId())
                                            .withLastName(clientArray[Integer.parseInt(clientId) - 1].getLastName())
                                            .withFirstName(clientArray[Integer.parseInt(clientId) - 1].getFirstName())
                                            .withMiddleName(clientArray[Integer.parseInt(clientId) - 1].getMiddleName())
                                            .withBirthDate(clientArray[Integer.parseInt(clientId) - 1].getBirthDate())
                                            .withClientCards(listOfCards)
                                            .build();

                                    clientWriteOperations.linkCardToClient(clientAddElementToList, card.getId().intValue());
                                }
                            }
                        } else {
                            LOGGER.warn("There is no card with id {}", idCardNumber);
                            try {
                                Thread.sleep(1800);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            new MenuLevels().repeatAttemptAfterLink();
                        }
                    } else {
                        LOGGER.warn("There is no client with id {}", clientId);
                        try {
                            Thread.sleep(1800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        new MenuLevels().repeatAttemptAfterLink();
                    }
                } else {
                    LOGGER.warn("Error with arguments while linking Card to Client");
                    try {
                        Thread.sleep(1800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new MenuLevels().repeatAttemptAfterLink();
                }
            } catch(IOException e){
                LOGGER.error("Intput error while linking card to client");
                e.printStackTrace();
            }
    }
}

