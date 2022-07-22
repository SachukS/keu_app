package com.sachuk.keu.entities.thirdparty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerShort {
    private String zv;
    private String fio;
    private String kwdate;
    private String nomplg;
    private String datplg;
    private String katplg;
    private String fam;
    private String room;
    private String zhk;
    private String work;

    public CustomerShort(String zv, String fio, String kwdate, String nomplg, String datplg, String katplg, String fam, String room, String zhk, String work) {
        this.zv = zv;
        this.fio = fio;
        this.kwdate = kwdate;
        this.nomplg = nomplg;
        this.datplg = datplg;
        this.katplg = katplg;
        this.fam = fam;
        this.room = room;
        this.zhk = zhk;
        this.work = work;
    }
}
