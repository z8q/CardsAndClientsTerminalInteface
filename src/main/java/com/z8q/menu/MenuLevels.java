package com.z8q.menu;

import com.z8q.cardpropeties.FormFactor;
import com.z8q.dto.Card;
import com.z8q.dto.Client;
import com.z8q.fileoperations.InfoWriter;
import com.z8q.fileoperations.MyFileReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MenuLevels {

    private Scanner sc = new Scanner(System.in);

    InfoWriter infoWriter = new InfoWriter();

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
        Card card = new Card(cardNumber16DigitsInput.substring(0, 4), cardNumber16DigitsInput.substring(4, 12), cardNumber16DigitsInput.substring(12, 16),
                formFactor, x, Integer.parseInt(pinInput));
        Client client = new Client();
        client.addToClientCards(card);
        infoWriter.writeCardInfo(card);
        System.out.println(client.getClientCards());
    }

    public void addClient() {
        System.out.println("Введите фамилию");
        String surnameInput = sc.nextLine();
        System.out.println("Введите имя");
        String firstnameInput = sc.nextLine();
        System.out.println("Введите отчество");
        String middlenameInput = sc.nextLine();
        System.out.println("Введите дату рождения - формат дд/мм/гггг");
        String birthDateInput = sc.nextLine();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = sdf.parse(birthDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        assert date != null;
        cal.setTime(date);

        System.out.println(cal);

        Client client = new Client(surnameInput, firstnameInput, middlenameInput, cal);

        infoWriter.writeClientInfo(client);
    }

    public void secondMenu() {
        MyFileReader myFileReader = new MyFileReader();

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
                myFileReader.printCardList();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "2":
                myFileReader.printClientList();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "3":
                myFileReader.linkCardToClient();
                break;
        }
    }
}
