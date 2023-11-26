package com.sachuk.keu.controllers.rest.dia;

import com.sachuk.keu.controllers.rest.dto.dia.DiaBranchDTO;
import com.sachuk.keu.controllers.rest.dto.dia.DiaDeeplinkDTO;
import com.sachuk.keu.controllers.rest.dto.dia.DiaOfferDTO;
import com.sachuk.keu.controllers.rest.dto.dia.DiaSessionDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dia/test-api")
public class DiaTestFunctionalController {

    private final String deepLink = "/authdone";

    @GetMapping("/v1/auth/acquirer/{acquirer_token}")
    @ResponseBody
    public DiaSessionDTO createSession(@PathVariable("acquirer_token") String acquirer_token) {
        return new DiaSessionDTO("eyJ2hKcS5m3Af5ePg");
    }

    @PostMapping("/v2/acquirers/branch")
    @ResponseBody
    public DiaBranchDTO createBranch() {
        return new DiaBranchDTO("5e8decccb92d8d73ad838c5d");
    }

    @PostMapping("/v1/acquirers/branch/{branch_id}/offer")
    @ResponseBody
    public DiaOfferDTO createOffer(@PathVariable String branch_id) {
        return new DiaOfferDTO("uuid4");
    }

    @PostMapping("/v2/acquirers/branch/{branch_id}/offer-request/dynamic")
    @ResponseBody
    public DiaDeeplinkDTO createRequest(@PathVariable String branch_id) {
        return new DiaDeeplinkDTO(deepLink); // TODO WHERE TO REDIRECT
    }


}
