package dev.negativekb.lemonkitpvp.core.data;

import com.google.gson.Gson;
import dev.negativekb.lemonkitpvp.LemonKitPvP;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class KitPvPPlayerManager {

    @Getter
    private static KitPvPPlayerManager instance;
    private final String dataPath;
    private final Gson gson;


    @Getter
    private ArrayList<KitPvPPlayer> players = new ArrayList<>();

    public KitPvPPlayerManager() {
        instance = this;
        gson = new Gson();
        LemonKitPvP plugin = LemonKitPvP.getInstance();
        this.dataPath = plugin.getDataFolder().getAbsolutePath() + "/players.json";

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
        gson.toJson(players, writer);
        writer.flush();
        writer.close();
    }

    public void load() throws IOException {
        File file = new File(dataPath);
        if (file.exists()) {
            Reader reader = new FileReader(file);
            KitPvPPlayer[] p = gson.fromJson(reader, KitPvPPlayer[].class);
            players = new ArrayList<>(Arrays.asList(p));
        }
    }

    public KitPvPPlayer getPlayerByUUID(UUID uuid) {
        return getPlayers().stream().filter(kitPvPPlayer -> kitPvPPlayer.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public KitPvPPlayer getPlayerByPlayer(Player player) {
        return getPlayerByUUID(player.getUniqueId());
    }

    public KitPvPPlayer getPlayerByOfflinePlayer(OfflinePlayer player) {
        return getPlayerByUUID(player.getUniqueId());
    }

    public void createPlayer(UUID uuid) {
        KitPvPPlayer player = new KitPvPPlayer(uuid);
        players.add(player);
    }

    public void deletePlayer(KitPvPPlayer player) {
        players.remove(player);
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
