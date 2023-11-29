package com.sachuk.keu.entities.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class NotificationRequest {
    private MessageViaMessenger message;
    private List<String> messengerPriority = Arrays.asList("WHATSAPP", "TELEGRAM");
}
