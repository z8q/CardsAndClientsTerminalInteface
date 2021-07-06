package com.z8q.fileoperations;

import java.io.*;
import java.util.Scanner;

public class MyFileReader {

    private Scanner sc = new Scanner(System.in);


    public void printCardList() {

        try (FileReader reader = new FileReader("src/main/resources/CardList.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void printClientList() {
        try (FileReader reader = new FileReader("src/main/resources/ClientList.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void linkCardToClient() {
        System.out.println("Введите последние 4 цифры номера карты, которую хотите привязать");
        String last4Digits = sc.nextLine();
        System.out.println("Введите фамилию человека, которому хотите привязать карту");
        String surname = sc.nextLine();

        findStringFile(surname, "src/main/resources/ClientList.txt");


    }
    // !!!!!!!
    public static void findStringFile(String findSurname, String pathFile) {

                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(pathFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                StringBuilder inputString = new StringBuilder();
                String line;

                while (true) {
                    try {
                        assert br != null;
                        if ((line = br.readLine()) == null) break;
                        if (line.contains(findSurname)) {
                            line = "Client{lastName='Петров', firstName='Петр', middleName='Петрович', birthDate=10/03/1980, clientCards=[8818]}";
                            inputString.append(line);
                            inputString.append('\n');
                        }
                        FileOutputStream fileOut = new FileOutputStream("src/main/resources/ClientList.txt");
                        fileOut.write(inputString.toString().getBytes());
                        fileOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            assert br != null;
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
    }

}


