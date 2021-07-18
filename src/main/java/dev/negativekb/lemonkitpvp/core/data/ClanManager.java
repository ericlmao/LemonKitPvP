package dev.negativekb.lemonkitpvp.core.data;

import com.google.gson.Gson;
import dev.negativekb.lemonkitpvp.LemonKitPvP;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.*;

public class ClanManager {

    @Getter
    private static ClanManager instance;
    private final String dataPath;
    private final Gson gson;
    @Getter
    private final HashMap<Clan, ArrayList<UUID>> clanInvites = new HashMap<>();
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


    public ArrayList<UUID> getInvites(Clan clan) {
        Map.Entry<Clan, ArrayList<UUID>> entry = getClanInvites()
                .entrySet()
                .stream()
                .filter(clanArrayListEntry -> clan.getName().equalsIgnoreCase(clanArrayListEntry.getKey().getName()))
                .findFirst().orElse(null);
        return (entry == null ? null : entry.getValue());
    }

    public boolean isInvited(UUID uuid, Clan clan) {
        if (clanInvites.isEmpty() || !clanInvites.containsKey(clan))
            return false;

        UUID invite = getInvites(clan)
                .stream()
                .filter(uuid1 -> uuid1.equals(uuid))
                .findFirst().orElse(null);
        return (invite != null);
    }

    public void invite(UUID uuid, Clan clan) {
        ArrayList<UUID> invites = (getInvites(clan) == null ? new ArrayList<>() : getInvites(clan));
        invites.add(uuid);

        if (clanInvites.containsKey(clan))
            clanInvites.replace(clan, invites);
        else
            clanInvites.put(clan, invites);

    }

    public void removeInvite(UUID uuid, Clan clan) {
        if (getInvites(clan) == null || !isInvited(uuid, clan))
            return;

        ArrayList<UUID> invites = getInvites(clan);
        invites.remove(uuid);

        if (invites.isEmpty()) {
            clanInvites.remove(clan);
            return;
        }

        if (clanInvites.containsKey(clan))
            clanInvites.replace(clan, invites);
        else
            clanInvites.put(clan, invites);
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
