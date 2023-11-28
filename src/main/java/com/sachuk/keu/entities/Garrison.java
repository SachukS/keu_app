package com.sachuk.keu.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "garrisons")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Garrison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price_per_meter", nullable = false)
    private double pricePerMeter;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "housing_rent_compensation", nullable = false)
    private double housingRentCompensation;

    public Garrison(String name, double pricePerMeter, String region) {
        this.name = name;
        this.pricePerMeter = pricePerMeter;
        this.region = region;
    }
}
