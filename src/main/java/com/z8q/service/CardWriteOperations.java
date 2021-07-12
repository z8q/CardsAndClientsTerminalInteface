package com.z8q.service;

import com.google.gson.Gson;
import com.z8q.propeties.FormFactor;
import com.z8q.interfaces.CardIO;
import com.z8q.models.Card;
import com.z8q.propeties.MyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardWriteOperations implements CardIO {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CARDPATH = "src/main/resources/CardList.txt";
    private Long cardId = 0L;

    @Override
    public void getCardById(Card card) {

    }

    @Override
    public void getAll() {
        try {
            String content = Files.lines(Paths.get(CARDPATH)).reduce("", String::concat);
            Gson gson = new Gson();
            List<Card> printCardList = gson.fromJson(content, ArrayList.class);
            for (int i = 0; i < printCardList.size(); i++) {
                System.out.println(printCardList.get(i));
            }
        } catch (IOException e) {
            LOGGER.error("Wrong path to file or Wrong JSON syntax");
            e.printStackTrace();
        }
    }

    @Override
    public MyStatus save(Card card) {
        MyStatus status = new MyStatus();
        try {
            LOGGER.info("Card with id {} was added to file", card.getId());

            //String contentCards = Files.lines(Paths.get(CARDPATH)).reduce("", String::concat);
            Scanner sc = new Scanner(new File(CARDPATH));
            String contentCards = sc.nextLine();
            Gson gsonCards = new Gson();
            List<Card> cardArray = gsonCards.fromJson(contentCards, ArrayList.class);
            if (cardArray != null) {
                cardArray.add(card);
                String contentCardsNew = gsonCards.toJson(cardArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(CARDPATH));
                writer.write(contentCardsNew);
                writer.close();
            } else {
                List<Card> tempCardArray = new ArrayList<>();
                tempCardArray.add(card);
                String humansString = gsonCards.toJson(tempCardArray);
                BufferedWriter writer = new BufferedWriter(new FileWriter(CARDPATH));
                writer.write(humansString);
                writer.close();
            }
            status.setStatus(true);
            return status;
        } catch (IOException e) {
            LOGGER.error("Error was occurred while saving Client with id {}", card.getId());
            e.printStackTrace();
            status.setStatus(false);
            status.setMessage("Error on Card save stage");
            return status;
        }
    }

    @Override
    public MyStatus createCardObject(String cardNumber16DigitsInput, String formFactor, String isHasAChipArg, String pinInput) {
        MyStatus status = new MyStatus();
        try {
            //String path = "src/main/resources/CardList.txt";
            //String contentCards = Files.lines(Paths.get(path)).reduce("", String::concat);
            Scanner sc = new Scanner(new File(CARDPATH));
            String contentCards = sc.nextLine();
            Gson gsonCards = new Gson();
            List<Card> cardArray = gsonCards.fromJson(contentCards, ArrayList.class);
            if (cardArray == null) {
                cardId = 1L;
            } else {
                cardId = (long) (cardArray.size() + 1);
            }
        } catch (IOException e) {
            LOGGER.error("Error while creating a Card");
            e.printStackTrace();
            status.setStatus(false);
            return status;
        }
        FormFactor formEnum;
        if (formFactor.equals("REAL")) {
            formEnum = FormFactor.REAL;
        } else {
            formEnum = FormFactor.VIRTUAL;
        }

        boolean hasChip = false;
        if (isHasAChipArg.equals("yes")) {
            hasChip = true;
        }
        Card card = new Card.Builder()
                .withId(cardId)
                .withCardNumberFirstFourDigits(cardNumber16DigitsInput.substring(0, 4))
                .withCardNumberSecondEightDigits(cardNumber16DigitsInput.substring(4, 12))
                .withCardNumberThirdFourDigits(cardNumber16DigitsInput.substring(12, 16))
                .withFormFactor(formEnum)
                .withHasAChip(hasChip)
                .withPinCode(pinInput)
                .build();

        save(card);
        System.out.println("Карта сохранена \n");
        status.setStatus(true);
        return status;
    }
}
