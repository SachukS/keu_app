package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.QuotaType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "familyMembers")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "thirdName", nullable = false, length = 100)
    private String thirdName;

    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date birthDate;

}
