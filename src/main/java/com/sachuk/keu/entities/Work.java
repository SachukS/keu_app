package com.sachuk.keu.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "works")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Work implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_place", length = 25, nullable = false)
    private String workPlace;

    @Column(name = "accounting_place", length = 25, nullable = false)
    private String accountingPlace;

    @Column(name = "garrison", length = 25, nullable = false)
    private String garrison;

    public Work(String workPlace, String accountingPlace, String garrison) {
        this.workPlace = workPlace;
        this.accountingPlace = accountingPlace;
        this.garrison = garrison;
        //this.privileges = privileges;
    }

    public Work(String workPlace, String accountingPlace) {
        this.workPlace = workPlace;
        this.accountingPlace = accountingPlace;
    }
}
