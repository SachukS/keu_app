package com.sachuk.keu.entities.payload;

import com.sachuk.keu.entities.DeletedMilitaryMan;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.entities.Registry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputPayload {
    public MilitaryMan militaryMan;
    public Registry registry;
}
