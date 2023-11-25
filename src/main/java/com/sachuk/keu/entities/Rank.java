package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.RankType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ranks")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Rank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "short_name", length = 20, nullable = false)
    private String shortName;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RankType type = RankType.PRESENTMILITARY;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "privilegeDocument",targetEntity=Privilege.class)
//    private List<Privilege> privileges = new ArrayList<>();


    public Rank(String name, String shortName, RankType type) {
        this.name = name;
        this.shortName = shortName;
        this.type = type;
        //this.privileges = privileges;
    }
}
