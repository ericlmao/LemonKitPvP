package dev.negativekb.lemonkitpvp.core.structure.clans;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum ClanUpgrade {
    // x-percent chance to get double coins on kill
    DOUBLE_COINS("Double Coins", 10, Arrays.asList(
            1000.0,
            2000.0,
            3000.0,
            4000.0,
            5000.0,
            6000.0,
            7000.0,
            8000.0,
            9000.0,
            10000.0
    )),
    // x-percent chance to get double xp on kill
    DOUBLE_XP("Double Experience", 10, Arrays.asList(
            1000.0,
            2000.0,
            3000.0,
            4000.0,
            5000.0,
            6000.0,
            7000.0,
            8000.0,
            9000.0,
            10000.0
    )),

    // x-percent chance to get x amount of potions on kill
    POTS_ON_KILL("Pots on Kill", 5, Arrays.asList(
            1000.0,
            2000.0,
            3000.0,
            4000.0,
            5000.0
    )), // x-percent chance to get x amount of potions on kill

    // x-percent chance to not lose a hunger bar when it tries to change, at max level, you will not lose hunger.
    SATURATION("Saturation", 3, Arrays.asList(
            1000.0,
            2000.0,
            3000.0
    )), // x-percent chance to not lose a hunger bar when it tries to change, at max level, you will not lose hunger.

    // x-percent chance to deflect an arrow when it hits you
    DEFLECTION("Deflection", 2, Arrays.asList(
            1000.0,
            2000.0
    )),
    ;

    @Getter
    private final String name;
    @Getter
    private final int maxLevel;
    @Getter
    private final List<Double> costPerLevel;

    ClanUpgrade(String name, int maxLevel, List<Double> costPerLevel) {
        this.name = name;
        this.maxLevel = maxLevel;
        this.costPerLevel = costPerLevel;
    }

}
