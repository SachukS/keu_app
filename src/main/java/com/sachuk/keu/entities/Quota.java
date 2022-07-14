package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.QuotaType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Quota")
@Table(name = "Quota")
@NamedQueries({
        @NamedQuery(name=Quota.findByNameQuota, query="from Quota i where i.nameQuota=:name"),
        @NamedQuery(name=Quota.findByShortNameQuota, query="from Quota i where i.shortNameQuota=:name")
})
@Getter
@Setter
public class Quota implements Serializable{

    private static final long serialVersionUID = -6430509643986769733L;
    public static final String findByNameQuota = "quota.findByNameQuota";
    public static final String findByShortNameQuota = "quota.findByShortNameQuota";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 100, unique=true)
    private String nameQuota;
    @Column(length=100, unique=true)
    private String shortNameQuota;
    @Enumerated(EnumType.STRING)
    private QuotaType quotaType = QuotaType.NONE;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "Quota",targetEntity=Privilege.class)
//    private List<Privilege> privileges = new ArrayList<>();


    public Quota(String nameQuota, String shortNameQuota, QuotaType quotaType) {
        this.nameQuota = nameQuota;
        this.shortNameQuota = shortNameQuota;
//        this.privileges = privileges;
        this.quotaType = quotaType;
    }
    public Quota(){}

}