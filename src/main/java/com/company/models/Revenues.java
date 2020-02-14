package com.company.models;

public class Revenues {
    private String name;
    private String type;
    private String money;
    private String date;


    public Revenues (String name, String type, String money, String date) {
        this.name = name;
        this.type = type;
        this.money = money;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMoney() {
        return money;
    }

    public String getDate() {
        return date;
    }
}
