package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.CurrencyType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "finance_sources")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FinanceSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sum", nullable = false)
    private double sum;

    @Column(name = "currency_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    public FinanceSource(String name, double sum, CurrencyType currencyType) {
        this.name = name;
        this.sum = sum;
        this.currencyType = currencyType;
    }
}
