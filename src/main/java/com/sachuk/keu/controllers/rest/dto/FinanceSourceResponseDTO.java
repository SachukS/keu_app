package com.sachuk.keu.controllers.rest.dto;

import com.sachuk.keu.entities.FinanceSource;
import com.sachuk.keu.entities.enums.CurrencyType;
import lombok.Getter;

@Getter
public class FinanceSourceResponseDTO {
    private Long id;
    private String name;
    private double sum;
    private CurrencyType currencyType;

    public FinanceSourceResponseDTO(FinanceSource financeSource) {
        this.id = financeSource.getId();
        this.name = financeSource.getName();
        this.sum = financeSource.getSum();
        this.currencyType = financeSource.getCurrencyType();
    }
}
