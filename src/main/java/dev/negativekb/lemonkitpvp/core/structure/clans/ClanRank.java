package dev.negativekb.lemonkitpvp.core.structure.clans;

import lombok.Getter;

import java.util.Arrays;

public enum ClanRank {
    LEADER("Leader", 5),
    OFFICER("Officer", 4),
    MODERATOR("Mod", 3),
    MEMBER("Member", 2),
    RECRUIT("Recruit", 1),
    ;

    @Getter
    private final String name;
    @Getter
    private final int priority;

    ClanRank(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public static ClanRank getByString(String input) {
        return Arrays.stream(values()).filter(clanRank -> clanRank.getName().equalsIgnoreCase(input)).findFirst().orElse(null);
    }

    public boolean isAbove(ClanRank rank) {
        return this.getPriority() > rank.getPriority();
    }
}
