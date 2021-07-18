package dev.negativekb.lemonkitpvp.core.structure.clans;

import lombok.Getter;

public enum ClanRank {
    LEADER("Leader"),
    OFFICER("Officer"),
    MODERATOR("Mod"),
    MEMBER("Member"),
    RECRUIT("Recruit"),
    ;

    @Getter
    private final String name;

    ClanRank(String name) {
        this.name = name;
    }
}
