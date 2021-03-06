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

    private static final Logger LOGGER = LogManager.getLogger(CardInputImpl.class);
    private static final String CARDPATH = "file-db/src/main/resources/CardList.txt";

    @Override
    public Card getCardById(Long cardIndex) {
        List<Card> cardArray = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(CARDPATH));
            String contentCards = sc.nextLine();
            Gson gsonCards = new Gson();
            cardArray = gsonCards.fromJson(contentCards, ArrayList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
            LOGGER.error("Wrong path to file or Wrong JSON syntax", e);
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
            LOGGER.error("Error was occurred while saving Client with id {}", card.getId(), e);

            status.setStatus(false);
            status.setMessage("Error on Card save stage" + e.getMessage());
            return status;
        }
    }

    private List<Card> saveTxtIntoList(Gson gsonCards) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(CARDPATH));
        List<Card> cardArray = null;
        while(sc.hasNext()) {
            String contentCards = sc.nextLine();
            cardArray = gsonCards.fromJson(contentCards, ArrayList.class);
        }
        return cardArray;
    }

    private String addElementToList(List<Card> cardArray, Card card, Gson gsonCards) {
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
        Gson gsonCards = new Gson();

        List<Card> cardArray = fromJSONToList(gsonCards);
        Long cardId = defineCardId(cardArray);
        FormFactor formFactor = defineFormFactor(cardDTO);
        boolean hasChip = defineChip(cardDTO);

        Card card = new Card.Builder()
                .withId(cardId)
                .withCardNumberFirstFourDigits(cardDTO.getPan().substring(0, 4))
                .withCardNumberSecondEightDigits(cardDTO.getPan().substring(4, 12))
                .withCardNumberThirdFourDigits(cardDTO.getPan().substring(12, 16))
                .withFormFactor(formFactor)
                .withHasAChip(hasChip)
                .withPinCode(cardDTO.getPinCode())
                .build();
        MyStatus status = save(card);
        if(status.isStatus()) {
            status.setStatus(true);
            System.out.println("Card was saved \n");
        } else {
            status.setStatus(false);
            status.setMessage("Error on createCardObject stage");
            LOGGER.error("Error on createCardObject stage, card id {}", cardId);
        }
        return status;
    }

    private List<Card> fromJSONToList(Gson gsonCards) {
        String contentCards = "";
        try {
            Scanner sc = new Scanner(new File(CARDPATH));
            while (sc.hasNext()) {
                contentCards = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Can't create a cardList while reading JSON");
        }
        return gsonCards.fromJson(contentCards, ArrayList.class);
    }

    private Long defineCardId(List<Card> cardArray) {
        Long cardIdTemp;
        if (cardArray == null) {
            cardIdTemp = 1L;
        } else {
            cardIdTemp = (long) (cardArray.size() + 1);
        }
        return cardIdTemp;
    }

    private FormFactor defineFormFactor(CardDTO cardDTO) {
        FormFactor formFactorTemp;
        if (cardDTO.getFormFactor().equals("1")) {
            formFactorTemp = FormFactor.REAL;
        } else {
            formFactorTemp = FormFactor.VIRTUAL;
        }
        return formFactorTemp;
    }

    private boolean defineChip(CardDTO cardDTO) {
        return cardDTO.getChip().equals("yes");
    }
}
