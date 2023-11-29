package com.sachuk.keu.services.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sachuk.keu.entities.payload.MessageViaMessenger;
import com.sachuk.keu.entities.payload.NotificationRequest;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessengerNotificationService {
    public void sendNotification(String phoneNumber, String text) {
        MessageViaMessenger message = new MessageViaMessenger();
        message.setReceiverPhone(phoneNumber.substring(1));
        message.setMessage(text);

        NotificationRequest request = new NotificationRequest();
        request.setMessage(message);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(json);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request req = new Request.Builder()
                .url("http://localhost:8080/api/v1/message/send")
                .method("POST", body)
//                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYW55YSIsImlhdCI6MTcwMTIxNTg2MSwiZXhwIjoxNzAxMzAyMjYxfQ.O9Rey18W_2q6-m-0gZ_WMalYtvm1SRuua63RH2hhJo9Ym4nDccScoFBt5ktATHN4kTNW0gDsmfXnfg7oesQIgQ")
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(req).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
