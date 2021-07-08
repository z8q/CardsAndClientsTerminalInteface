package com.z8q.menu;

import com.z8q.cardpropeties.FormFactor;
import com.z8q.service.CreateOperations;
import com.z8q.service.ReadAndShowOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MenuLevels {

    private Scanner sc = new Scanner(System.in);

    CreateOperations createOperations = new CreateOperations();

    public void startMenu() {
        while (true) {
            mainMenu();
            String choose = sc.nextLine();

            switch (choose) {
                case "1":
                    addCard();
                    System.out.println("Карта сохранена");
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
                case "0":
                    System.exit(0);
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
        System.out.println("3. Перейти в меню привязки карты к клиенту");
        System.out.println("-----------------------------------------");
        System.out.println("0. Выйти из программы");
    }

    public void addCard() {
        System.out.println("Введите номер карты - 16 цифр");
        String cardNumber16DigitsInput = sc.nextLine();
        System.out.println("Введите вид карты - REAL/VIRTUAL");
        String realOrVirtualInput = sc.nextLine();
        FormFactor formFactor;
        if (realOrVirtualInput.equals("REAL")) {
            formFactor = FormFactor.REAL;
        } else {
            formFactor = FormFactor.VIRTUAL;
        }
        System.out.println("Добавить чип в карту? - yes/no");
        String hasAChipInput = sc.nextLine();
        boolean x = false;
        if (hasAChipInput.equals("yes")) {
            x = true;
        }
        System.out.println("Введите пин-код - 4 цифры");
        String pinInput = sc.nextLine();

        createOperations.createCardObject(cardNumber16DigitsInput, formFactor, x, pinInput);
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

        createOperations.createClientObject(lastnameInput, firstnameInput, middlenameInput, birthDateInput);
    }

    public void secondMenu() {
        ReadAndShowOperations readAndShowOperations = new ReadAndShowOperations();

        System.out.println("Введите номер пункта для перехода по меню");
        System.out.println("-----------------------------------------");
        System.out.println("1. Показать список карт");
        System.out.println("-----------------------------------------");
        System.out.println("2. Показать список клиентов");
        System.out.println("-----------------------------------------");
        System.out.println("3. Привязать карту к клиенту");
//        System.out.println("-----------------------------------------");
//        System.out.println("4. Вернуться в основное меню");
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
        }
    }
    public void linkCardToClientMenu() {
        System.out.println("Введите id карты, которую хотите привязать");
        String idCardNumber = sc.nextLine();
        System.out.println("Введите номер id человека, к которому хотите привязать карту");
        String clientId = sc.nextLine();

        CreateOperations createOperations = new CreateOperations();
        createOperations.createClientObjectWithUpdatedCardList(idCardNumber, clientId);
    }
}
