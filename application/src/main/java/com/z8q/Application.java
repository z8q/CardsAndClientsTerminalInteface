package com.z8q;

import com.z8q.handler.CardHandlerImpl;
import com.z8q.handler.ClientHandlerImpl;
import com.z8q.impl.PostgreCardInputImpl;
import com.z8q.impl.PostgreClientInputImpl;
import com.z8q.interfaces.*;
import com.z8q.io.CardInputImpl;
import com.z8q.menu.MenuLevels;
import com.z8q.io.ClientInputImpl;
import com.z8q.postgredb.CardsAndClientsTablesCreation;

public class Application {
    public static void main(String[] args) {

        //-------------------------You can use postgreSQL to store data-------------------------------
        PostgreClientInputImpl clientInput = PostgreClientInputImpl.checkClientTableAndGetInstance();
        PostgreCardInputImpl cardInput = PostgreCardInputImpl.checkCardTableAndGetInstance();
        //-------------------------Or simple txt file-------------------------------
//        ClientInputImpl clientInput = new ClientInputImpl();
//        CardInputImpl cardInput = new CardInputImpl();

        ClientHandler clientHandler = new ClientHandlerImpl(clientInput);
        CardHandler cardHandler = new CardHandlerImpl(cardInput);

        MenuLevels menuLevels = new MenuLevels(clientHandler, cardHandler, cardInput, clientInput);
        menuLevels.startMenu();

        System.out.println("Program is stopped");
    }
}
