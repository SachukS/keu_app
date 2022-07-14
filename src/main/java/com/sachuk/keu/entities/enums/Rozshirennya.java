package com.sachuk.keu.entities.enums;

import java.util.Arrays;

public enum Rozshirennya {
    NO("НІ"), YES("ТАК");

    private String name;

    Rozshirennya(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static Rozshirennya getByValue(String mean) {
        return Arrays.stream(Rozshirennya.values()).filter(f -> f.getName().equals(mean)).findAny().orElse(null);
    }

}