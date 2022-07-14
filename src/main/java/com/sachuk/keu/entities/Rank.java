package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.QuotaType;
import com.sachuk.keu.entities.enums.RankType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Rank")
@Table(name = "Rank")
@NamedQueries({
        @NamedQuery(name= Rank.findByNameRank, query="from Rank i where i.nameRank=:name"),
        @NamedQuery(name= Rank.findByShortNameRank, query="from Rank i where i.shortNameRank=:name")
})
@Getter
@Setter
public class Rank implements Serializable {

    private static final long serialVersionUID = -1757435202596317113L;
    public static final String findByNameRank = "rank.findByNameRank";
    public static final String findByShortNameRank = "rank.findByShortNameRank";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 100)
    private String nameRank;
    @Column(length=20)
    private String shortNameRank;

    @Enumerated(EnumType.STRING)
    private RankType rankType = RankType.PRESENTMILITARY;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "privilegeDocument",targetEntity=Privilege.class)
//    private List<Privilege> privileges = new ArrayList<>();


    public Rank(String nameRank, String shortNameRank, RankType rankType) {
        this.nameRank = nameRank;
        this.shortNameRank = shortNameRank;
        this.rankType = rankType;
        //this.privileges = privileges;
    }
    public Rank(){}

}
