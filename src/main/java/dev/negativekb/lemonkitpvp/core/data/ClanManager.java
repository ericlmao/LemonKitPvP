package dev.negativekb.lemonkitpvp.core.data;

import com.google.gson.Gson;
import dev.negativekb.lemonkitpvp.LemonKitPvP;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ClanManager {

    @Getter
    private static ClanManager instance;
    private final String dataPath;
    private final Gson gson;

    @Getter
    private ArrayList<Clan> clans = new ArrayList<>();

    public ClanManager() {
        instance = this;
        gson = new Gson();
        LemonKitPvP plugin = LemonKitPvP.getInstance();
        this.dataPath = plugin.getDataFolder().getAbsolutePath() + "/clans.json";

        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Timer().runTaskTimerAsynchronously(plugin, 0, 20L * 120);
    }

    public void save() throws IOException {
        File file = new File(dataPath);
        file.getParentFile().mkdir();
        file.createNewFile();

        Writer writer = new FileWriter(file, false);
        gson.toJson(clans, writer);
        writer.flush();
        writer.close();
    }

    public void load() throws IOException {
        File file = new File(dataPath);
        if (file.exists()) {
            Reader reader = new FileReader(file);
            Clan[] p = gson.fromJson(reader, Clan[].class);
            clans = new ArrayList<>(Arrays.asList(p));
        }
    }

    public Clan getClanByName(String input) {
        return getClans()
                .stream()
                .filter(clan -> clan.getName().equalsIgnoreCase(input))
                .findFirst().orElse(null);
    }

    public Clan getClanByUUID(UUID uuid) {
        return getClans()
                .stream()
                .filter(clan -> clan.getMembers().containsKey(uuid))
                .findFirst().orElse(null);
    }

    public Clan getClanByPlayer(Player player) {
        return getClanByUUID(player.getUniqueId());
    }

    public Clan getClanByOfflinePlayer(OfflinePlayer player) {
        return getClanByUUID(player.getUniqueId());
    }

    public void createClan(String name, Player leader) {
        Clan clan = new Clan(name, leader);
        clans.add(clan);
    }

    public void deleteClan(Clan clan) {
        clans.remove(clan);
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
