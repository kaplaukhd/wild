package com.example.demo.notify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    public void sendMessage(String message) {
        try {
            execute(new SendMessage(chatId, message));
        } catch (TelegramApiException var) {
            log.error(var.getMessage());
        }
    }
}
