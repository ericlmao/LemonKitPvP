package dev.negativekb.lemonkitpvp.core.structure;

import lombok.Data;

import java.util.UUID;

@Data
public class KitPvPPlayer {

    private final UUID uuid;
    private int kills;
    private int deaths;
    private int killStreak;
    private int bestKillStreak;
    private double bounty;
    private int level;
    private double xp;
    private double coins;
    private String kit;


    public double getKDR() {
        return (double) kills / (double) deaths;
    }
}
