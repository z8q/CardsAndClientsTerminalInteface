package com.z8q.postgredb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateTables {

    public static void main(String[] args) {
        String s;
        StringBuilder query = new StringBuilder();
        try {
            FileReader fr = new FileReader("postgre-db/src/main/resources/CardsAndClients.sql");
            BufferedReader br = new BufferedReader(fr);
            while((s = br.readLine()) != null) {
                query.append(s);
            }
            br.close();

            String[] inst = query.toString().split(";");
            Connection connection = ConnectFactory.getConnection();

            for (String value : inst) {
                PreparedStatement st = connection.prepareStatement(value);
                if (!value.trim().equals("")) {
                    st.execute();
                }
            }
            System.out.println("Clients and cards tables were created.");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
