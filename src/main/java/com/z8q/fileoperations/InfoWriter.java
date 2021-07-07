package com.z8q.fileoperations;

import com.google.gson.Gson;
import com.z8q.dto.Card;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InfoWriter {

    public void writeCardInfo(String json) {
        String path = "src/main/resources/CardList.txt";

//        try {
//            String contentCards = Files.lines(Paths.get(path)).reduce("", String::concat);
//            Gson gsonCards = new Gson();
//            Card[] cardArray = gsonCards.fromJson(contentCards, Card[].class);
//            Card[] newCardArray = new Card[cardArray.length+1];
//            for (int i = 0; i < cardArray.length; i++) {
//                newCardArray[i] = cardArray[i];
//            }
//            newCardArray[cardArray.length+1] = card;
//
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        appendUsingFileWriter(path, json + "\n");
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
