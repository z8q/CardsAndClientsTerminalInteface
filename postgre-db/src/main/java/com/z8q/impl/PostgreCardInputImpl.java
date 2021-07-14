package com.z8q.impl;

import com.google.gson.Gson;
import com.z8q.dto.CardDTO;
import com.z8q.interfaces.PostgreCardInput;
import com.z8q.model.Card;
import com.z8q.postgredb.ConnectFactory;
import com.z8q.properties.FormFactor;
import com.z8q.properties.MyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgreCardInputImpl implements PostgreCardInput {

    private static final Logger LOGGER = LogManager.getLogger(PostgreCardInputImpl.class);
    private static final String PATH_TO_CREATE_CARDS_TABLE = "postgre-db/src/main/resources/Cards.sql";
    private static final String INSERT_CARD = "INSERT INTO cards " +
            "(pan, form_factor, chip, pincode) VALUES (?, ?, ?, ?);";

    @Override
    public MyStatus save(Card card) {
        MyStatus status = new MyStatus();

        try (Connection connection = ConnectFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CARD))
        {
            LOGGER.info("Data was inserted into cards table");

            preparedStatement.setString(1, card.getCardNumberFirstFourDigits() +
                    card.getCardNumberSecondEightDigits() +
                    card.getCardNumberThirdFourDigits());
            preparedStatement.setString(2, String.valueOf(card.getFormFactor()));
            preparedStatement.setBoolean(3, card.isHasAChip());
            preparedStatement.setString(4, card.getPinCode());

            preparedStatement.executeUpdate();
            status.setStatus(true);
        } catch (SQLException e) {
            LOGGER.error("Data wasn't inserted into cards table");
            status.setStatus(false);
        }
        return status;
    }

    @Override
    public MyStatus createCardObject(CardDTO cardDTO) {
        MyStatus status = new MyStatus();
        Gson gson = new Gson();

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
            status.setStatus(true);
            System.out.println("Карта сохранена \n");
        } else {
            status.setStatus(false);
            status.setMessage("Error on createCardObject stage");
            LOGGER.error("Error on createCardObject stage");
        }
        return status;
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
