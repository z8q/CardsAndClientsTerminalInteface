package com.z8q.models;

import com.z8q.propeties.FormFactor;

import java.util.Objects;

public class Card {

    private Long id;
    private String cardNumberFirstFourDigits;
    private String cardNumberSecondEightDigits;
    private String cardNumberThirdFourDigits;

    private FormFactor formFactor;
    private boolean hasAChip;
    private String pinCode;

    private Card(){}

    public Long getId() {
        return id;
    }

    public String getCardNumberFirstFourDigits() {
        return cardNumberFirstFourDigits;
    }

    public String getCardNumberSecondEightDigits() {
        return cardNumberSecondEightDigits;
    }

    public String getCardNumberThirdFourDigits() {
        return cardNumberThirdFourDigits;
    }

    public FormFactor getFormFactor() {
        return formFactor;
    }

    public boolean isHasAChip() {
        return hasAChip;
    }

    public String getPinCode() {
        return pinCode;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumberFirstFourDigits='" + cardNumberFirstFourDigits + '\'' +
                ", cardNumberSecondEightDigits='" + cardNumberSecondEightDigits + '\'' +
                ", cardNumberThirdFourDigits='" + cardNumberThirdFourDigits + '\'' +
                ", formFactor=" + formFactor +
                ", hasAChip=" + hasAChip +
                ", pinCode=" + pinCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return hasAChip == card.hasAChip && pinCode == card.pinCode && Objects.equals(id, card.id) && Objects.equals(cardNumberFirstFourDigits, card.cardNumberFirstFourDigits) && Objects.equals(cardNumberSecondEightDigits, card.cardNumberSecondEightDigits) && Objects.equals(cardNumberThirdFourDigits, card.cardNumberThirdFourDigits) && formFactor == card.formFactor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumberFirstFourDigits, cardNumberSecondEightDigits, cardNumberThirdFourDigits, formFactor, hasAChip, pinCode);
    }

    public static class Builder {
        private final Card newCard;

        public Builder() {
            newCard = new Card();
        }
        public Builder withId(Long id) {
            newCard.id = id;
            return this;
        }
        public Builder withCardNumberFirstFourDigits(String cardNumberFirstFourDigits) {
            newCard.cardNumberFirstFourDigits = cardNumberFirstFourDigits;
            return this;
        }
        public Builder withCardNumberSecondEightDigits(String cardNumberSecondEightDigits) {
            newCard.cardNumberSecondEightDigits = cardNumberSecondEightDigits;
            return this;
        }
        public Builder withCardNumberThirdFourDigits(String cardNumberThirdFourDigits) {
            newCard.cardNumberThirdFourDigits = cardNumberThirdFourDigits;
            return this;
        }
        public Builder withFormFactor(FormFactor formFactor) {
            newCard.formFactor = formFactor;
            return this;
        }
        public Builder withHasAChip(boolean hasAChip) {
            newCard.hasAChip = hasAChip;
            return this;
        }
        public Builder withPinCode(String pinCode) {
            newCard.pinCode = pinCode;
            return this;
        }
        public Card build() {
            return newCard;
        }
    }
}
