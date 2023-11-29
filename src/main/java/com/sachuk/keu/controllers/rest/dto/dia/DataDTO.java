package com.sachuk.keu.controllers.rest.dto.dia;

import lombok.Data;

@Data

public class DataDTO {
    String requestId;
    String signature;

    public DataDTO(String requestId, String signature) {
        this.requestId = requestId;
        this.signature = signature;
    }
}
