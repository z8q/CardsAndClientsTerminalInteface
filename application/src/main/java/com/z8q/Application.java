package com.z8q;

import com.z8q.handler.CardHandlerImpl;
import com.z8q.handler.ClientHandlerImpl;
import com.z8q.interfaces.*;
import com.z8q.io.CardInputImpl;
import com.z8q.menu.MenuLevels;
import com.z8q.io.ClientInputImpl;

public class Application {
    public static void main(String[] args) {

        ClientOutput clientOutput = new ClientInputImpl();
        ClientInput clientInput = new ClientInputImpl();
        ClientHandler clientHandler = new ClientHandlerImpl(clientInput);

        CardOutput cardOutput = new CardInputImpl();
        CardInput cardInput = new CardInputImpl();
        CardHandler cardHandler = new CardHandlerImpl(cardInput);

        MenuLevels menuLevels = new MenuLevels(clientHandler, cardHandler, cardOutput, clientOutput);
        menuLevels.startMenu();

    }
}
