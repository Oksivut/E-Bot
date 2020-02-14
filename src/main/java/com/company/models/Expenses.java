package com.company.models;

public class Expenses {
    private String category;
    private String name;
    private String money;
    private String date;

    public Expenses(String category, String name, String money, String date) {
        this.category = category;
        this.name = name;
        this.money = money;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getMoney() {
        return money;
    }

    public String getDate() {
        return date;
    }
}