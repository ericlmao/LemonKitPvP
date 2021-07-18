package dev.negativekb.lemonkitpvp.commands.crystals.menu;

import dev.negativekb.lemonkitpvp.commands.bounty.subcommands.menu.BountyMenu;
import dev.negativekb.lemonkitpvp.core.api.gui.GUI;
import dev.negativekb.lemonkitpvp.core.data.CrystalManager;
import dev.negativekb.lemonkitpvp.core.structure.RefillCrystal;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CrystalMenu extends GUI {
    public CrystalMenu(int page) {
        super("&d&lRefill Crystals", 6);

        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).setName("     ").build();
        for (int i = 0; i < 9; i++) {
            setItem(i, filler);
        }

        for (int i = 45; i < 54; i++) {
            setItem(i, filler);
        }

        ArrayList<RefillCrystal> refillCrystals = CrystalManager.getInstance().getRefillCrystals();
        refillCrystals
                .stream().skip((page - 1) * 36L)
                .limit(36)
                .forEach(crystal -> {
                    ItemStack c = new ItemBuilder(Material.BEDROCK).setName("&d&lRefill Crystal &c#" + (refillCrystals.indexOf(crystal) + 1))
                            .setLore(
                                    "&ex: &f" + crystal.getX(),
                                    "&ey: &f" + crystal.getY(),
                                    "&ez: &f" + crystal.getZ(),
                                    " ",
                                    "&6Left-Click to teleport",
                                    "&6Right-Click to remove"
                            ).build();

                    addItemClickEvent(c, (player, event) -> {
                        if (event.isLeftClick()) {
                            player.teleport(crystal.getLocation());
                            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                            return;
                        }

                        if (event.isRightClick()) {
                            CrystalManager instance = CrystalManager.getInstance();
                            instance.removeCrystals();
                            instance.deleteCrystal(crystal);
                            instance.spawnCrystals();
                            new Message("&cDeleted Refill Crystal!").send(player);
                            new CrystalMenu(page).open(player);
                            System.out.println(player.getName() + " has removed a Refill Crystal");
                        }
                    });
                });

        if (refillCrystals.size() > page * 36) {
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
