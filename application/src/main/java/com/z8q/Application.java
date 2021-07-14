package com.z8q;

import com.z8q.handler.CardHandlerImpl;
import com.z8q.handler.ClientHandlerImpl;
import com.z8q.interfaces.*;
import com.z8q.io.CardInputImpl;
import com.z8q.menu.MenuLevels;
import com.z8q.io.ClientInputImpl;
import com.z8q.postgredb.CardsAndClientsTablesCreation;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {


        //-------------------------Подлежит замене-------------------------------
        ClientInputImpl clientOutput = new ClientInputImpl();
        CardInputImpl cardOutput = new CardInputImpl();
        //-------------------------Подлежит замене-------------------------------


        //---------------------------Не менять-----------------------------------
        ClientHandler clientHandler = new ClientHandlerImpl(clientOutput);
        CardHandler cardHandler = new CardHandlerImpl(cardOutput);

        MenuLevels menuLevels = new MenuLevels(clientHandler, cardHandler, cardOutput, clientOutput);
        menuLevels.startMenu();

        System.out.println("Program is stopped");
        //---------------------------Не менять-----------------------------------
    }
}
