package com.sachuk.keu.entities;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "provided_flats")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProvidedFlat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "square", nullable = false)
    private double square;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "room_count", nullable = false)
    private int roomCount;

    @Column(name = "unserviced_apartment", nullable = false, length = 100)
    @ColumnDefault("false")
    private boolean unservicedApartment;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = FinanceSource.class)
    @JoinColumn(name = "finance_source_id", nullable = false)
    private FinanceSource financeSource;

    public ProvidedFlat(double square, double cost, int roomCount, FinanceSource financeSourceId) {
        this.square = square;
        this.cost = cost;
        this.roomCount = roomCount;
        this.financeSource = financeSourceId;
    }
}
