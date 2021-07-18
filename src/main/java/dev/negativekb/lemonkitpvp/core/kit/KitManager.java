package dev.negativekb.lemonkitpvp.core.kit;

import dev.negativekb.lemonkitpvp.kits.*;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KitManager {

    @Getter
    private static KitManager instance;
    @Getter
    private final HashMap<KitType, Kit> kits = new HashMap<>();

    public KitManager() {
        instance = this;

        registerKits(
                new KitGladiator(),
                new KitBeam(),
                new KitHunter(),
                new KitInferno(),
                new KitMagical()
        );
    }

    private void registerKits(Kit... kits) {
        Arrays.stream(kits).forEach(kit -> {
            this.kits.put(kit.getKitType(), kit);
        });
    }

    public Kit getKitByType(KitType type) {
        Map.Entry<KitType, Kit> entry = getKits()
                .entrySet()
                .stream()
                .filter(kitTypeKitEntry -> kitTypeKitEntry.getKey() == type)
                .findFirst().orElse(null);

        return (entry == null ? null : entry.getValue());
    }

    public Kit getKitByString(String input) {
        Map.Entry<KitType, Kit> entry = getKits()
                .entrySet()
                .stream()
                .filter(kitTypeKitEntry -> input.equalsIgnoreCase(kitTypeKitEntry.getKey().getAlias()))
                .findFirst().orElse(null);

        return (entry == null ? null : entry.getValue());
    }
}
