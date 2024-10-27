package com.web2_lab1.TicketsApp.domain;

public class TicketDTO {

    private String vatin;
    private String firstName;
    private String lastName;

    public TicketDTO(String vatin, String firstName, String lastName) {
        this.vatin = vatin;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getVatin() {
        return vatin;
    }

    public void setVatin(String vatin) {
        this.vatin = vatin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
