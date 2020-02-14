package com.company.managers;

import com.company.models.Revenues;

import java.util.ArrayList;
import java.util.List;

public class RevenuesManager {
    private static List<Revenues> revenues = new ArrayList<>();
    private static RevenuesManager revenuesManager = new RevenuesManager();

    private RevenuesManager ()
    {}
    public static synchronized List<Revenues> getRevenues() {
        return revenues;
    }
    public synchronized static RevenuesManager RevenuesManagerSingl ()
    {
        return revenuesManager;
    }
}
