package dev.negativekb.lemonkitpvp.core.structure;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

@Data
public class RefillCrystal {

    private final UUID uuid;
    private final String world;
    private final double x;
    private final double y;
    private final double z;

    public Location getLocation() {
        World w = Bukkit.getWorld(world);
        return new Location(w, x, y, z);
    }
}
