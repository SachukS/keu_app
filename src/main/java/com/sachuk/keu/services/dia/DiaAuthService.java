package com.sachuk.keu.services.dia;

import com.google.gson.Gson;
import com.iit.certificateAuthority.endUser.libraries.signJava.*;
import com.sachuk.keu.controllers.rest.dto.dia.DiaBranchDTO;
import com.sachuk.keu.controllers.rest.dto.dia.DiaDeeplinkDTO;
import com.sachuk.keu.controllers.rest.dto.dia.DiaOfferDTO;
import com.sachuk.keu.controllers.rest.dto.dia.DiaSessionDTO;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DiaAuthService {
    private final String DIA_API_URL = "https://api2s.diia.gov.ua/api"; // TODO
    private final String ACQUIRER_TOKEN = "viti_test_token_fon998";
    private final String AUTH_ACQUIRER_TOKEN = "YWNxdWlyZXJfNzEwOnZpdGlfdGVzdF90b2tlbl9mb245OTg=";

    @Getter
    @Setter
    private EndUser endUser = new EndUser();

    public String getCreateSessionAndGetToken() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(DIA_API_URL + "/v1/auth/acquirer/" + ACQUIRER_TOKEN)
                .addHeader("Authorization", "Basic " + AUTH_ACQUIRER_TOKEN)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            String responseBody = response.body().string();
            Gson gson = new Gson();
            DiaSessionDTO session = gson.fromJson(responseBody, DiaSessionDTO.class);
            return session.getToken();
        }
        return null;
    }

    public String postCreateBranchAndGetId(String session_token) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"name\": \"JAM Team\", \"location\": \"м. Київ\", \"street\": \"Хакатонна\",\"house\": \"1\", \"deliveryTypes\": [\"api\"], \"offerRequestType\": \"dynamic\", \"scopes\": { \"diiaId\": [\"auth\"] }}",
                mediaType);
        Request request = new Request.Builder()
                .url(DIA_API_URL + "/v2/acquirers/branch") // DIA URL, delete "s" for prod. version of dia
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + session_token)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            String responseBody = response.body().string();
            Gson gson = new Gson();
            DiaBranchDTO branch = gson.fromJson(responseBody, DiaBranchDTO.class);
            return branch.get_id();
        }
        return null;
    }

    public String postCreateOfferAndGetId(String session_token, String branch_id, String returnLink) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"name\": \"Авторизація\",\"returnLink\": \"" + returnLink + "\",\"scopes\": { \"diiaId\": [\"auth\"] }}",
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
            Gson gson = new Gson();
            DiaOfferDTO offer = gson.fromJson(responseBody, DiaOfferDTO.class);
            return offer.get_id();
        }
        return null;
    }

    public String postCreateOfferRequestAndGetDeeplink(String branch_id, String offer_id, String returnLink, String session_token) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"offerId\": \"" + offer_id + "\"" +
                ",\"returnLink\": \"" + returnLink + "\"" +
                ",\"requestId\": \"" + generateRequestId(offer_id) + "\"}", mediaType);
        Request request = new Request.Builder()
                .url(DIA_API_URL + "/v2/acquirers/branch/" + branch_id + "/offer-request/dynamic")
                .addHeader("Authorization", "Bearer " + session_token)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            String responseBody = response.body().string();
            System.out.println(responseBody);
            Gson gson = new Gson();
            DiaDeeplinkDTO deeplink = gson.fromJson(responseBody, DiaDeeplinkDTO.class);
            return deeplink.getDeeplink();
        }
        return null;
    }

    private String generateRequestId(String uuid4) throws Exception {
        init();
        byte[] array = uuid4.getBytes(); // mb 44 due to documentation
        String hash = endUser.Hash(array);
        return hash; // TODO RABOTAI

    }

    public void init() throws Exception{
        endUser.SetUIMode(false);
        endUser.SetLanguage(EndUser.EU_RU_LANG);
        endUser.SetCharset("UTF-8");
        try {
            endUser.Initialize();
            EndUserModeSettings modeSettings = new EndUserModeSettings(false);
            modeSettings.SetOfflineMode(false);
            endUser.SetModeSettings(modeSettings);
            EndUserProxySettings proxySettings = endUser.CreateProxySettings();
            proxySettings.SetUseProxy(false);
            proxySettings.SetAnonymous(true);
            proxySettings.SetAddress("");
            proxySettings.SetPort("");
            proxySettings.SetUser("");
            proxySettings.SetPassword("");
            proxySettings.SetSavePassword(false);
            endUser.SetProxySettings(proxySettings);
            EndUserFileStoreSettings fileStoreSettings = endUser.CreateFileStoreSettings();
            fileStoreSettings.SetPath("/static/enduserstorage");
            fileStoreSettings.SetSaveLoadedCerts(true);
            endUser.SetFileStoreSettings(fileStoreSettings);
            EndUserCMPSettings cmpSettings = endUser.CreateCMPSettings();
            cmpSettings.SetUseCMP(true);
            cmpSettings.SetAddress("http://acsk.privatbank.ua/services/cmp/");
            cmpSettings.SetPort("80");
            cmpSettings.SetCommonName("");
            endUser.SetCMPSettings(cmpSettings);
            EndUserTSPSettings tspSettings = endUser.CreateTSPSettings();
            tspSettings.SetAddress("http://acsk.privatbank.ua/services/tsp/");
            tspSettings.SetPort("80");
            tspSettings.SetGetStamps(true);
            endUser.SetTSPSettings(tspSettings);
            EndUserOCSPSettings ocspSettings = endUser.CreateOCSPSettings();
            ocspSettings.SetAddress("http://acsk.privatbank.ua/services/ocsp/");
            ocspSettings.SetPort("80");
            ocspSettings.SetUseOCSP(true);
            endUser.SetOCSPSettings(ocspSettings);
            EndUserLDAPSettings lDAPSettings = endUser.CreateLDAPSettings();
            endUser.SetLDAPSettings(lDAPSettings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
