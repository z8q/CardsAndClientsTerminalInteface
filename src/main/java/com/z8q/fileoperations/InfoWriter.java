package com.z8q.fileoperations;

import com.z8q.dto.Card;
import com.z8q.dto.Client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InfoWriter {

    public void writeCardInfo(Card card) {
        String path = "src/main/resources/CardList.txt";
        String appendText = String.valueOf(card);
        appendUsingFileWriter(path, appendText + "\n");
    }

    public void writeClientInfo(Client client) {

        String path = "src/main/resources/ClientList.txt";
        String appendText = String.valueOf(client);
        appendUsingFileWriter(path, appendText + "\n");
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
