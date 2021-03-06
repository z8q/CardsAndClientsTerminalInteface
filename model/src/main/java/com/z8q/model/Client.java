package com.z8q.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Client {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthDate;

    private List<String> clientCards = new ArrayList<>();

    private Client() {}

    public Long getId() {
        return id;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public List<String> getClientCards() {
        return clientCards;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "Client{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + dateFormat.format(birthDate) +
                ", clientCards=" + clientCards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(lastName, client.lastName) && Objects.equals(firstName, client.firstName) && Objects.equals(middleName, client.middleName) && Objects.equals(birthDate, client.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, middleName, birthDate);
    }

    public static class Builder {
        private final Client newClient;

        public Builder() {
            newClient = new Client();
        }
        public Builder withId(Long id) {
            newClient.id = id;
            return this;
        }
        public Builder withLastName(String lastName) {
            newClient.lastName = lastName;
            return this;
        }
        public Builder withFirstName(String firstName) {
            newClient.firstName = firstName;
            return this;
        }
        public Builder withMiddleName(String middleName) {
            newClient.middleName = middleName;
            return this;
        }
        public Builder withBirthDate(Date birthDate) {
            newClient.birthDate = birthDate;
            return this;
        }
        public Builder withClientCards(List<String> clientCards) {
            newClient.clientCards = clientCards;
            return this;
        }
        public Client build() {
            return newClient;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return Objects.equals(newClient, builder.newClient);
        }

        @Override
        public int hashCode() {
            return Objects.hash(newClient);
        }
    }
}
