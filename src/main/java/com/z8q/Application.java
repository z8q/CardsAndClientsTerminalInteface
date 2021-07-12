package com.z8q;

import com.z8q.handler.CardHandler;
import com.z8q.handler.ClientHandler;
import com.z8q.interfaces.CardCheck;
import com.z8q.interfaces.CardIO;
import com.z8q.interfaces.ClientCheck;
import com.z8q.interfaces.ClientIO;
import com.z8q.menu.MenuLevels;
import com.z8q.service.CardWriteOperations;
import com.z8q.service.ClientWriteOperations;

public class Application {
    public static void main(String[] args) {

        ClientIO clientOperations = new ClientWriteOperations();
        ClientCheck clientHandler = new ClientHandler(clientOperations);

        CardIO cardOperations = new CardWriteOperations();
        CardCheck cardHandler = new CardHandler(cardOperations);

        MenuLevels menuLevels = new MenuLevels(clientHandler, cardHandler, cardOperations, clientOperations);
        menuLevels.startMenu();

    }
}
