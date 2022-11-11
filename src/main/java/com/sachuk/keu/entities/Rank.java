package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.RankType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Rank")
@Table(name = "Rank")
@NamedQueries({
        @NamedQuery(name = Rank.findByNameRank, query = "from Rank i where i.nameRank=:name"),
        @NamedQuery(name = Rank.findByShortNameRank, query = "from Rank i where i.shortNameRank=:name")
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
    @Column(length = 20)
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

    public Rank() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank = (Rank) o;
        return Objects.equals(nameRank, rank.nameRank) && Objects.equals(shortNameRank, rank.shortNameRank) && rankType == rank.rankType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameRank, shortNameRank, rankType);
    }
}
