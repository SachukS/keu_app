package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.QuotaType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "quotas")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Quota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 100, unique = true)
    private String name;

    @Column(name = "short_name", length = 100, unique = true)
    private String shortName;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private QuotaType type = QuotaType.NONE;

    public Quota(String name, String shortName, QuotaType type) {
        this.name = name;
        this.shortName = shortName;
        this.type = type;
    }
}