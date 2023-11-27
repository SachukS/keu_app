package com.sachuk.keu.controllers.rest.dto.dia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DiaDeeplinkDTO {
    @JsonProperty("deeplink")
    private String deeplink;

    public DiaDeeplinkDTO(String deeplink) {
        this.deeplink = deeplink;
    }
}
