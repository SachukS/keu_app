package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.Registrated;
import com.sachuk.keu.entities.enums.Rozshirennya;
import com.sachuk.keu.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class  Customer implements Serializable {

    private static final long serialVersionUID = 1727059679068137102L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Rank.class)
    @JoinColumn(name = "rank_id")
    private Rank rank;
    //private String rankType = rank.getRankType().getName();
    private String rankType;
    private String surname;
    private String name;
    private String thirdName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date accountingDate;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Quota.class)
    @JoinColumn(name = "quota_id")
    private Quota quota;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date quotaDate;
    private int familyCount;
    private int roomCount;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Work.class)
    @JoinColumn(name = "work_id")
    private Work work;
    private String address;
    @Enumerated(EnumType.ORDINAL)
    private Rozshirennya rozshirennya = Rozshirennya.NO;
    //private String quotaType = quota.getQuotaType().getName();
    private String quotaType;
    @CreationTimestamp
    private LocalDateTime updateDate;
    private String info;
    private String family;
    @Enumerated(EnumType.ORDINAL)
    private Registrated registrated = Registrated.NO;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date flatFileDate;
    private String flatFileNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date serviceFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date serviceUntill;
    private String experience;

    public String getFormatAccounting() {
        if (this.getAccountingDate()!=null)
            return DateUtil.formatDateToString(this.getAccountingDate(), "dd.MM.yyyy");
        return null;
    }
    public String getFormatQuota() {
        if (this.getQuotaDate()!=null)
            return DateUtil.formatDateToString(this.getQuotaDate(), "dd.MM.yyyy");
        return null;
    }

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "enrollee")
//    private List<ZNOCertificate> znoCertificates = new ArrayList<>();
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "enrollee")
//    private Certificate certificate;
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "enrollee", targetEntity = EnrolleeSpecialty.class)
//    private List<EnrolleeSpecialty> specialties = new ArrayList<>();
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "enrollee", targetEntity = Privilege.class)
//    private List<Privilege> privileges = new ArrayList<>();
//    private String remark;
//    @CreationTimestamp
//    private LocalDateTime createDateTime;
//    @UpdateTimestamp
//    private LocalDateTime updateDateTime;


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", rank=" + rank +
                ", rankType='" + rankType + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", thirdName='" + thirdName + '\'' +
                ", accountingDate=" + accountingDate +
                ", quota=" + quota +
                ", quotaDate=" + quotaDate +
                ", familyCount=" + familyCount +
                ", roomCount=" + roomCount +
                ", work=" + work +
                ", address='" + address + '\'' +
                ", rozshirennya=" + rozshirennya +
                ", quotaType='" + quotaType + '\'' +
                ", updateDate=" + updateDate +
                ", info='" + info + '\'' +
                ", family='" + family + '\'' +
                ", registrated=" + registrated +
                ", flatFileDate=" + flatFileDate +
                ", flatFileNumber='" + flatFileNumber + '\'' +
                ", serviceFrom=" + serviceFrom +
                ", serviceUntill=" + serviceUntill +
                ", experience='" + experience + '\'' +
                '}';
    }
}
