package com.example.demo.config.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

public class StatusParseEvent extends ApplicationEvent {

    private final String message;
    private final String platform;


    public StatusParseEvent(Object source, String message, String platform) {
        super(source);
        this.message = message;
        this.platform = platform;
    }

    public String getMessage() {
        return message;
    }

    public String getPlatform() {
        return platform;
    }
}
