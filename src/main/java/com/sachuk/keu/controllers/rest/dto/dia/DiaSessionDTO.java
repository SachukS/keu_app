package com.sachuk.keu.controllers.rest.dto.dia;

import lombok.Data;

@Data
public class DiaSessionDTO {
    private String token;

    public DiaSessionDTO(String token) {
        this.token = token;
    }
}
