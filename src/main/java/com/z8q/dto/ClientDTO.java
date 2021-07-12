package com.z8q.dto;

public class ClientDTO {
    private String lastname;
    private String firstname;
    private String middlename;
    private String date;

    // public ClientDTO() {}

    public ClientDTO(String lastname, String firstname, String middlename, String date) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.date = date;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getDate() {
        return date;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
