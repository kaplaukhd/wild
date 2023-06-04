package com.example.demo.notify;

import com.example.demo.entities.entity.search.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class Notification extends TelegramLongPollingBot {

    @Value(value = "${TELEGRAM_CHATID}")
    private  String chatId;

    @Value(value = "${TELEGRAM_USERNAME}")
    private  String botUsername;

    @Value(value = "${TELEGRAM_TOKEN}")
    private String token;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(new SendMessage(update.getMessage().getChatId().toString(), "За доступом к парсеру\n\n@fedukoneeevol"));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message, String url) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        if (url != null) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setUrl(url);
            inlineKeyboardButton.setText("Открыть");
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

    public void sendProduct(Map<Boolean, List<Product>> products) {
        products.forEach(this::sendProduct);
    }

    public void sendProduct(Boolean kind, List<Product> products) {
        String vector = kind ?  "\uD83D\uDD34" : "🟢";

        products.forEach(x -> sendMessage(
                String.format("""

                Новая цена! %s\s

                %s

                Цена:  %d --> %d""", vector, x.getName(), x.getOldPrice(), x.getSalePriceU()), String.format("https://www.wildberries.ru/catalog/%s/detail.aspx", x.getId().toString())
        ));
    }

}
