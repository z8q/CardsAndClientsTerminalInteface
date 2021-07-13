package com.z8q;

import com.z8q.handler.CardHandlerImpl;
import com.z8q.handler.ClientHandlerImpl;
import com.z8q.interfaces.CardHandler;
import com.z8q.interfaces.CardIO;
import com.z8q.interfaces.ClientHandler;
import com.z8q.interfaces.ClientIO;
import com.z8q.menu.MenuLevels;
import com.z8q.service.CardIOImpl;
import com.z8q.service.ClientIOImpl;

public class Application {
    public static void main(String[] args) {

        ClientIO clientOperations = new ClientIOImpl();
        ClientHandler clientHandler = new ClientHandlerImpl();

        CardIO cardOperations = new CardIOImpl();
        CardHandler cardHandler = new CardHandlerImpl();

        MenuLevels menuLevels = new MenuLevels(clientHandler, cardHandler, cardOperations, clientOperations);
        menuLevels.startMenu();

    }
}
