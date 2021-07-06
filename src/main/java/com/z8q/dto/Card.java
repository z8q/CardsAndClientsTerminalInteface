package com.z8q.dto;

import com.z8q.cardpropeties.FormFactor;

public class Card {

    private String cardNumberFirstFourDigits;
    private String cardNumberSecondEightDigits;
    private String cardNumberThirdFourDigits;

    private FormFactor formFactor;
    private boolean hasAChip;
    private int pinCode;

    public Card(String cardNumberFirstFourDigits, String cardNumberSecondEightDigits, String cardNumberThirdFourDigits, FormFactor formFactor, boolean hasAChip, int pinCode) {
        this.cardNumberFirstFourDigits = cardNumberFirstFourDigits;
        this.cardNumberSecondEightDigits = cardNumberSecondEightDigits;
        this.cardNumberThirdFourDigits = cardNumberThirdFourDigits;
        this.formFactor = formFactor;
        this.hasAChip = hasAChip;
        this.pinCode = pinCode;
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

    public int getPinCode() {
        return pinCode;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumberFirstFourDigits='" + cardNumberFirstFourDigits + '\'' +
                ", cardNumberSecondEightDigits='" + cardNumberSecondEightDigits + '\'' +
                ", cardNumberThirdFourDigits='" + cardNumberThirdFourDigits + '\'' +
                ", formFactor=" + formFactor +
                ", hasAChip=" + hasAChip +
                ", pinCode=" + pinCode +
                '}';
    }
}
