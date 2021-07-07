package com.z8q;

import com.z8q.mapping.MappingTest;
import com.z8q.menu.MenuLevels;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Application {
    public static void main(String[] args) {

        MenuLevels menuLevels = new MenuLevels();

        Scanner sc = new Scanner(System.in);
        MappingTest mappingTest = new MappingTest();
        mappingTest.gsonMapper();
        while (true) {
            menuLevels.mainMenu();
            String choose = sc.nextLine();

            switch (choose) {
                case "1":
                    menuLevels.addCard();
                    System.out.println("Карта сохранена");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    menuLevels.addClient();
                case "3":
                    menuLevels.secondMenu();

            }

        }
    }
}
