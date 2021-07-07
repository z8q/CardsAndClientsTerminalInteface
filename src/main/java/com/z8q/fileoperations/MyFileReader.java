package com.z8q.fileoperations;

import com.google.gson.Gson;
import com.z8q.dto.Card;
import com.z8q.dto.Client;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class MyFileReader {

    private Scanner sc = new Scanner(System.in);


    public void printCardList() {

        try {
            String content = Files.lines(Paths.get("src/main/resources/CardList.txt")).reduce("", String::concat);
            Gson gson = new Gson();
            Client[] client = gson.fromJson(content, Client[].class);
            System.out.println(gson.toJson(client));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void printClientList() {
        try {
            String content = Files.lines(Paths.get("src/main/resources/ClientList.txt")).reduce("", String::concat);
            Gson gson = new Gson();
            Client[] client = gson.fromJson(content, Client[].class);
            System.out.println(gson.toJson(client));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkCardToClientMenu() {
        System.out.println("Введите id карты, которую хотите привязать");
        String idCardNumber = sc.nextLine();
        System.out.println("Введите номер id человека, к которому хотите привязать карту");
        String clientId = sc.nextLine();

        addCardToClient(idCardNumber, clientId);


    }

    public static void addCardToClient(String idCardNumber, String clientId) {
        try {
            String content = Files.lines(Paths.get("src/main/resources/ClientList.txt")).reduce("", String::concat);
            Gson gson = new Gson();
            Client[] clientArray = gson.fromJson(content, Client[].class);

            String contentCards = Files.lines(Paths.get("src/main/resources/CardList.txt")).reduce("", String::concat);
            Gson gsonCards = new Gson();
            Card[] cardArray = gsonCards.fromJson(contentCards, Card[].class);

            List<Card> list = clientArray[Integer.parseInt(clientId)-1].getClientCards();

                for (Card card : cardArray) {
                    if (card.getId().intValue() == (Integer.parseInt(idCardNumber))) {
                        list.add(cardArray[Integer.parseInt(idCardNumber) - 1]);

                        Client clientAddElementToList = new Client.Builder()
                                .withId(clientArray[Integer.parseInt(clientId)-1].getId())
                                .withLastName(clientArray[Integer.parseInt(clientId)-1].getLastName())
                                .withFirstName(clientArray[Integer.parseInt(clientId)-1].getFirstName())
                                .withMiddleName(clientArray[Integer.parseInt(clientId)-1].getMiddleName())
                                .withBirthDate(clientArray[Integer.parseInt(clientId)-1].getBirthDate())
                                .withClientCards(list)
                                .build();

                        String jsonClient = gson.toJson(clientAddElementToList);
                        InfoWriter infoWriter = new InfoWriter();
                        infoWriter.writeClientInfo(jsonClient);
                    }
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


