package com.sachuk.keu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Work")
@Table(name = "Work")
@NamedQueries({
        @NamedQuery(name = Work.findByWorkPlace, query = "from Work i where i.workPlace=:name"),
        @NamedQuery(name = Work.findByAccountingPlace, query = "from Work i where i.accountingPlace=:name"),
        @NamedQuery(name = Work.findByGarrison, query = "from Work i where i.garrison=:name")
})
@Getter
@Setter
public class Work implements Serializable {

    private static final long serialVersionUID = -8030707021894951893L;
    public static final String findByWorkPlace = "work.findByWorkPlace";
    public static final String findByAccountingPlace = "work.findByAccountingPlace";
    public static final String findByGarrison = "work.findByGarrison";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 25)
    private String workPlace;
    @Column(length = 25)
    private String accountingPlace;
    @Column(length = 25)
    private String garrison;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "privilegeDocument",targetEntity=Privilege.class)
//    private List<Privilege> privileges = new ArrayList<>();


    public Work(String workPlace, String accountingPlace, String garrison) {
        this.workPlace = workPlace;
        this.accountingPlace = accountingPlace;
        this.garrison = garrison;
        //this.privileges = privileges;
    }

    public Work(String workPlace, String accountingPlace) {
        this.workPlace = workPlace;
        this.accountingPlace = accountingPlace;
        //this.privileges = privileges;
    }

    public Work() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Work work = (Work) o;
        return Objects.equals(workPlace, work.workPlace) && Objects.equals(accountingPlace, work.accountingPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workPlace, accountingPlace);
    }

    @Override
    public String toString() {
        return "Work{" +
                "workPlace='" + workPlace + '\'' +
                ", accountingPlace='" + accountingPlace + '\'' +
                ", garrison='" + garrison + '\'' +
                '}';
    }
}
