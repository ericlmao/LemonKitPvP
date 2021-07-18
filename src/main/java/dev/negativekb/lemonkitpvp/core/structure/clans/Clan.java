package dev.negativekb.lemonkitpvp.core.structure.clans;

import dev.negativekb.lemonkitpvp.core.util.Message;
import dev.negativekb.lemonkitpvp.core.util.RomanNumeral;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Clan {

    private final String name;
    private int level;
    private int kills;
    private int deaths;
    private HashMap<UUID, String> members;
    private HashMap<String, Integer> upgrades;

    public Clan(String name, Player leader) {
        this.name = name;
        this.members = new HashMap<>();
        this.upgrades = new HashMap<>();

        this.members.put(leader.getUniqueId(), "Leader");
    }

    public void addMember(Player player) {
        this.members.put(player.getUniqueId(), "Recruit");
    }

    public OfflinePlayer getMember(UUID uuid) {
        Map.Entry<UUID, String> e = getMembers()
                .entrySet()
                .stream()
                .filter(uuidStringEntry -> uuidStringEntry.getKey().equals(uuid))
                .findFirst().orElse(null);

        return (e == null ? null : Bukkit.getOfflinePlayer(e.getKey()));
    }

    public void setMemberRank(UUID uuid, ClanRank rank) {
        this.members.replace(uuid, rank.getName());
    }

    public ClanRank getMemberRank(UUID uuid) {
        Map.Entry<UUID, String> entry = getMembers()
                .entrySet()
                .stream()
                .filter(uuidStringEntry -> uuid.equals(uuidStringEntry.getKey()))
                .findFirst().orElse(null);
        return (entry == null ? null : ClanRank.getByString(entry.getValue()));
    }

    public void removeMember(UUID uuid) {
        HashMap<UUID, String> members = getMembers();
        members.remove(uuid);
        setMembers(members);
    }

    public String getUpgradeDisplay(ClanUpgrade upgrade) {
        Map.Entry<String, Integer> e = getUpgrades()
                .entrySet()
                .stream()
                .filter(stringIntegerEntry -> upgrade.getName().equalsIgnoreCase(stringIntegerEntry.getKey()))
                .findFirst().orElse(null);

        return (e == null ? upgrade.getName() + " 0" : upgrade.getName() + " " + RomanNumeral.convert(e.getValue()));
    }

    public boolean isMaxLevel(ClanUpgrade upgrade) {
        Map.Entry<String, Integer> e = getUpgrades()
                .entrySet()
                .stream()
                .filter(stringIntegerEntry -> upgrade.getName().equalsIgnoreCase(stringIntegerEntry.getKey()))
                .findFirst().orElse(null);

        if (e == null)
            return false;

        int current = e.getValue();
        int max = upgrade.getMaxLevel();
        return (current == max);
    }

    public boolean hasUpgrade(ClanUpgrade upgrade) {
        Map.Entry<String, Integer> e = getUpgrades()
                .entrySet()
                .stream()
                .filter(stringIntegerEntry -> upgrade.getName().equalsIgnoreCase(stringIntegerEntry.getKey()))
                .findFirst().orElse(null);

        return e != null;
    }

    public void setUpgrade(ClanUpgrade upgrade, int level) {
        if (upgrades.containsKey(upgrade.getName()))
            upgrades.replace(upgrade.getName(), level);
        else
            upgrades.put(upgrade.getName(), level);
    }

    public int getNextUpgradeLevel(ClanUpgrade upgrade) {
        Map.Entry<String, Integer> e = getUpgrades()
                .entrySet()
                .stream()
                .filter(stringIntegerEntry -> upgrade.getName().equalsIgnoreCase(stringIntegerEntry.getKey()))
                .findFirst().orElse(null);

        return (e == null ? 1 : e.getValue() + 1);
    }

    public double getNextUpgradeCost(ClanUpgrade upgrade) {
        int next = getNextUpgradeLevel(upgrade);
        return upgrade.getCostPerLevel().get((next - 1));
    }

    public void sendAnnouncement(String message) {
        List<Map.Entry<UUID, String>> onlineMembers = getMembers()
                .entrySet()
                .stream()
                .filter(uuidStringEntry -> Bukkit.getPlayer(uuidStringEntry.getKey()) != null)
                .collect(Collectors.toList());

        onlineMembers.forEach(uuidStringEntry -> {
            Player player = Bukkit.getPlayer(uuidStringEntry.getKey());
            new Message("&b[Clan Announcement] &7" + message).send(player);
        });
    }
}
