package com.z8q.dto;

import com.z8q.models.Card;

public class CardDTO {
    private String pan;
    private String formFactor;
    private String chip;
    private String pinCode;

//    public CardDTO(){}

    public CardDTO(String pan, String formFactor, String chip, String pinCode) {
        this.pan = pan;
        this.formFactor = formFactor;
        this.chip = chip;
        this.pinCode = pinCode;
    }

    public String getPan() {
        return pan;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public String getChip() {
        return chip;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

}
