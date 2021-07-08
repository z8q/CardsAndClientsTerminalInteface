package com.z8q.service;

import com.google.gson.Gson;
import com.z8q.dto.Card;
import com.z8q.dto.Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadAndShowOperations {

    public void showCardList() {
        try {
            String content = Files.lines(Paths.get("src/main/resources/CardList.txt")).reduce("", String::concat);
            Gson gson = new Gson();
            List<Card> printCardList = gson.fromJson(content, ArrayList.class);
            for (int i = 0; i < printCardList.size(); i++) {
                System.out.println(printCardList.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showClientList() {
        try {
            String content = Files.lines(Paths.get("src/main/resources/ClientList.txt")).reduce("", String::concat);
            Gson gson = new Gson();
            List<Client> printClientList = gson.fromJson(content, ArrayList.class);
            for (int i = 0; i < printClientList.size(); i++) {
                System.out.println(printClientList.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
