package com.sachuk.keu.controllers.rest.dto;

import com.sachuk.keu.entities.enums.CurrencyType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class FinanceSourceRequestDTO {
    private String name;
    private double sum;
    private CurrencyType currencyType;
}

