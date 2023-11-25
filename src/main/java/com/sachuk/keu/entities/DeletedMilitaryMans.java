package com.sachuk.keu.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "works")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DeletedMilitaryMans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = MilitaryMan.class)
    @JoinColumn(name = "military_man_id", nullable = false)
    private MilitaryMan militaryMan;

    @Column(name = "death_certificate", nullable = true)
    private String deathCertificate;

    @Column(name = "protokol_number", nullable = false)
    private String protokolNumber;

    @Column(name = "delete_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deleteDate;
}
