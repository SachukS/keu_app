package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.*;
import com.sachuk.keu.utils.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
public class MilitaryMan implements Serializable {

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
    private Quota quota = new Quota("Без пільг", "Без пільг", QuotaType.NONE);

//    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Quota.class)
//    @JoinColumn(name = "quota_id2")
//    private Quota quota2 = new Quota("Без пільг", "Без пільг", QuotaType.NONE);
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date quotaDate;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date quotaDate2;
    private int familyCount;
    private int roomCount;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Work.class)
    @JoinColumn(name = "work_id")
    private Work work;
    private String address;
    @Enumerated(EnumType.ORDINAL)
    private Rozshirennya rozshirennya = Rozshirennya.NO;
    @Enumerated(EnumType.ORDINAL)
    private Provided provided = Provided.NO;
    private String quotaType;
    private String quotaType2;
    @CreationTimestamp
    private LocalDateTime updateDate;
    private String info;
    private String family;
    @Enumerated(EnumType.ORDINAL)
    private Registrated registrated = Registrated.NO;
    @Enumerated(EnumType.ORDINAL)
    private FamilyWar2022 familyWar2022 = FamilyWar2022.NO;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date flatFileDate;
    private String flatFileNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date serviceFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date serviceUntill;
    private String experience;
    private String phoneNumber;
    @ColumnDefault("0")
    private String zagalna;
    @ColumnDefault("0")
    private String pilgova;

    public int getExpInt() {
        return Integer.parseInt(getExperience());
    }

    public String getFormatAccounting() {
        if (this.getAccountingDate() != null)
            return DateUtil.formatDateToString(this.getAccountingDate(), "dd.MM.yyyy");
        return null;
    }

    public Date getUpdateDateTime() {
        if (updateDate == null)
            return null;
        return Date.from(updateDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getFormatQuota() {
        if (this.getQuotaDate() != null)
            return DateUtil.formatDateToString(this.getQuotaDate(), "dd.MM.yyyy");
        return null;
    }

//    public String getFormatQuota2() {
//        if (this.getQuotaDate() != null)
//            return DateUtil.formatDateToString(this.getQuotaDate2(), "dd.MM.yyyy");
//        return null;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MilitaryMan militaryMan = (MilitaryMan) o;
        return familyCount == militaryMan.familyCount && roomCount == militaryMan.roomCount && Objects.equals(surname, militaryMan.surname) && Objects.equals(name, militaryMan.name) && Objects.equals(thirdName, militaryMan.thirdName) && Objects.equals(accountingDate, militaryMan.accountingDate) && Objects.equals(quotaDate, militaryMan.quotaDate) && Objects.equals(quotaType, militaryMan.quotaType) && registrated == militaryMan.registrated;
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, thirdName, accountingDate, quotaDate, familyCount, roomCount, quotaType, registrated);
    }

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
                ", quotaType=" + quotaType +
                ", familyCount=" + familyCount +
                ", roomCount=" + roomCount +
                ", work=" + work +
                ", address='" + address + '\'' +
                ", rozshirennya=" + rozshirennya +
                ", updateDate=" + updateDate +
                ", info='" + info + '\'' +
                ", family='" + family + '\'' +
                ", registrated=" + registrated +
                ", flatFileDate=" + flatFileDate +
                ", flatFileNumber='" + flatFileNumber + '\'' +
                ", serviceFrom=" + serviceFrom +
                ", serviceUntill=" + serviceUntill +
                ", zagalna=" + zagalna +
                ", pilgova=" + pilgova +
                '}';
    }
}
