package dev.negativekb.lemonkitpvp.commands.clans.subcommands.menus;

import dev.negativekb.lemonkitpvp.core.api.gui.GUI;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.structure.clans.ClanRank;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClanMembersMenu extends GUI {
    public ClanMembersMenu(Player player, Clan clan, int page) {
        super("&6&lClan Members", 6);

        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).setName("     ").build();
        for (int i = 0; i < 9; i++) {
            setItem(i, filler);
        }

        for (int i = 45; i < 54; i++) {
            setItem(i, filler);
        }

        // Sorts by Clan Rank (Recruit -> Member -> Moderator -> Officer -> Leader)
        List<Map.Entry<UUID, String>> members = clan.getMembers().entrySet().stream().sorted((o1, o2) -> {
            ClanRank rank1 = clan.getMemberRank(o1.getKey());
            ClanRank rank2 = clan.getMemberRank(o2.getKey());
            int pri1 = rank1.getPriority();
            int pri2 = rank2.getPriority();

            if (pri1 < pri2) {
                return 1;
            } else if (pri1 > pri2) {
                return -1;
            }
            return 0;

        }).collect(Collectors.toList());

        members.stream()
                .skip((page - 1) * 36L)
                .limit(36)
                .forEach(entry -> {
                    UUID uuid = entry.getKey();

                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                    ItemStack skull = new ItemBuilder(Material.SKULL_ITEM).setDurability((short) 3).setSkullOwner(offlinePlayer.getName())
                            .setName("&e" + offlinePlayer.getName() + " &7- &c" + clan.getMemberRank(offlinePlayer.getUniqueId()).getName())
                            .build();

                    addItem(skull);
                });
    }
}
