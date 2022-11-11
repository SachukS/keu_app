package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.QuotaType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Quota")
@Table(name = "Quota")
@NamedQueries({
        @NamedQuery(name = Quota.findByNameQuota, query = "from Quota i where i.nameQuota=:name"),
        @NamedQuery(name = Quota.findByShortNameQuota, query = "from Quota i where i.shortNameQuota=:name")
})
@Getter
@Setter
public class Quota implements Serializable {

    private static final long serialVersionUID = -6430509643986769733L;
    public static final String findByNameQuota = "quota.findByNameQuota";
    public static final String findByShortNameQuota = "quota.findByShortNameQuota";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 100, unique = true)
    private String nameQuota;
    @Column(length = 100, unique = true)
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

    public Quota() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quota quota = (Quota) o;
        return Objects.equals(nameQuota, quota.nameQuota) && Objects.equals(shortNameQuota, quota.shortNameQuota) && quotaType == quota.quotaType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameQuota, shortNameQuota, quotaType);
    }

    @Override
    public String toString() {
        return "Quota{" +
                "id=" + id +
                ", nameQuota='" + nameQuota + '\'' +
                ", shortNameQuota='" + shortNameQuota + '\'' +
                ", quotaType=" + quotaType +
                '}';
    }
}