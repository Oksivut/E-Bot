package com.company.utils;

public class Utils {
    private Utils() {

    }

    public static boolean isNumeric(String text) {

        double dbl;
        try {
            dbl = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            dbl = 0;
        }
        return dbl > 0;
    }

    public static boolean isDate(String text) { // переробити
        int day;
        int month;
        int year;
            try {

                String[] array = text.split("\\.");
                if (array.length == 3) {
                    day = Integer.parseInt(array[0]);
                    month = Integer.parseInt(array[1]);
                    year = Integer.parseInt(array[2]);
                    if (day > 31 || month > 12 || month < 0 || year > 9999 || year < 1000) {
                        day = 0;
                    }
                }  else {day = 0;}
            } catch (NumberFormatException e) {
                day = 0;
            }
        return day > 0;
    }
}


