package dev.negativekb.lemonkitpvp.commands.bounty.subcommands.menu;

import dev.negativekb.lemonkitpvp.core.api.gui.GUI;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class BountyMenu extends GUI {
    public BountyMenu(int page) {
        super("&6&lViewing all bounties", 6);

        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).setName("      ").build();
        for (int i = 0; i < 9; i++) {
            setItem(i, filler);
        }

        for (int i = 45; i < 54; i++) {
            setItem(i, filler);
        }

        List<KitPvPPlayer> bountiedPlayers = KitPvPPlayerManager.getInstance().getPlayers()
                .stream()
                .filter(player -> player.getBounty() != 0)
                .sorted((o1, o2) -> {
            double bounty1 = o1.getBounty();
            double bounty2 = o2.getBounty();

            if (bounty1 < bounty2) {
                return 1;
            } else if (bounty1 > bounty2) {
                return -1;
            }
            return 0;
        }).collect(Collectors.toList());

        DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");
        bountiedPlayers.stream()
                .skip((page - 1) * 36L)
                .limit(36)
                .forEach(player -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUuid());
            ItemStack skull = new ItemBuilder(Material.SKULL_ITEM).setDurability((short) 3).setSkullOwner(offlinePlayer.getName())
                    .setName("&e" + offlinePlayer.getName() + " &7- &c$" + df.format(player.getBounty()))
                    .build();

            addItem(skull);
        });

        if (bountiedPlayers.size() > page * 36) {
            ItemStack nextPage = new ItemBuilder(Material.ARROW)
                    .setName("&aNext Page").build();
            setItemClickEvent(53, nextPage, (player1, event) -> new BountyMenu(page + 1).open(player1));
        }

        if (page > 1) {
            ItemStack backPage = new ItemBuilder(Material.ARROW)
                    .setName("&cPrevious Page").build();
            setItemClickEvent(45, backPage, (player1, event) -> new BountyMenu(page - 1).open(player1));
        }
    }
}
