package com.sachuk.keu.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registry")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Registry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = MilitaryMan.class)
    @JoinColumn(name = "military_man_id", nullable = false)
    private MilitaryMan militaryMan;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = ProvidedFlat.class)
    @JoinColumn(name = "provided_flat_id", nullable = true)
    private ProvidedFlat ProvidedFlat;

    @Column(name = "received_money", nullable = true)
    private double receivedMoney;

    @Column(name = "receive_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date receiveDate;

    public Registry(MilitaryMan militaryMan, ProvidedFlat providedFlat, double receivedMoney, Date receiveDate) {
        this.militaryMan = militaryMan;
        ProvidedFlat = providedFlat;
        this.receivedMoney = receivedMoney;
        this.receiveDate = receiveDate;
    }
}
