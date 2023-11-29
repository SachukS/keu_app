package com.sachuk.keu.services.dia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUser;
import com.sachuk.keu.controllers.rest.dto.dia.DiaBranchDTO;
import com.sachuk.keu.controllers.rest.dto.dia.DiaDeeplinkDTO;
import com.sachuk.keu.controllers.rest.dto.dia.DiaOfferDTO;
import com.sachuk.keu.controllers.rest.dto.dia.DiaSessionDTO;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
public class DiaDocumentsService { // Документи - will be provided in future
    private final String DIA_API_URL = "http://localhost:8081/dia/test-api"; //https://api2s.diia.gov.ua/api

    public String getCreateSessionAndGetToken(String acquirer_token) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(DIA_API_URL + "/v1/auth/acquirer/" + acquirer_token)
                .addHeader("Authorization", "Basic " + acquirer_token)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            DiaSessionDTO session = objectMapper.readValue(responseBody, DiaSessionDTO.class);
            return session.getToken();
        }
        return null;
    }

    public String postCreateBranchAndGetId(String session_token) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"name\": \"Назва відділення\", \"location\": \"Місто\", \"street\": \"Вулиця\",\"house\": \"Будинок\", \"deliveryTypes\": [\"api\"], \"offerRequestType\": \"dynamic\", \"scopes\": { \"sharing\": [\"passport\", \"internal-passport\", \"taxpayer-card\"] }}",
                mediaType);
        Request request = new Request.Builder()
                .url(DIA_API_URL + "/v2/acquirers/branch") // DIA URL, delete "s" for prod. version of dia
                .post(body)
                .addHeader("Content-Type", "application/json") // Можливо генерується автоматом в медіатайп, чекнути
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + session_token)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            DiaBranchDTO branch = objectMapper.readValue(responseBody, DiaBranchDTO.class);
            return branch.get_id();
        }
        return null;
    }

    public String postCreateOfferAndGetId(String session_token, String branch_id, String returnLink) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"name\": \"Авторизація\",\"returnLink\": \"" + returnLink + "\",\"scopes\": { \"sharing\": [\"passport\", \"internal-passport\", \"taxpayer-card\"] }}\r\n",
                mediaType);
        Request request = new Request.Builder()
                .url(DIA_API_URL + "/v1/acquirers/branch/" + branch_id + "/offer")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + session_token)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            DiaOfferDTO offer = objectMapper.readValue(responseBody, DiaOfferDTO.class);
            return offer.get_id();
        }
        return null;
    }

    public String postCreateOfferRequestAndGetDeeplink(String branch_id, String offer_id, String returnLink) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"offerId\": \"" + offer_id + "\",\"returnLink\": \"" + returnLink + "\"requestId\": \""  + generateRequestId() + "\"}", mediaType);
        Request request = new Request.Builder()
                .url(DIA_API_URL + "/v2/acquirers/branch/" + branch_id + "/offer-request/dynamic")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            DiaDeeplinkDTO deeplink = objectMapper.readValue(responseBody, DiaDeeplinkDTO.class);
            return deeplink.getDeeplink();
        }
        return null;
    }

    private String generateRequestId() throws Exception {
        byte[] array = new byte[32]; // mb 44 due to documentation
        new Random().nextBytes(array);
        EndUser user = new EndUser();
        return user.BASE64Encode(array);
    }

}
