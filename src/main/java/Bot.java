

import com.company.forEnum.IndexMenu;
import com.company.managers.ExpensesManager;
import com.company.managers.RevenuesManager;
import com.company.models.Expenses;
import com.company.models.Revenues;
import com.company.uiutils.ButtonTelegram;
import com.company.utils.Utils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot extends TelegramLongPollingBot {

    private static final String botToken = "1038691897:AAFc3FyyLTGH9UME2TGVwEMQYb6j2xKQv7A";
    private static final String botUsername = "ExpensesSuperBot";

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private String categoryAdd = " ";
    private String nameAdd = " ";
    private String moneyAdd = " ";
    private String dateAdd = " ";
    private String nameRemoveE = " ";
    private String moneyRemoveE = " ";
    private String dateRemoveE = " ";
    private String finishAction = "";
    IndexMenu indexMenu;



    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            processMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            processCallBack(update.getCallbackQuery());

        }
    }

    private void processCallBack(CallbackQuery callbackQuery) {
        String callback = callbackQuery.getData();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());//
        if (callback.equals("expenses")) {
            // затраты
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(com.company.uiutils.ButtonTelegram.Bottonnew("Add", "addE", "Change", "viewE", "Remove", "removeE"));
            sendMessage.setText("Expenses");
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        } else if (callback.equals("revenues")) {
            // доходы
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(com.company.uiutils.ButtonTelegram.Bottonnew("Add", "addR", "Change", "viewR", "Remove", "removeR"));
            sendMessage.setText("Revenues");
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        } else if (callback.equals("addE")) { // меню затраты
            indexMenu = IndexMenu.AddCategoryExpenses;
            sendMessage.setText("to select category of expenses ");
        } else if (callback.equals("viewE")) {
            String value = "Expenses";
            for (Expenses u : ExpensesManager.getExpenses()) {
                String category = u.getCategory();
                String name = u.getName();
                String money = u.getMoney() + "";
                String date = u.getDate();
                value = value + "\n" + "Category - " + category + "; " + "Name  - " + name + "; " + "Cost - " + money + "$; " + "Date - " + date + ".";

            }
            sendMessage.setText(value);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(com.company.uiutils.ButtonTelegram.Bottonnew("Expenses", "expenses", "Revenues", "revenues", "Balance", "balance"));
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        } else if (callback.equals("removeE")) {
            indexMenu = IndexMenu.RemoveExpensesName;
            sendMessage.setText("To choose category of expenses for removing");
        } else if (callback.equals("addR")) {  // доходы меню
            indexMenu = IndexMenu.AddCategoryRevenues;
            sendMessage.setText("To write source of revenue");

        } else if (callback.equals("viewR")) {
            String value = "Revenues";
            for (Revenues u : RevenuesManager.getRevenues()) {
                String name = u.getName();
                String type = u.getType();
                String many = u.getMoney() + "";
                String date = u.getDate();
                value = value + "\n" + "Name  - " + name + "; " + "Type" + type + "; " + "Money - " + many + "$; " + "Date - " + date + ".";
            }
            sendMessage.setText(value);
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(ButtonTelegram.Bottonnew("Expenses", "expenses", "Revenues", "revenues", "Balance", "balance"));
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        } else if (callback.equals("removeR")) {
            indexMenu = IndexMenu.RemoveRevenuesName;
            sendMessage.setText("To choose category of expenses for removing");
        } else if (callback.equals("balance")) {
            sendMessage.setText("Balance!");
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void processMessage(Message message) {
        String text = message.getText();
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage();


        if (text.equalsIgnoreCase("/start")) {
            indexMenu = IndexMenu.Start;
            finishAction = "Hello. ";
            menu(text, chatId, sendMessage);
        } else if (!(indexMenu == null)) {
            menu(text, chatId, sendMessage);
            sendMessage.setChatId(chatId);
        } else {
            indexMenu = IndexMenu.DontFound;
            menu(text, chatId, sendMessage);
        }
        if ((indexMenu == IndexMenu.Menu)) {
            menu(text, chatId, sendMessage);
            sendMessage.setChatId(chatId);
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void menu(String text, long chatId, SendMessage sendMessage) {
        if (!(indexMenu == null)) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            switch (indexMenu) {
                case Menu:
                    sendMessage.setText(finishAction + "To choose category of expenses");
                    finishAction = " ";
                    sendMessage.setChatId(chatId);
                    inlineKeyboardMarkup.setKeyboard(ButtonTelegram.Bottonnew("Expenses", "expenses", "Revenues", "revenues", "Balance", "balance"));
                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                    break;
                case DontFound:
                    sendMessage.setText("Wrong command");
                    sendMessage.setChatId(chatId);
                    sendMessage.setChatId(chatId);
                    inlineKeyboardMarkup.setKeyboard(ButtonTelegram.Bottonnew("Expenses", "expenses", "Revenues", "revenues", "Balance", "balance"));
                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                    break;
                case AddCategoryExpenses:
                    categoryAdd = text;
                    sendMessage.setText("To write tne category of expenses");
                    indexMenu = IndexMenu.AddNameExpenses;
                    break;
                case AddNameExpenses:
                    nameAdd = text;
                    sendMessage.setText("To write the sum,  through the point ");
                    indexMenu = IndexMenu.AddCostExpenses;
                    break;
                case AddCostExpenses:
                    if (Utils.isNumeric(text)) {
                        moneyAdd = text;
                        sendMessage.setText("To write the date, through the point, in format __.__.____");
                        indexMenu = IndexMenu.AddDateExpenses;
                        break;
                    } else {
                        sendMessage.setText("Incorrect sum");
                        indexMenu = IndexMenu.AddCostExpenses;
                        break;
                    }
                case AddDateExpenses:
                    if (Utils.isDate(text)) {
                        dateAdd = text;
                        ExpensesManager.getExpenses().add(new Expenses(categoryAdd, nameAdd, moneyAdd, dateAdd));
                        sendMessage.setText("Expense is added");
                        finishAction = "Expense is added ";
                        indexMenu = IndexMenu.Menu;
                        break;
                    } else {
                        sendMessage.setText("Incorrect date");
                        indexMenu = IndexMenu.AddDateExpenses;
                        break;
                    }
                case AddCategoryRevenues:
                    categoryAdd = text;
                    sendMessage.setText("To write the category of revenues");
                    indexMenu = IndexMenu.AddNameRevenues;
                    break;
                case AddNameRevenues:
                    nameAdd = text;
                    sendMessage.setText("To write the sum");
                case AddCostRevenues:
                    if (Utils.isNumeric(text)) {
                        moneyAdd = text;
                        sendMessage.setText("To write the date, through the point, in format __.__.____");
                        indexMenu = IndexMenu.AddDateRevenues;
                        break;
                    } else {
                        sendMessage.setText("Incorrect sum");
                        indexMenu = IndexMenu.AddCostRevenues;
                        break;
                    }
                case AddDateRevenues:
                    if (Utils.isDate(text)) {
                        dateAdd = text;
                        RevenuesManager.getRevenues().add(new Revenues(categoryAdd, nameAdd, moneyAdd, dateAdd));
                        sendMessage.setText("Revenue is added");
                        finishAction = "Revenue is added. ";
                        indexMenu = IndexMenu.Menu;
                        break;
                    } else {
                        sendMessage.setText("Incorrect date");
                        indexMenu = IndexMenu.AddDateRevenues;
                        break;
                    }
                case RemoveExpensesName:
                    nameRemoveE = text;
                    sendMessage.setText("to write the cost of revenue for removing");
                    indexMenu = IndexMenu.RemoveExpensesCost;
                    break;
                case RemoveExpensesCost:
                    moneyRemoveE = text;
                    sendMessage.setText("To write the year bought");
                    indexMenu = IndexMenu.RemoveExpensesDate;
                    break;
                case RemoveExpensesDate:
                    dateRemoveE = text;
                    for (int i = 0; i < ExpensesManager.getExpenses().size(); i++) {
                        if (ExpensesManager.getExpenses().get(i).getName().equalsIgnoreCase(nameRemoveE)) {
                            if (ExpensesManager.getExpenses().get(i).getMoney().equalsIgnoreCase(moneyRemoveE)) {
                                if (ExpensesManager.getExpenses().get(i).getDate().equalsIgnoreCase(dateRemoveE)) {
                                    ExpensesManager.getExpenses().remove(i);
                                    sendMessage.setText("Removed ");
                                    indexMenu = IndexMenu.Menu;
                                    finishAction = "Removed ";
                                    break;
                                }
                            }
                        }
                        if (i == ExpensesManager.getExpenses().size() - 1) {
                            sendMessage.setText("Nothing found'");
                            finishAction = "Nothing found";
                            indexMenu = IndexMenu.Menu;
                        }
                    }
                    break;
                case RemoveRevenuesName:
                    nameRemoveE = text;
                    sendMessage.setText("To write the cost of revenue for removing");
                    indexMenu = IndexMenu.RemoveRevenuesCost;
                    break;
                case RemoveRevenuesCost:
                    moneyRemoveE = text;
                    sendMessage.setText("To write the year bought");
                    indexMenu = IndexMenu.RemoveRevenuesDate;
                    break;
                case RemoveRevenuesDate:
                    dateRemoveE = text;
                    for (int i = 0; i < RevenuesManager.getRevenues().size(); i++) {
                        if (RevenuesManager.getRevenues().get(i).getName().equalsIgnoreCase(nameRemoveE)) {
                            if (RevenuesManager.getRevenues().get(i).getMoney().equalsIgnoreCase(moneyRemoveE)) {
                                if (RevenuesManager.getRevenues().get(i).getDate().equalsIgnoreCase(dateRemoveE)) {
                                    RevenuesManager.getRevenues().remove(i);
                                    indexMenu = IndexMenu.Menu;
                                    finishAction = "Removed ";
                                    break;
                                }
                            }
                        }
                        if (i == RevenuesManager.getRevenues().size() - 1) {
                            finishAction = "Nothing found ";
                            indexMenu = IndexMenu.Menu;
                        }
                    }

                    break;
            }
        }
    }
}

