package com.sachuk.keu.controllers.rest.dia;

import com.sachuk.keu.controllers.rest.dto.dia.DiaQRCodeDTO;
import com.sachuk.keu.services.dia.DiaAuthService;
import com.sachuk.keu.services.dia.DiaQRCodeService;
import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/login/dia")
@Data
public class DiaAuthController { // Авторизація через дію
    private final DiaAuthService service;
    private final String returnLink = "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley"; // TODO

    public DiaAuthController(DiaAuthService service) {
        this.service = service;
    }

    @GetMapping("/auth")
    public DiaQRCodeDTO authorize(Model model) {
        try {
            String session_token = service.getCreateSessionAndGetToken();
            String branch_id = "4caf73a1a770b0ca18a9d1aa454d627954c6477097ce95aca4893211bf5bbd7c5bb7eac945670aed9af612245cbd39bc82c2ad725433b3f0794a535ccb7b3c65";
            String offer_id = service.postCreateOfferAndGetId(session_token, branch_id, this.returnLink);
            String deepLink = service.postCreateOfferRequestAndGetDeeplink(branch_id, offer_id, this.returnLink, session_token);
            byte[] image = new byte[0];
            image = DiaQRCodeService.getQRCode(deepLink, 250, 250);
            String qrcode = Base64.getEncoder().encodeToString(image);
            model.addAttribute("qrcode", qrcode);
            return new DiaQRCodeDTO(qrcode); // Qr code
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
