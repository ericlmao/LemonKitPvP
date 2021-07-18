package dev.negativekb.lemonkitpvp.core.data;

import com.google.gson.Gson;
import dev.negativekb.lemonkitpvp.LemonKitPvP;
import dev.negativekb.lemonkitpvp.core.structure.RefillCrystal;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class CrystalManager {

    @Getter
    private static CrystalManager instance;
    private final String dataPath;
    private final Gson gson;

    @Getter
    private ArrayList<RefillCrystal> refillCrystals = new ArrayList<>();
    @Getter
    private final HashMap<RefillCrystal, EnderCrystal> entityMap = new HashMap<>();

    public CrystalManager() {
        instance = this;
        gson = new Gson();
        LemonKitPvP plugin = LemonKitPvP.getInstance();
        this.dataPath = plugin.getDataFolder().getAbsolutePath() + "/clans.json";

        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        spawnCrystals();

        new Timer().runTaskTimerAsynchronously(plugin, 0, 20L * 120);
    }

    public void save() throws IOException {
        File file = new File(dataPath);
        file.getParentFile().mkdir();
        file.createNewFile();

        Writer writer = new FileWriter(file, false);
        gson.toJson(refillCrystals, writer);
        writer.flush();
        writer.close();
    }

    public void load() throws IOException {
        File file = new File(dataPath);
        if (file.exists()) {
            Reader reader = new FileReader(file);
            RefillCrystal[] p = gson.fromJson(reader, RefillCrystal[].class);
            refillCrystals = new ArrayList<>(Arrays.asList(p));
        }
    }

    public RefillCrystal getByID(UUID uuid) {
        return getRefillCrystals().stream().filter(refillCrystal -> refillCrystal.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public RefillCrystal getByLocation(Location location) {
        return getRefillCrystals().stream().filter(refillCrystal -> {
            Location l = refillCrystal.getLocation();
            return location.getWorld() == l.getWorld() && location.getX() == l.getX() && location.getY() == l.getY() && location.getZ() == l.getZ();
        }).findFirst().orElse(null);
    }

    public void createRefillCrystal(Location location) {
        RefillCrystal crystal = new RefillCrystal(UUID.randomUUID(), location.getWorld().toString(), location.getX(), location.getY(), location.getZ());
        refillCrystals.add(crystal);
    }

    public void deleteCrystal(RefillCrystal crystal) {
        refillCrystals.remove(crystal);
    }

    public void spawnCrystals() {
        getRefillCrystals().forEach(crystal -> {
            Location location = crystal.getLocation();
            World world = crystal.getLocation().getWorld();
            Entity c = world.spawnEntity(location, EntityType.ENDER_CRYSTAL);
            EnderCrystal ender = (EnderCrystal) c;
            entityMap.put(crystal, ender);
        });
    }

    private class Timer extends BukkitRunnable {

        @Override
        public void run() {
            try {
                save();
                load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
