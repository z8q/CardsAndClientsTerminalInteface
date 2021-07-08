package com.z8q.service;

import com.google.gson.Gson;
import com.z8q.cardpropeties.FormFactor;
import com.z8q.dto.Card;
import com.z8q.dto.Client;

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

    WriteOperations writeOperations = new WriteOperations();

    private Long cardId = 0L;
    private Long clientId = 0L;

    public void createCardObject(String cardNumber16DigitsInput, FormFactor formFactor, boolean x, String pinInput) {
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
            e.printStackTrace();
        }

        Card card = new Card.Builder()
                .withId(cardId)
                .withCardNumberFirstFourDigits(cardNumber16DigitsInput.substring(0, 4))
                .withCardNumberSecondEightDigits(cardNumber16DigitsInput.substring(4, 12))
                .withCardNumberThirdFourDigits(cardNumber16DigitsInput.substring(12, 16))
                .withFormFactor(formFactor)
                .withHasAChip(x)
                .withPinCode(pinInput)
                .build();

        writeOperations.writeCardInfo(card);
    }

    public void createClientObject(String lastnameInput, String firstnameInput, String middlenameInput, String birthDateInput) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDateInput);
        } catch (ParseException e) {
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

        writeOperations.writeClientInfo(client);
    }

    public void createClientObjectWithUpdatedCardList(String idCardNumber, String clientId) {
        try {
            String content = Files.lines(Paths.get("src/main/resources/ClientList.txt")).reduce("", String::concat);
            Gson gsonClients = new Gson();
            Client[] clientArray = gsonClients.fromJson(content, Client[].class);

            String contentCards = Files.lines(Paths.get("src/main/resources/CardList.txt")).reduce("", String::concat);
            Gson gsonCards = new Gson();
            Card[] cardArray = gsonCards.fromJson(contentCards, Card[].class);

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

                    writeOperations.writeUpdatedClientInfo(clientAddElementToList);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

