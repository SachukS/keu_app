package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.Provided;
import com.sachuk.keu.entities.enums.QuotaType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "military_man")
public class MilitaryMan implements Serializable { // TODO: 25.11.2023 Refactor length fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "thirdName", nullable = false, length = 100)
    private String thirdName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "work_experience", nullable = false)
    private int workExperience;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "accounting_date", nullable = false, length = 6)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date accountingDate;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Quota.class)
    @JoinColumn(name = "quota_id", nullable = false)
    private Quota quota = new Quota("Без пільг", "Без пільг", QuotaType.NONE);

    @Column(name = "quota_date", nullable = true)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date quotaDate;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Rank.class)
    @JoinColumn(name = "rank_id", nullable = false)
    private Rank rank;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Work.class)
    @JoinColumn(name = "work_id", nullable = false)
    private Work work;

    @Lob
    @Column(name = "info", nullable = true)
    @Type(type = "org.hibernate.type.TextType")
    private String info;

    @Column(name = "general_queue")
    @ColumnDefault("0")
    private String generalQueue;

    @Column(name = "quota_queue")
    @ColumnDefault("0")
    private String quotaQueue;

    @Column(name = "registrated", nullable = false)
    @ColumnDefault("false")
    private boolean registrated;

    @Column(name = "rozshirennya", nullable = false)
    @ColumnDefault("false")
    private boolean rozshirennya;

    @Column(name = "want_compensation", nullable = false)
    @ColumnDefault("false")
    private boolean wantCompensation;

    @Column(name = "expected_compensation_value", nullable = false)
    private double expectedCompensationValue;

    @Column(name = "death_date", nullable = false)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date deathDate;

    @Column(name = "ipn", nullable = false)
    private String ipn;

    @Enumerated(EnumType.STRING)
    @Column(name = "provided", nullable = false)
    private Provided provided = Provided.NO;

    @Column(name = "update_date", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column(name = "room_count", nullable = false)
    private int roomCount;

    @Column(name = "family_war_2022", nullable = false)
    @ColumnDefault("false")
    private boolean familyWar2022;

    @Column(name = "service_from", nullable = false)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date serviceFrom;

    @Column(name = "service_until", nullable = false)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date serviceUntil;

    @Column(name = "apartment_file_date", nullable = false)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date apartmentFileDate;

    @Column(name = "apartment_file_number", nullable = false)
    private String apartmentFileNumber;

    @OneToMany
    @JoinColumn(name = "military_man_id")
    private List<FamilyMember> family = new ArrayList<>();

//    public int getExpInt() {
//        return getWorkExperience();
//    }
//
//    public String getFormatAccounting() {
//        if (this.getAccountingDate() != null)
//            return DateUtil.formatDateToString(this.getAccountingDate(), "dd.MM.yyyy");
//        return null;
//    }
//
//    public Date getUpdateDateTime() {
//        if (updateDate == null)
//            return null;
//        return Date.from(updateDate.atZone(ZoneId.systemDefault()).toInstant());
//    }

//    public String getFormatQuota() {
//        if (this.getQuotaDate() != null)
//            return DateUtil.formatDateToString(this.getQuotaDate(), "dd.MM.yyyy");
//        return null;
//    }
}
