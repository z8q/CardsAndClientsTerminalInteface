package com.z8q.impl;

import com.z8q.dto.CardDTO;
import com.z8q.interfaces.CardInput;
import com.z8q.interfaces.CardOutput;
import com.z8q.model.Card;
import com.z8q.postgredb.CardsAndClientsTablesCreation;
import com.z8q.postgredb.ConnectFactory;
import com.z8q.properties.FormFactor;
import com.z8q.properties.MyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreCardInputImpl implements CardInput, CardOutput {

    private static final Logger LOGGER = LogManager.getLogger(PostgreCardInputImpl.class);
    private static final String PATH_TO_CREATE_CARDS_TABLE = "postgre-db/src/main/resources/Cards.sql";
    private static final String GET_CARD_BY_ID = "SELECT * FROM cards WHERE id = ?;";
    private static final String GET_ALL_CARDS = "SELECT * FROM cards;";
    private static final String INSERT_CARD = "INSERT INTO cards " +
            "(pan, form_factor, chip, pincode) VALUES (?, ?, ?, ?);";

    @Override
    public Card getCardById(Long cardIndex) {
        Card card = null;
        try (Connection connection = ConnectFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CARD_BY_ID)) {
            preparedStatement.setLong(1, cardIndex);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long rsCardId = rs.getLong("id");
                String rsPan = rs.getString("pan");
                String rsFormFactor = rs.getString("form_factor");
                boolean rsChip = rs.getBoolean("chip");
                String rsPin = rs.getString("pincode");

                card = new Card.Builder()
                        .withId(rsCardId)
                        .withCardNumberFirstFourDigits(rsPan.substring(0, 4))
                        .withCardNumberSecondEightDigits(rsPan.substring(4, 12))
                        .withCardNumberThirdFourDigits(rsPan.substring(12, 16))
                        .withFormFactor(FormFactor.valueOf(rsFormFactor))
                        .withHasAChip(rsChip)
                        .withPinCode(rsPin)
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get a Card by Id", e);
        }
        return card;
    }

    @Override
    public List<Card> getAll() {
        //  надо здесь dto использовать
        List<Card> cardList = new ArrayList<>();
        try (Connection connection = ConnectFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CARDS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long rsCardId = rs.getLong("id");
                String rsPan = rs.getString("pan");
                String rsFormFactor = rs.getString("form_factor");
                boolean rsChip = rs.getBoolean("chip");
                String rsPin = rs.getString("pincode");
                //Long rsLinkedClient = rs.getLong("client_id");

                Card card = new Card.Builder()
                        .withId(rsCardId)
                        .withCardNumberFirstFourDigits(rsPan.substring(0, 4))
                        .withCardNumberSecondEightDigits(rsPan.substring(4, 12))
                        .withCardNumberThirdFourDigits(rsPan.substring(12, 16))
                        .withFormFactor(FormFactor.valueOf(rsFormFactor))
                        .withHasAChip(rsChip)
                        .withPinCode(rsPin)
                        .build();
                cardList.add(card);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get list of Cards", e);
        }
       return cardList;
    }

    @Override
    public MyStatus createCardObject(CardDTO cardDTO) {
        MyStatus status = new MyStatus();

        startCreationOfCardTableIfNotExists();

        FormFactor formFactor = defineFormFactor(cardDTO);
        boolean hasChip = defineChip(cardDTO);

        Card card = new Card.Builder()
                .withCardNumberFirstFourDigits(cardDTO.getPan().substring(0, 4))
                .withCardNumberSecondEightDigits(cardDTO.getPan().substring(4, 12))
                .withCardNumberThirdFourDigits(cardDTO.getPan().substring(12, 16))
                .withFormFactor(formFactor)
                .withHasAChip(hasChip)
                .withPinCode(cardDTO.getPinCode())
                .build();

        MyStatus saveCard = save(card);
        if(saveCard.isStatus()) {
            System.out.println("Карта сохранена \n");
        } else {
            status.setMessage("Error on createCardObject stage");
            LOGGER.error("Error on createCardObject stage");
        }
        return status;
    }

    private void startCreationOfCardTableIfNotExists() {
        try {
            PostgreClientInputImpl client = new PostgreClientInputImpl();
            client.startCreationOfClientTableIfNotExists();
            CardsAndClientsTablesCreation.createTable(PATH_TO_CREATE_CARDS_TABLE);
        } catch (SQLException throwables) {
            LOGGER.warn("Table {} already exists",
                    PATH_TO_CREATE_CARDS_TABLE.substring(PATH_TO_CREATE_CARDS_TABLE.lastIndexOf("/")+1));
        }
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

    @Override
    public MyStatus save(Card card) {
        MyStatus status = new MyStatus();

        try (Connection connection = ConnectFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CARD))
        {
            preparedStatement.setString(1, card.getCardNumberFirstFourDigits() +
                    card.getCardNumberSecondEightDigits() +
                    card.getCardNumberThirdFourDigits());
            preparedStatement.setString(2, String.valueOf(card.getFormFactor()));
            preparedStatement.setBoolean(3, card.isHasAChip());
            preparedStatement.setString(4, card.getPinCode());

            preparedStatement.executeUpdate();
            LOGGER.info("Data was inserted into cards table");
            status.setStatus(true);
        } catch (SQLException e) {
            LOGGER.error("Data wasn't inserted into cards table");
            status.setStatus(false);
        }
        return status;
    }
}
