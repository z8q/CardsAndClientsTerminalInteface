package com.z8q.menu;

import com.z8q.dto.CardDTO;
import com.z8q.dto.ClientDTO;
import com.z8q.dto.StatusMessage;
import com.z8q.handler.CardHandler;
import com.z8q.handler.ClientHandler;
import com.z8q.service.CreateOperations;
import com.z8q.service.ReadAndShowOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MenuLevels {

    private final Scanner sc = new Scanner(System.in);

    CreateOperations createOperations = new CreateOperations();
    ReadAndShowOperations readAndShowOperations = new ReadAndShowOperations();
    CardHandler cardHandler = new CardHandler();
    ClientHandler clientHandler = new ClientHandler();
    StatusMessage statusMessage = new StatusMessage();

    public void startMenu() {
        while (true) {
            mainMenu();
            String choose = sc.nextLine();

            switch (choose) {
                case "1":
                    addCard();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    addClient();
                    break;
                case "3":
                    secondMenu();
                    break;
                case "4":
                    readAndShowOperations.showCardList();
                    try {
                        Thread.sleep(1800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "5":
                    readAndShowOperations.showClientList();
                    try {
                        Thread.sleep(1800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("\nВыберите существующий пункт меню\n");
                    try {
                        Thread.sleep(1800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    public static void mainMenu() {
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

    public void addCard() {
        CardDTO cardDTO = new CardDTO();

        System.out.println("Введите номер карты - 16 цифр");
        String cardNumber16DigitsInput = null;
        statusMessage = new StatusMessage();
        statusMessage.setCompletion(false);
        while(!statusMessage.isCompletion()) {
            System.out.println("Повторите попытку, для выхода просто нажмите Enter");
            cardNumber16DigitsInput = sc.nextLine();
            statusMessage = cardHandler.checkPAN(cardNumber16DigitsInput);
            if(cardNumber16DigitsInput.length()==0) {
                new MenuLevels().startMenu();
            }
        }
        cardDTO.setPan(cardNumber16DigitsInput);

        System.out.println("Введите вид карты - REAL/VIRTUAL");
        String realOrVirtualInput = sc.nextLine();
        statusMessage = cardHandler.checkFormFactor(realOrVirtualInput);
        while(!statusMessage.isCompletion()) {
            System.out.println("Повторите попытку, для выхода просто нажмите Enter");
            realOrVirtualInput = sc.nextLine();
            statusMessage = cardHandler.checkFormFactor(realOrVirtualInput);
            if(realOrVirtualInput.length()==0) {
                new MenuLevels().startMenu();
            }
        }
        cardDTO.setFormFactor(realOrVirtualInput);

        if (realOrVirtualInput.equals("VIRTUAL")) {
            cardDTO.setChip("no");
        } else {
            System.out.println("Добавить чип в карту? - yes/no");
            String hasAChipInput = sc.nextLine();
            statusMessage = cardHandler.checkChip(hasAChipInput);
            while(!statusMessage.isCompletion()) {
                System.out.println("Повторите попытку, для выхода просто нажмите Enter");
                hasAChipInput = sc.nextLine();
                statusMessage = cardHandler.checkChip(hasAChipInput);
                if(hasAChipInput.length()==0) {
                    new MenuLevels().startMenu();
                }
            }
            cardDTO.setChip(hasAChipInput);
        }

        System.out.println("Введите пин-код - 4 цифры");
        String pinInput = sc.nextLine();
        statusMessage = cardHandler.checkPin(pinInput);
        while(!statusMessage.isCompletion()) {
            System.out.println("Повторите попытку, для выхода просто нажмите Enter");
            pinInput = sc.nextLine();
            statusMessage = cardHandler.checkPin(pinInput);
            if(pinInput.length()==0) {
                new MenuLevels().startMenu();
            }
        }
        cardDTO.setPinCode(pinInput);

        cardHandler.sendCardToIo(cardDTO);
    }

    public void addClient() {
        ClientDTO clientDTO = new ClientDTO();

        System.out.println("Введите фамилию");
        String lastnameInput = sc.nextLine();
        statusMessage = clientHandler.checkLastName(lastnameInput);
        while(!statusMessage.isCompletion()) {
            System.out.println("Фамилия могжет содержать только буквы\nПовторите ввод, для выхода просто нажмите Enter");
            lastnameInput = sc.nextLine();
            statusMessage = clientHandler.checkLastName(lastnameInput);
            if(lastnameInput.length()==0) {
                new MenuLevels().startMenu();
            }
        }
        clientDTO.setLastname(lastnameInput);

        System.out.println("Введите имя");
        String firstnameInput = sc.nextLine();
        statusMessage = clientHandler.checkFirstName(firstnameInput);
        while(!statusMessage.isCompletion()) {
            System.out.println("Имя может содержать только буквы\nПовторите ввод, для выхода просто нажмите Enter");
            firstnameInput = sc.nextLine();
            statusMessage = clientHandler.checkFirstName(firstnameInput);
            if(firstnameInput.length()==0) {
                new MenuLevels().startMenu();
            }
        }
        clientDTO.setFirstname(firstnameInput);

        System.out.println("Введите отчество");
        String middlenameInput = sc.nextLine();
        statusMessage = clientHandler.checkMiddleName(middlenameInput);
        while(!statusMessage.isCompletion()) {
            System.out.println("Отчество может содержать только буквы\nПовторите ввод, для выхода просто нажмите Enter");
            middlenameInput = sc.nextLine();
            statusMessage = clientHandler.checkMiddleName(middlenameInput);
            if(middlenameInput.length()==0) {
                new MenuLevels().startMenu();
            }
        }
        clientDTO.setMiddlename(middlenameInput);

        System.out.println("Введите дату рождения - формат дд/мм/гггг");
        String birthDateInput = sc.nextLine();
        statusMessage = clientHandler.checkBirthDate(birthDateInput);
        while(!statusMessage.isCompletion()) {
            System.out.println("Формат даты - формат дд/мм/гггг\nПовторите ввод, для выхода просто нажмите Enter");
            birthDateInput = sc.nextLine();
            statusMessage = clientHandler.checkBirthDate(birthDateInput);
            if(birthDateInput.length()==0) {
                new MenuLevels().startMenu();
            }
        }
        clientDTO.setDate(birthDateInput);

        clientHandler.sendCardToIo(clientDTO);
    }

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
                readAndShowOperations.showCardList();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "2":
                readAndShowOperations.showClientList();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "3":
                linkCardToClientMenu();
                break;
            case "4":
                break;
            default:
                System.out.println("\nВыберите существующий пункт меню\n");
                try {
                    Thread.sleep(1800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public void linkCardToClientMenu() {
        System.out.println("Введите id карты, которую хотите привязать");
        String idCardNumber = sc.nextLine();
        System.out.println("Введите номер id человека, к которому хотите привязать карту");
        String clientId = sc.nextLine();

        createOperations.createClientObjectWithUpdatedCardList(idCardNumber, clientId);

    }

    public void repeatAttemptAfterLink() {
        System.out.println("Ввдены ошибочные данные, хотите повторить попытку? 1 - да / 2 - нет");
        String choose = sc.nextLine();
        switch (choose) {
            case "1":
                linkCardToClientMenu();
                break;
            case "2":
                break;
            default:
                System.out.println("\nВыберите существующий пункт меню\n");
                try {
                    Thread.sleep(1800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
