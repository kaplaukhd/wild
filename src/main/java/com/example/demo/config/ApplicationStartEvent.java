package com.example.demo.config;

import com.example.demo.notify.Notification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartEvent implements ApplicationListener<ApplicationEvent> {

    private final Notification notification;

    @Override
    public void onApplicationEvent(@NonNull ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            notification.sendMessage("Бот запущен");
        }
    }
}
