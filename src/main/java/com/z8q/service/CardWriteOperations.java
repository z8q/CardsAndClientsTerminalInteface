package com.z8q.service;

import com.google.gson.Gson;
import com.z8q.interfaces.CardInterface;
import com.z8q.models.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CardWriteOperations implements CardInterface {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CARDPATH = "src/main/resources/CardList.txt";

    @Override
    public void getCardById(Card card) {

    }

    @Override
    public void getAll(Card card) {

    }

    @Override
    public void save(Card card) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CARDPATH))) {
            LOGGER.info("Card with id {} was added to file", card.getId());

            String contentCards = Files.lines(Paths.get(CARDPATH)).reduce("", String::concat);
            Gson gsonCards = new Gson();
            List<Card> cardArray = gsonCards.fromJson(contentCards, ArrayList.class);

            String dataToWrite;
            if (cardArray != null) {
                cardArray.add(card);
                dataToWrite = gsonCards.toJson(cardArray);

            } else {
                List<Card> tempCardArray = new ArrayList<>();
                tempCardArray.add(card);
                dataToWrite = gsonCards.toJson(tempCardArray);

            }

            writer.write(dataToWrite);
        } catch (IOException e) {
            LOGGER.error("Error was occurred while saving Card with id {}", card.getId());
            e.printStackTrace();
        }
    }
}
