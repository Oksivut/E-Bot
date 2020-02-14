package com.company.managers;

import com.company.models.Expenses;

import java.util.ArrayList;
import java.util.List;

public class ExpensesManager {
    private static ExpensesManager  expensesManager = new ExpensesManager();
    private static List<Expenses> expenses = new ArrayList<>();

    private ExpensesManager ()
    {}
    public static synchronized List<Expenses> getExpenses() {
        return expenses;
    }
    public synchronized static ExpensesManager  ExpensesManagerSingl()
    {
        return expensesManager;
    }

}
