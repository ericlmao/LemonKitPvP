package dev.negativekb.lemonkitpvp.core.kit;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum KitType {
    GLADIATOR("Gladiator"),
    INFERNO("Inferno"),
    HUNTER("Hunter"),
    MAGICAL("Magical"),
    BEAM("Beam"),
    ;

    private static final Map<String, KitType> aliases = new HashMap<>();

    static {
        for (KitType kitType : values()) {
            aliases.put(kitType.getAlias(), kitType);
        }
    }


    @Getter
    private final String alias;

    KitType(String alias) {
        this.alias = alias;
    }
}
