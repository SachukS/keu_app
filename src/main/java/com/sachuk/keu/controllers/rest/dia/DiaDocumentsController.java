package com.sachuk.keu.controllers.rest.dia;

import com.sachuk.keu.services.dia.DiaDocumentsService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
@Data
public class DiaDocumentsController { // Документи - will be provided in future
    private final DiaDocumentsService service;
    private final String ACQUIRER_TOKEN = "some_really_cool_dia_token"; // TODO
    private final long TIMEOUT_MILLIS = 500;
    private final String returnLink = "where"; // TODO

    public DiaDocumentsController(DiaDocumentsService service) {
        this.service = service;
    }

    @GetMapping("/dia")
    public String authorize() {
        try {
            String session_token = service.getCreateSessionAndGetToken(ACQUIRER_TOKEN);
            wait(TIMEOUT_MILLIS);
            String branch_id = service.postCreateBranchAndGetId(session_token);
            wait(TIMEOUT_MILLIS);
            String offer_id = service.postCreateOfferAndGetId(session_token, branch_id, this.returnLink);
            wait(TIMEOUT_MILLIS);
            String deepLink = service.postCreateOfferRequestAndGetDeeplink(branch_id, offer_id, this.returnLink);
            return "redirect:"+deepLink;
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // TODO Redirect where?
        }
    }
}
