package dev.negativekb.lemonkitpvp.core.managers;

import dev.negativekb.lemonkitpvp.LemonKitPvP;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPLevel;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class KitPvPLevelManager {

    @Getter
    private static KitPvPLevelManager instance;
    @Getter
    private final ArrayList<KitPvPLevel> levels = new ArrayList<>();

    public KitPvPLevelManager() {
        instance = this;
        LemonKitPvP plugin = LemonKitPvP.getInstance();

        FileConfiguration config = plugin.getConfig();
        config.getConfigurationSection("levels").getKeys(false).forEach(s -> {
            int level = Integer.parseInt(s);
            double required = config.getDouble("levels." + level);
            levels.add(new KitPvPLevel(level, required));
        });
    }

    public double getRequiredExperience(int level) {
        KitPvPLevel kLevel = getLevels()
                .stream()
                .filter(kitPvPLevel -> kitPvPLevel.getLevel() == level)
                .findFirst().orElse(null);

        return (kLevel == null ? -1 : kLevel.getRequiredExperience());
    }

    public KitPvPLevel getLevelClass(int level) {
        return getLevels()
                .stream()
                .filter(kitPvPLevel -> kitPvPLevel.getLevel() == level)
                .findFirst().orElse(null);
    }

}
