package com.example.demo.config.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CustomEventPublisher {

    private final ApplicationEventPublisher publisher;

    public CustomEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
    public void publishCustomEvent(final String message, final String platform) {
        System.out.println("Publishing custom event. ");
        StatusParseEvent customSpringEvent = new StatusParseEvent(this, message, platform);
        publisher.publishEvent(customSpringEvent);
    }
}