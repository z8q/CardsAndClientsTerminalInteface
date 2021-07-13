package com.z8q.menu;

import com.z8q.dto.CardDTO;
import com.z8q.dto.ClientDTO;
import com.z8q.interfaces.*;
import com.z8q.models.Client;
import com.z8q.propeties.MyStatus;

import java.util.*;

public class MenuLevels implements MenuInterface {

    private final Scanner sc = new Scanner(System.in);

    CardHandler cardHandler;
    ClientHandler clientHandler;
    CardIO cardIO;
    ClientIO clientIO;

    public MenuLevels(ClientHandler clientHandler, CardHandler cardHandler, CardIO cardIO, ClientIO clientIO) {
        this.clientHandler = clientHandler;
        this.cardHandler = cardHandler;
        this.cardIO = cardIO;
        this.clientIO = clientIO;
    }

    @Override
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
                    secondMenu();
                    break;
                case "4":
                    System.out.println(cardIO.getAll());
                    break;
                case "5":
                    System.out.println(clientIO.getAll());
                    //System.out.println(clientIO.getAll());
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("\nВыберите существующий пункт меню\n");
                    break;
            }
        }
    }

    @Override
    public void mainMenu() {
        System.out.println("Главное меню");
        System.out.println("-----------------------------------------");
        System.out.println("Введите номер пункта для перехода по меню");
        System.out.println("-----------------------------------------");
        System.out.println("1. Добавить карту");
        System.out.println("-----------------------------------------");
        System.out.println("2. Добавить клиента");
        System.out.println("-----------------------------------------");
        System.out.println("3. Перейти в меню привязки карты к клиенту");
        System.out.println("-----------------------------------------");
        System.out.println("4. Показать список карт");
        System.out.println("-----------------------------------------");
        System.out.println("5. Показать список клиентов");
        System.out.println("-----------------------------------------");
        System.out.println("0. Выйти из программы");
    }
    @Override
    public void addCard() {

        System.out.println("Введите номер карты - 16 цифр");
        String cardNumber16DigitsInput = sc.nextLine();

        System.out.println("Введите вид карты - REAL/VIRTUAL");
        String realOrVirtualInput = sc.nextLine();

        System.out.println("Добавить чип в карту? - yes/no");
        String hasAChipInput = sc.nextLine();

        System.out.println("Введите пин-код - 4 цифры");
        String pinInput = sc.nextLine();

        CardDTO cardDTO = new CardDTO(cardNumber16DigitsInput, realOrVirtualInput, hasAChipInput, pinInput);
        if (!cardHandler.checkCardDTO(cardDTO).isStatus()) {
            startMenu();
        } else {
            cardIO.createCardObject(cardDTO.getPan(), cardDTO.getFormFactor(),
                    cardDTO.getChip(), cardDTO.getPinCode());
        }
    }
    @Override
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
        if (!clientHandler.checkClientDTO(clientDTO).isStatus()) {
            startMenu();
        } else {
            clientIO.createClientObject(clientDTO.getLastname(), clientDTO.getFirstname(),
                    clientDTO.getMiddlename(), clientDTO.getDate());
        }
    }
    @Override
    public void secondMenu() {

        System.out.println("Введите номер пункта для перехода по меню");
        System.out.println("-----------------------------------------");
        System.out.println("1. Показать список карт");
        System.out.println("-----------------------------------------");
        System.out.println("2. Показать список клиентов");
        System.out.println("-----------------------------------------");
        System.out.println("3. Привязать карту к клиенту");
        System.out.println("-----------------------------------------");
        System.out.println("4. Вернуться в основное меню");
        String chooseSecondMenu = sc.nextLine();
        switch (chooseSecondMenu) {
            case "1":
                System.out.println(cardIO.getAll());
                break;
            case "2":
                System.out.println(clientIO.getAll());
                break;
            case "3":
                linkCardToClientMenu();
                break;
            case "4":
                break;
            default:
                System.out.println("\nВыберите существующий пункт меню\n");
                break;
        }
    }
    @Override
    public void linkCardToClientMenu() {
        System.out.println("Введите id карты, которую хотите привязать");
        String cardId = sc.nextLine();
        System.out.println("Введите номер id человека, к которому хотите привязать карту");
        String clientId = sc.nextLine();
        MyStatus linkCardToClient = clientIO.createClientObjectWithUpdatedCardList(cardId, clientId);
        if (linkCardToClient.isStatus()) {
            System.out.println("Карта привязана");
        } else {
            System.out.println("Карта не привязана");
        }
        startMenu();
    }
}
