package com.z8q.menu;

import com.z8q.dto.CardDTO;
import com.z8q.dto.ClientDTO;
import com.z8q.interfaces.*;
import com.z8q.model.Card;
import com.z8q.model.Client;
import com.z8q.properties.MyStatus;

import java.util.*;

public class MenuLevels {

    private final Scanner sc = new Scanner(System.in);

    CardHandler cardHandler;
    ClientHandler clientHandler;
    CardOutput cardOutput;
    ClientOutput clientOutput;

    public MenuLevels(ClientHandler clientHandler, CardHandler cardHandler,
                      CardOutput cardOutput, ClientOutput clientOutput) {
        this.clientHandler = clientHandler;
        this.cardHandler = cardHandler;
        this.cardOutput = cardOutput;
        this.clientOutput = clientOutput;
    }

    public void startMenu() {
        while (true) {
            mainMenu();
            String choose = sc.nextLine();

            switch (choose) {
                case "1":
                    addCard();
                    break;
                case "2":
                    addClient();
                    break;
                case "3":
                    linkCardToClientMenu();
                    break;
                case "4":
                    List<Card> cards = cardOutput.getAll();
                    if (cards.isEmpty()) {
                        System.out.println("\nList is empty\n");
                    } else {
                        for (Object o : cards) {
                            System.out.println(o);
                        }
                    }
                    break;
                case "5":
                    List<Client> clients = clientOutput.getAll();
                    if (clients.isEmpty()) {
                        System.out.println("\nList is empty\n");
                    } else {
                        for (Object o : clients) {
                            System.out.println(o);
                        }
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\nSelect an existing menu item\n");
                    break;
            }
        }
    }

    public void mainMenu() {
        System.out.println("Main menu");
        System.out.println("-----------------------------------------");
        System.out.println("Select the menu item");
        System.out.println("-----------------------------------------");
        System.out.println("1. Create new card");
        System.out.println("-----------------------------------------");
        System.out.println("2. Create new client");
        System.out.println("-----------------------------------------");
        System.out.println("3. Link card to client");
        System.out.println("-----------------------------------------");
        System.out.println("4. Show a list of cards");
        System.out.println("-----------------------------------------");
        System.out.println("5. Show a list of clients");
        System.out.println("-----------------------------------------");
        System.out.println("0. Exit");
    }

    public void addCard() {

        System.out.println("Enter card number - 16 digits");
        String cardNumber16DigitsInput = sc.nextLine();

        System.out.println("Enter card type\nFor regular card enter - 1\nFor virtual card enter - 2");
        String realOrVirtualInput = sc.nextLine();

        String hasAChipInput;
        if (("2").equals(realOrVirtualInput)) {
            hasAChipInput = "no";
        } else {
            System.out.println("Add chip to card? - yes/no");
            hasAChipInput = sc.nextLine();
        }

        System.out.println("Enter pin-code - 4 digits");
        String pinInput = sc.nextLine();

        CardDTO cardDTO = new CardDTO(cardNumber16DigitsInput, realOrVirtualInput, hasAChipInput, pinInput);
        MyStatus operationStatus = cardHandler.checkCardDTO(cardDTO);
        if (!operationStatus.isStatus()) {
            System.out.println(operationStatus.getMessage());
        }
    }

    public void addClient() {

        System.out.println("Enter lastname");
        String lastnameInput = sc.nextLine();

        System.out.println("Enter name");
        String firstnameInput = sc.nextLine();

        System.out.println("Enter middle name");
        String middlenameInput = sc.nextLine();

        System.out.println("Enter birthdate - format dd/mm/yyyy");
        String birthDateInput = sc.nextLine();

        ClientDTO clientDTO = new ClientDTO(lastnameInput, firstnameInput, middlenameInput, birthDateInput);
        MyStatus checkClient = clientHandler.checkClientDTO(clientDTO);
        if (!checkClient.isStatus()) {
            System.out.println(checkClient.getMessage());

        }
    }

    public void linkCardToClientMenu() {
        System.out.println("Enter card id you want to link");
        String cardId = sc.nextLine();
        System.out.println("Enter client id you want to link");
        String clientId = sc.nextLine();
        MyStatus linkCardToClient = clientHandler.checkPossibilityToLinkCardToClient(cardId, clientId);
        if (linkCardToClient.isStatus()) {
            System.out.println("Card and client was linked");
        }
        else {
            System.out.println("Something went wrong. Card and client wasn't linked");
        }
    }
}
