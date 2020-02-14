package com.company.uiutils;

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonTelegram {
    private ButtonTelegram() {
        // Required private constructor
    }

    public static List<List<InlineKeyboardButton>> Bottonnew (String nameone, String valueone,String nametwo, String valuetwo, String namethree, String valuethree) {
        InlineKeyboardButton inlineKeyboardButtonOne = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButtonTwo = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButtonThree = new InlineKeyboardButton();

        inlineKeyboardButtonOne.setText(nameone);
        inlineKeyboardButtonOne.setCallbackData(valueone);
        inlineKeyboardButtonTwo.setText(nametwo);
        inlineKeyboardButtonTwo.setCallbackData(valuetwo);
        inlineKeyboardButtonThree.setText(namethree);
        inlineKeyboardButtonThree.setCallbackData(valuethree);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();


        List<InlineKeyboardButton> rowone = new ArrayList<>();
        List<InlineKeyboardButton> rowtwo = new ArrayList<>();
        List<InlineKeyboardButton> rowtree = new ArrayList<>();

        rowone.add(inlineKeyboardButtonOne);
        rowtwo.add(inlineKeyboardButtonTwo);
        rowtree.add(inlineKeyboardButtonThree);
        keyboard.add(rowone);
        keyboard.add(rowtwo);
        keyboard.add(rowtree);

        return keyboard;
    }
}
