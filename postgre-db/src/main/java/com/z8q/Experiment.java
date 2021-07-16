package com.z8q;

import com.z8q.postgredb.ConnectFactory;

import java.sql.*;

public class Experiment {

    public static void main(String[] args) {
        try(Connection connection = ConnectFactory.getConnection()) {

            String query = "select id from cards where id = 2";
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            String id = null;
            while (rs.next()) {
                id = rs.getString("id");
                System.out.println(id);
            }
            if (id == null) {
                System.out.println("error");
            }

//            DatabaseMetaData metaData = connection.getMetaData();
//            ResultSet table = metaData.getTables(null, null, "clients", null);
//
//            if(table.next()) {
//                System.out.println("true");
//            } else {
//                System.out.println("false");
//            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
