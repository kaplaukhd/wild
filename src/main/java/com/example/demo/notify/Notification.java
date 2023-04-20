package com.example.demo.notify;

import com.example.demo.entities.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@Slf4j
public class Notification extends TelegramLongPollingBot {

    private static final String chatId = "-1001886045503";


    @Override
    public String getBotUsername() {
        return "Wb_checkers_bot";
    }

    @Override
    public String getBotToken() {
        return "6068122340:AAHVGJ4QNKpC5nJtvBX0oFSXhURUZE3mY54";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void sendMessage(String message, String url) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        if (url != null) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setUrl(url);
            inlineKeyboardButton.setText("Трахнуть");
            List<InlineKeyboardButton> list = List.of(inlineKeyboardButton);
            List<List<InlineKeyboardButton>> btns = List.of(list);
            inlineKeyboardMarkup.setKeyboard(btns);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
        try {
            execute(sendMessage);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException thread) {
                log.error(thread.getMessage());
            }
        } catch (TelegramApiException var) {
            try {
                Thread.sleep(39000);
            } catch (InterruptedException threadOne) {
                log.error(threadOne.getMessage());
            }
            log.error(var.getMessage());
        }
    }

    public void sendProduct(List<Product> products) {
        products.forEach(this::sendProduct);
    }

    public void sendProduct(Product product) {
        String url = String.format("https://www.wildberries.ru/catalog/%s/detail.aspx", product.getId().toString());
        sendMessage(String.format("""

                Новая цена! 🟢

                %s

                Цена:  %d""", product.getName(), product.getSalePriceU()), url);

    }

}
