package com.sachuk.keu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Work")
@Table(name = "Work")
@NamedQueries({
        @NamedQuery(name= Work.findByWorkPlace, query="from Work i where i.workPlace=:name"),
        @NamedQuery(name= Work.findByAccountingPlace, query="from Work i where i.accountingPlace=:name")
})
@Getter
@Setter
public class Work implements Serializable {

    private static final long serialVersionUID = -8030707021894951893L;
    public static final String findByWorkPlace = "work.findByWorkPlace";
    public static final String findByAccountingPlace = "work.findByAccountingPlace";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 25)
    private String workPlace;
    @Column(length=25)
    private String accountingPlace;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "privilegeDocument",targetEntity=Privilege.class)
//    private List<Privilege> privileges = new ArrayList<>();


    public Work(String workPlace, String accountingPlace) {
        this.workPlace = workPlace;
        this.accountingPlace = accountingPlace;
        //this.privileges = privileges;
    }
    public Work(){}

}
