package com.sachuk.keu.controllers.rest.dto.dia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DiaBranchDTO {
    @JsonProperty("_id")
    private String _id;

    public DiaBranchDTO(String _id) {
        this._id = _id;
    }
}
