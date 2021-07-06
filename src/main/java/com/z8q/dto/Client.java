package com.z8q.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Client {

    private String lastName;
    private String firstName;
    private String middleName;

    private Calendar birthDate;

    private List<Card> clientCards = new ArrayList<>();

    public Client() {}

    public Client(String lastName, String firstName, String middleName, Calendar birthDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
    }

    public Client(String lastName, String firstName, String middleName, Calendar birthDate, List<Card> clientCards) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.clientCards = clientCards;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public List<Card> getClientCards() {
        return clientCards;
    }
    public void addToClientCards(Card card) {
        clientCards.add(card);
    }

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String y = df.format(getBirthDate().getTime());
        return "Client{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + y +
                ", clientCards=" + clientCards +
                '}';
    }
}
