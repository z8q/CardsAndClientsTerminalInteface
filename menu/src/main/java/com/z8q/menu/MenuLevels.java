package com.z8q.menu;

import com.z8q.dto.CardDTO;
import com.z8q.dto.ClientDTO;
import com.z8q.interfaces.*;
import com.z8q.model.Card;
import com.z8q.properties.FormFactor;
import com.z8q.properties.MyStatus;

import java.util.*;

public class MenuLevels {

    private final Scanner sc = new Scanner(System.in);

    CardHandler cardHandler;
    ClientHandler clientHandler;
    CardOutput cardOutput;
    ClientOutput clientOutput;

    public MenuLevels(ClientHandler clientHandler, CardHandler cardHandler, CardOutput cardOutput, ClientOutput clientOutput) {
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
                    List<Card> card = cardOutput.getAll();
                    if (card.isEmpty()) {
                        System.out.println("List is empty");
                    } else {
                        for (Object o : cardOutput.getAll()) {
                            System.out.println(o);
                        }
                    }
                    break;
                case "5":
                    for(Object o : clientOutput.getAll()) {
                        System.out.println(o);
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("\nВыберите существующий пункт меню\n");
                    break;
            }
        }
    }

    public void mainMenu() {
        System.out.println("Главное меню");
        System.out.println("-----------------------------------------");
        System.out.println("Введите номер пункта для перехода по меню");
        System.out.println("-----------------------------------------");
        System.out.println("1. Добавить карту");
        System.out.println("-----------------------------------------");
        System.out.println("2. Добавить клиента");
        System.out.println("-----------------------------------------");
        System.out.println("3. Привязать карту к клиенту");
        System.out.println("-----------------------------------------");
        System.out.println("4. Показать список карт");
        System.out.println("-----------------------------------------");
        System.out.println("5. Показать список клиентов");
        System.out.println("-----------------------------------------");
        System.out.println("0. Выйти из программы");
    }

    public void addCard() {

        System.out.println("Введите номер карты - 16 цифр");
        String cardNumber16DigitsInput = sc.nextLine();

        System.out.println("Введите вид карты\nДля выбора обычной карты нажмите - 1\nДля выбора виртуальной карты нажмите - 2");
        String realOrVirtualInput = sc.nextLine();

        String hasAChipInput;
        if (FormFactor.VIRTUAL.name().equals(realOrVirtualInput)) {
            hasAChipInput = "no";
        } else {
            System.out.println("Добавить чип в карту? - yes/no");
            hasAChipInput = sc.nextLine();
        }

        System.out.println("Введите пин-код - 4 цифры");
        String pinInput = sc.nextLine();

        CardDTO cardDTO = new CardDTO(cardNumber16DigitsInput, realOrVirtualInput, hasAChipInput, pinInput);
        MyStatus operationStatus = cardHandler.checkCardDTO(cardDTO);
        if (!operationStatus.isStatus()) {
            System.out.println(operationStatus.getMessage());
        }
    }

    public void addClient() {

        System.out.println("Введите фамилию");
        String lastnameInput = sc.nextLine();

        System.out.println("Введите имя");
        String firstnameInput = sc.nextLine();

        System.out.println("Введите отчество");
        String middlenameInput = sc.nextLine();

        System.out.println("Введите дату рождения - формат дд/мм/гггг");
        String birthDateInput = sc.nextLine();

        ClientDTO clientDTO = new ClientDTO(lastnameInput, firstnameInput, middlenameInput, birthDateInput);
        MyStatus checkClient = clientHandler.checkClientDTO(clientDTO);
        if (!checkClient.isStatus()) {
            System.out.println(checkClient.getMessage());

        }
    }

    public void linkCardToClientMenu() {
        System.out.println("Введите id карты, которую хотите привязать");
        String cardId = sc.nextLine();
        System.out.println("Введите номер id человека, к которому хотите привязать карту");
        String clientId = sc.nextLine();
        MyStatus linkCardToClient = clientHandler.checkPossibilityToLinkCardToClient(cardId, clientId);
        if (linkCardToClient.isStatus()) {
            System.out.println("Карта привязана");
        } else {
            System.out.println("Карта не привязана");
        }

    }
}
