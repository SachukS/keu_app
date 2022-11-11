package com.sachuk.keu.entities.enums;

import java.util.Arrays;

public enum FamilyWar2022 {
    NO("НІ"), YES("ТАК");

    private String name;

    FamilyWar2022(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FamilyWar2022 getByValue(String mean) {
        return Arrays.stream(FamilyWar2022.values()).filter(f -> f.getName().equals(mean)).findAny().orElse(null);
    }
}
