package com.sachuk.keu.entities.enums;

import java.util.Arrays;

public enum Registrated {

    NO("НІ"), YES("ТАК"), ALL("Всі");

    private String name;

    Registrated(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Registrated getByValue(String mean) {
        return Arrays.stream(Registrated.values()).filter(f -> f.getName().equals(mean)).findAny().orElse(null);
    }
}
