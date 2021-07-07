package com.z8q.fileoperations;

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

public class InfoWriter {

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


        //appendUsingFileWriter(path, json + "\n");
    }

    public void writeClientInfo(String json) {

        String path = "src/main/resources/ClientList.txt";
        appendUsingFileWriter(path, json + "\n");
    }

    private static void appendUsingFileWriter(String filePath, String text) {
        File file = new File(filePath);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file,true);
            fr.write(text);

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                assert fr != null;
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
