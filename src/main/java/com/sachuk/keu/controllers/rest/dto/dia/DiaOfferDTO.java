package com.sachuk.keu.controllers.rest.dto.dia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DiaOfferDTO {
    @JsonProperty("")
    private String _id;

    public DiaOfferDTO(String _id) {
        this._id = _id;
    }
}
