package com.sachuk.keu.entities.enums;

import java.util.Arrays;

public enum Provided {
    NO("НІ"), SLZH("Службовим житлом"), GURT("Гуртожитком"), SHPZH("ШПЖ"), PERE("Переобладнаною будівлею"), POST("Постійним житлом"), COMP("Компенсацією");

    private String name;

    Provided(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Provided getByValue(String mean) {
        return Arrays.stream(Provided.values()).filter(f -> f.getName().equals(mean)).findAny().orElse(null);
    }
}
