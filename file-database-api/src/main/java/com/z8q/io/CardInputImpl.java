package com.z8q.io;

import com.google.gson.Gson;
import com.z8q.dto.CardDTO;
import com.z8q.interfaces.CardInput;
import com.z8q.interfaces.CardOutput;
import com.z8q.model.Card;
import com.z8q.properties.FormFactor;
import com.z8q.properties.MyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardInputImpl implements CardInput, CardOutput {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CARDPATH = "file-database-api/src/main/resources/CardList.txt";

    @Override
    public Card getCardById(Long cardIndex) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(CARDPATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String contentCards = sc.nextLine();
        Gson gsonCards = new Gson();
        List<Card> cardArray = gsonCards.fromJson(contentCards, ArrayList.class);
        return cardArray.get(cardIndex.intValue());
    }

    @Override
    public List<Card> getAll() {
        List<Card> printCardList = null;
        try {
            Scanner sc = new Scanner(new File(CARDPATH));
            Gson gson = new Gson();
            while(sc.hasNext()) {
                String content = sc.nextLine();
                printCardList = gson.fromJson(content, ArrayList.class);
            }
        } catch (IOException e) {
            LOGGER.error("Wrong path to file or Wrong JSON syntax");
            e.printStackTrace();
        }
        return printCardList;
    }
    @Override
    public MyStatus save(Card card) {
        MyStatus status = new MyStatus();
        try {
            LOGGER.info("Card with id {} was added to file", card.getId());
            Gson gsonCards = new Gson();
            List<Card> cardArray = saveTxtIntoList(gsonCards);
            String contentCardsNew = addElementToList(cardArray, card, gsonCards);

            BufferedWriter writer = new BufferedWriter(new FileWriter(CARDPATH));
            writer.write(contentCardsNew);
            writer.close();

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
    public List<Card> saveTxtIntoList(Gson gsonCards) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(CARDPATH));
        List<Card> cardArray = null;
        while(sc.hasNext()) {
            String contentCards = sc.nextLine();
            cardArray = gsonCards.fromJson(contentCards, ArrayList.class);
        }
        return cardArray;
    }
    public String addElementToList(List<Card> cardArray, Card card, Gson gsonCards) {
        String contentCardsNew;
        if (cardArray != null) {
            cardArray.add(card);
            contentCardsNew = gsonCards.toJson(cardArray);
        } else {
            List<Card> tempCardArray = new ArrayList<>();
            tempCardArray.add(card);
            contentCardsNew = gsonCards.toJson(tempCardArray);
        }
        return contentCardsNew;
    }

    @Override
    public MyStatus createCardObject(CardDTO cardDTO) {
        MyStatus status = new MyStatus();
        Long cardId;
        try {
            Scanner sc = new Scanner(new File(CARDPATH));
            String contentCards = null;
            while (sc.hasNext()) {
                contentCards = sc.nextLine();
            }
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
        if (cardDTO.getFormFactor().equals("REAL")) {
            formEnum = FormFactor.REAL;
        } else {
            formEnum = FormFactor.VIRTUAL;
        }
        boolean hasChip = false;
        if (cardDTO.getChip().equals("yes")) {
            hasChip = true;
        }
        Card card = new Card.Builder()
                .withId(cardId)
                .withCardNumberFirstFourDigits(cardDTO.getPan().substring(0, 4))
                .withCardNumberSecondEightDigits(cardDTO.getPan().substring(4, 12))
                .withCardNumberThirdFourDigits(cardDTO.getPan().substring(12, 16))
                .withFormFactor(formEnum)
                .withHasAChip(hasChip)
                .withPinCode(cardDTO.getPinCode())
                .build();
        if(save(card).isStatus()) {
            status.setStatus(true);
            System.out.println("Карта сохранена \n");
        } else {
            status.setStatus(false);
        }
        return status;
    }
}
