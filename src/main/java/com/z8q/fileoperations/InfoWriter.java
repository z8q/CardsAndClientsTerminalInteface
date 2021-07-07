package com.z8q.fileoperations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InfoWriter {

    public void writeCardInfo(String json) {
        String path = "src/main/resources/CardList.txt";
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
