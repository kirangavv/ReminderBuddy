package com.remainder.datamodels;

/**
 * Created by satish on 11/14/2015.
 */
public class Remainder {

    private int id;
    private String name;
    private String details;
    private String phone;
    private String email;
    private String date;
    private String type;
    private Boolean sendWishes;
    private String wishesDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSendWishes() {
        return sendWishes;
    }

    public void setSendWishes(Boolean sendWishes) {
        this.sendWishes = sendWishes;
    }

    public String getWishesDetails() {
        return wishesDetails;
    }

    public void setWishesDetails(String wishesDetails) {
        this.wishesDetails = wishesDetails;
    }
}
