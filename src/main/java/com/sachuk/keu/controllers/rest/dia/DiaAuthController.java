package com.sachuk.keu.controllers.rest.dia;

import com.sachuk.keu.services.dia.DiaAuthService;
import com.sachuk.keu.services.dia.DiaDocumentsService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/login/dia")
@Data
public class DiaAuthController {
    private final DiaAuthService service;
    private final long TIMEOUT_MILLIS = 500;
    private final String returnLink = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley"; // TODO

    public DiaAuthController(DiaAuthService service) {
        this.service = service;
    }

    @GetMapping("/auth")
    public String authorize(Model model) {
        try {
            String session_token = service.getCreateSessionAndGetToken();
            String branch_id = service.postCreateBranchAndGetId(session_token);
            String offer_id = service.postCreateOfferAndGetId(session_token, branch_id, this.returnLink);
            String deepLink = service.postCreateOfferRequestAndGetDeeplink(branch_id, offer_id, this.returnLink, session_token);
            model.addAttribute("deeplink", deepLink);
            return "diaTest";
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // TODO Redirect where?
        }
    }

}
