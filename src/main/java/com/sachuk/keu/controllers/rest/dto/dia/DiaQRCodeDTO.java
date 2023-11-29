package com.sachuk.keu.controllers.rest.dto.dia;

import lombok.Data;

@Data
public class DiaQRCodeDTO {
    String qrcodeInBase64;

    public DiaQRCodeDTO(String qrcodeInBase64) {
        this.qrcodeInBase64 = qrcodeInBase64;
    }
}
