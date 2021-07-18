package dev.negativekb.lemonkitpvp.commands.clans.subcommands.upgrades;

import dev.negativekb.lemonkitpvp.core.api.gui.GUI;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.structure.clans.ClanUpgrade;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import dev.negativekb.lemonkitpvp.core.util.Message;
import dev.negativekb.lemonkitpvp.core.util.RomanNumeral;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Arrays;

public class ClanUpgradeMenu extends GUI {
    public ClanUpgradeMenu(KitPvPPlayer stats, Clan clan) {
        super("&b&lClan Upgrades", 6);

        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).setName("       ").build();
        for (int i = 0; i < 9; i++) {
            setItem(i, filler);
        }

        for (int i = 45; i < 54; i++) {
            setItem(i, filler);
        }

        Arrays.stream(ClanUpgrade.values()).forEach(upgrade -> {

            boolean has = clan.hasUpgrade(upgrade);
            int level = clan.getUpgradeLevel(upgrade);

            DecimalFormat df = new DecimalFormat("###,###,###,###.##");

            String description = upgrade.getDescription();
            description = description.replaceAll("%percent%", String.valueOf(level * 10))
                    .replaceAll("%amount%", String.valueOf(level * 2));

            if (!has) {
                double cost = upgrade.getCostPerLevel().get(level);
                ItemStack item = new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 14)
                        .setName("&c&l" + upgrade.getName())
                        .setLore("&eClick to purchase upgrade",
                                "&6Cost: &f" + df.format(cost))
                        .build();

                addItemClickEvent(item, (player, event) -> {
                    double coins = stats.getCoins();
                    if (coins < cost) {
                        new Message("&cYou cannot afford to purchase this upgrade!").send(player);
                        return;
                    }

                    stats.setCoins(stats.getCoins() - cost);
                    clan.setUpgrade(upgrade, 1);
                    clan.sendAnnouncement("&e" + player.getName() + " &7has purchased &c"
                            + upgrade.getName() + " " + RomanNumeral.convert(clan.getUpgradeLevel(upgrade)));

                    new ClanUpgradeMenu(stats, clan).open(player);
                });
                return;
            }

            if (clan.isMaxLevel(upgrade)) {
                ItemStack item = new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 11)
                        .setName("&a&l" + upgrade.getName() + " &e&l" + RomanNumeral.convert(level))
                        .setLore(description,
                                "&b&lMAXED")
                        .build();

                addItem(item);
                return;
            }
            double cost = upgrade.getCostPerLevel().get(level);
            ItemStack item = new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 5)
                    .setName("&a&l" + upgrade.getName() + " &e&l" + RomanNumeral.convert(level))
                    .setLore(description, " ",
                            "&eClick to upgrade",
                            "&6Cost: &f" + df.format(cost))
                    .build();

            addItemClickEvent(item, (player, event) -> {
                double coins = stats.getCoins();
                if (coins < cost) {
                    new Message("&cYou cannot afford to purchase this upgrade!").send(player);
                    return;
                }

                stats.setCoins(stats.getCoins() - cost);
                clan.setUpgrade(upgrade, level + 1);
                clan.sendAnnouncement("&e" + player.getName() + " &7has purchased &c"
                        + upgrade.getName() + " " + RomanNumeral.convert(clan.getUpgradeLevel(upgrade)));
                new ClanUpgradeMenu(stats, clan).open(player);
            });

        });
    }
}
